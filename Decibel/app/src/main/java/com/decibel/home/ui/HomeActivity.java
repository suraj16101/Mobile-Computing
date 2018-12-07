package com.decibel.home.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.decibel.R;
import com.decibel.home.HomeContract;
import com.decibel.home.presenter.HomePresenter;
import com.decibel.noise.NoiseService;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.HomeView {

    private static final int MIC_PERMISSION_REQUEST_CODE = 101;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 102;

    private HomePresenter mPresenter;
    ImageView bg;
    Animation bganim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bg = findViewById(R.id.bg);
        bganim = AnimationUtils.loadAnimation(this,R.anim.bganimation);
        bg.animate().translationY(-2500).setDuration(1000).setStartDelay(2000);

        ButterKnife.bind(this);

        mPresenter = new HomePresenter(this);
        mPresenter.storeRooms();

        HomeFragment homeFragment = new HomeFragment();
        invalidateOptionsMenu();
        String backStateName = homeFragment.getClass().getName();
        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName,
                0);
        if (!fragmentPopped && getSupportFragmentManager().findFragmentByTag(backStateName) ==
                null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.parent_container, homeFragment, backStateName);
            transaction.commit();
        }

        checkMicPermission();
    }

    private void checkMicPermission() {
        String permission = Manifest.permission.RECORD_AUDIO;
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted. Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{permission},
                    MIC_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            checkLocationPermission();
        }
    }

    private void checkLocationPermission() {
        String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted. Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{permission},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            startNoiseService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case MIC_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkLocationPermission();
                } else {
                    makePermissionCancelDialog();
                }
            }
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startNoiseService();
                } else {
                    makePermissionCancelDialog();
                }
            }
        }
    }

    public void makePermissionCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this)
                .setTitle("And so it ends....")
                .setMessage(
                        "We are sorry to inform you that we cannot continue without this "
                                + "permission. Press OK to exit the application.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                        System.exit(0);
                    }
                });
        builder.show();
    }

    private void startNoiseService() {
        NoiseService noiseService = new NoiseService();
        Intent serviceIntent = new Intent(HomeActivity.this, noiseService.getClass());
        if (!isMyServiceRunning(noiseService.getClass())) {
            startService(serviceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }
}
