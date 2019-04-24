package com.example.messages;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.messages.ui.OnFragmentInteractionListener;

import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
    }

    @Override
    public void openPage(NavDirections action) {
        navHostFragment.getNavController().navigate(action);
    }

    @Override
    public void navigateTo(int resId) {
        navHostFragment.getNavController().navigate(resId);
    }
}
