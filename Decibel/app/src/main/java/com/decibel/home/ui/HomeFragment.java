package com.decibel.home.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.decibel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, rootView);

        replaceFragment(new RegionFragment(), false,
                R.id.bottom_navigation_fragment_container);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        navigateFragment(item.getItemId(), false);
                        return true;
                    }
                });

        return rootView;
    }

    public void navigateFragment(int id, boolean shouldSelect) {
        if (shouldSelect) {
            bottomNavigationView.setSelectedItemId(id);
        } else {
            switch (id) {
                case R.id.action_home:
                    replaceFragment(new RegionFragment(), false,
                            R.id.bottom_navigation_fragment_container);
                    break;

                case R.id.action_noise:
                    replaceFragment(new NoiseFragment(), false,
                            R.id.bottom_navigation_fragment_container);
                    break;

                case R.id.action_vacant:
                    replaceFragment(new VacantFragment(), false,
                            R.id.bottom_navigation_fragment_container);
                    break;

            }
        }
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        String backStateName = fragment.getClass().getName();
        boolean fragmentPopped = getChildFragmentManager().popBackStackImmediate(backStateName,
                0);

        if (!fragmentPopped && getChildFragmentManager().findFragmentByTag(backStateName) ==
                null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(containerId, fragment, backStateName);
            if (addToBackStack) {
                transaction.addToBackStack(backStateName);
            }
            transaction.commit();
        }
    }
}
