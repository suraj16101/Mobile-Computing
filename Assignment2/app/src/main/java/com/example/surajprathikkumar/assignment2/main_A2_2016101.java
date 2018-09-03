package com.example.surajprathikkumar.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class main_A2_2016101 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__a2_2016101);

        AppReceiver receiver = new AppReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(receiver,filter);
        IntentFilter filter2 = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        this.registerReceiver(receiver,filter2);
        IntentFilter filter3 = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        this.registerReceiver(receiver,filter3);
    }


    public void onClickDownload(View v)
    {
        ConnectivityManager connManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            Toast.makeText(this,"Network is connected",Toast.LENGTH_SHORT).show();
            Log.i("Network status","Connected");
            Intent intent=new Intent(this,Download.class);
            startService(intent);

        }
        else{
            Log.i("Network status","Not Connected");
            Toast.makeText(this,"Network connection not available",Toast.LENGTH_SHORT).show();
        }
    }
}
