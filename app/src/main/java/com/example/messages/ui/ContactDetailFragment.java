package com.example.messages.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messages.MainViewModel;
import com.example.messages.OtpStatus;
import com.example.messages.R;
import com.example.messages.data.entities.User;


public class ContactDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.tv1)
    TextView tv1;

    @BindView(R.id.tv2)
    TextView tv2;

    @BindView(R.id.button)
    Button button;

    private MainViewModel mainViewModel;
    private int userId;
    private Observer<User> userObserver;


    public ContactDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        userId = ContactDetailFragmentArgs.fromBundle(getArguments()).getUserId();
        userObserver = new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                bindUi(user);
            }
        };


    }

    private void bindUi(User user) {
        tv1.setText(user.getName());
        tv2.setText(user.getMobile());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.openPage(ContactDetailFragmentDirections.actionContactDetailFragmentToCreateMessageFragment(userId));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_detail, container, false);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
