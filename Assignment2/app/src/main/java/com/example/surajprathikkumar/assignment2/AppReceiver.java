package com.example.surajprathikkumar.assignment2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String val=intent.getAction();
        Toast.makeText(context,val,Toast.LENGTH_SHORT).show();
        Log.i("The Broadcast Received",val);
    }
}
