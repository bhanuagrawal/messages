package com.example.messages.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messages.MainViewModel;
import com.example.messages.OtpStatus;
import com.example.messages.R;
import com.example.messages.data.entities.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateMessageFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.textView)
    TextView tv1;

    @BindView(R.id.message)
    EditText message;

    @BindView(R.id.button)
    Button button;

    private MainViewModel mainViewModel;
    private int userId;
    private Observer<User> userObserver;
    private Observer<OtpStatus> otpStatusObserver;
    private CreateMessageViewModel createMessageViewModel;
    private ProgressDialog mProgress;


    public CreateMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        createMessageViewModel = ViewModelProviders.of(this).get(CreateMessageViewModel.class);
        mProgress = new ProgressDialog(getActivity());
        userId = CreateMessageFragmentArgs.fromBundle(getArguments()).getUserId();
        userObserver = new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                bindUi(user);
            }
        };


        otpStatusObserver = new Observer<OtpStatus>() {
            @Override
            public void onChanged(OtpStatus otpStatus) {
                if(otpStatus == OtpStatus.SENT){
                    Toast.makeText(getContext(), "OTP sent", Toast.LENGTH_LONG).show();
                    if(mProgress!= null && mProgress.isShowing()){
                        mProgress.dismiss();
                    }
                    mListener.navigateTo(R.id.mainFragment);
                }
                else if(otpStatus == OtpStatus.SENDING){
                    mProgress.setMessage("Sending...");
                    mProgress.show();
                }
                else if(otpStatus == OtpStatus.ERROR_SENDING){
                    Toast.makeText(getContext(), "Error sending otp", Toast.LENGTH_LONG).show();
                    if(mProgress!= null && mProgress.isShowing()){
                        mProgress.dismiss();
                    }
                }
            }
        };
    }

    private void bindUi(User user) {
        tv1.setText("To: " + user.getName());
        message.setText(createMessageViewModel.getDefaultMessage());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMessageViewModel.sendMessage(user, message.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_message, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel.getUser(userId).observe(this, userObserver);
        createMessageViewModel.getOtpStatusMutableLiveData().observe(this, otpStatusObserver);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainViewModel.getUser(userId).removeObserver(userObserver);
        createMessageViewModel.getOtpStatusMutableLiveData().removeObserver(otpStatusObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
