package com.example.messages.ui;

import android.app.Application;
import android.util.Log;

import com.example.messages.OtpStatus;
import com.example.messages.data.Repository;
import com.example.messages.data.entities.User;
import com.example.messages.network.TwilioUtil;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMessageViewModel extends AndroidViewModel {
    private final Repository repo;
    private final TwilioUtil twilioHelper;
    private MutableLiveData<OtpStatus> otpStatusMutableLiveData;
    private String status;

    public CreateMessageViewModel(@NonNull Application application) {
        super(application);
        repo = new Repository(application);
        twilioHelper = new TwilioUtil();
    }

    public MutableLiveData<OtpStatus> getOtpStatusMutableLiveData() {
        if(otpStatusMutableLiveData == null){
            otpStatusMutableLiveData = new MutableLiveData<>();
            otpStatusMutableLiveData.postValue(OtpStatus.NOT_SENDING);
        }
        return otpStatusMutableLiveData;
    }

    public void sendMessage(User user, String message) {
        otpStatusMutableLiveData.postValue(OtpStatus.SENDING);
        twilioHelper.sendMessage(user.getMobile(), message, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    Completable.fromAction(new Action() {
                        @Override
                        public void run() throws Exception {
                            repo.saveMessage(user, message);
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            Log.d("message_add_operation", "complete");
                            getOtpStatusMutableLiveData().postValue(OtpStatus.SENT);
                        }

                        @Override
                        public void onError(Throwable e) {
                            otpStatusMutableLiveData.postValue(OtpStatus.ERROR_SENDING);
                            Log.d("message_add_operation", "error");

                        }
                    });
                }
                else{
                    otpStatusMutableLiveData.postValue(OtpStatus.ERROR_SENDING);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("message_send_operation", "failure");


            }
        });


    }

    public String getDefaultMessage() {
        String otp = String.format("%06d",  new Random().nextInt(1000000));
        String message = "Hi. Your OTP is " + otp;
        return message;
    }
}
