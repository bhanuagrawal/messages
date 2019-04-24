package com.example.messages.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messages.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private Unbinder uibinder;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uibinder = ButterKnife.bind(this, view);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                MainFragmentListner mainFragmentListner =(MainFragmentListner)viewPagerAdapter.getRegisteredFragment(i);
                mainFragmentListner.onPageSelected();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
        uibinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private int itemCount;

        SparseArray<Fragment> registeredFragments = new SparseArray<>();

        public ViewPagerAdapter(FragmentManager fm, int itemCount) {
            super(fm);
            this.itemCount = itemCount;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(final int i) {

            switch (i){
                case 0:
                    return new ContactListFragment();
                case 1:
                    return new MessageListFragment();
                default:
                    return new ContactListFragment();
            }

        }

        @Override
        public int getCount() {
            return itemCount;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Contacts";
                case 1:
                    return "Messages";
                default:
                    return "Contacts";
            }
        }


        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

    }



}
