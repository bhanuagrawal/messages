package com.example.messages.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messages.MainViewModel;
import com.example.messages.R;
import com.example.messages.data.entities.User;

import java.util.ArrayList;
import java.util.List;


public class ContactListFragment extends Fragment implements ItemAdater.ItemAdaterListner, MainFragmentListner {

    private OnFragmentInteractionListener mListener;
    private ItemAdater itemApdater;
    private Observer<List<User>> userObserver;
    private MainViewModel mainViewModel;

    @BindView(R.id.recyclerview)
    RecyclerView itemsRV;



    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userObserver = new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                itemApdater.onDataChange((ArrayList) users);
                if(users.isEmpty()){
                    mainViewModel.addUsers();
                }

            }
        };
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        itemApdater = new ItemAdater(getContext(), this, ItemAdater.USERS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        itemsRV.setAdapter(itemApdater);
        itemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel.getUsersLiveData().observe(this, userObserver);

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
        mainViewModel.getUsersLiveData().removeObserver(userObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onSelect(User user) {
        mListener.openPage( MainFragmentDirections.actionMainFragmentToContactDetailFragment(user.getId()));
    }

    @Override
    public void onPageSelected() {

    }
}
