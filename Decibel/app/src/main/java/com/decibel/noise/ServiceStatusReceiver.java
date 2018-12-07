package com.decibel.noise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceStatusReceiver extends BroadcastReceiver {
    private static final String TAG = ServiceStatusReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Starting Service from the Receiver");
        context.startService(new Intent(context, NoiseService.class));
    }
}
