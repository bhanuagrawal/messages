package com.example.messages;

import android.app.Application;
import android.util.Log;

import com.example.messages.data.Repository;
import com.example.messages.data.entities.User;
import com.example.messages.data.entities.UserMessage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private final Repository repo;
    private LiveData<List<User>> usersLiveData;


    private MutableLiveData<OtpStatus> otpStatusMutableLiveData;



    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = new Repository(application);


    }


    public LiveData<List<User>> getUsersLiveData() {
        return repo.getUsers();
    }

    public void addUsers() {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repo.addUsers();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("user_add_operation", "complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("user_add_operation", "error");

            }
        });
    }

    public LiveData<User> getUser(int userId) {
        return repo.getUser(userId);
    }


    public LiveData<List<UserMessage>> getMessages() {
        return repo.getMessages();
    }
}
