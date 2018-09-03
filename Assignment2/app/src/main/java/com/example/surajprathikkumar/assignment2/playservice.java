package com.example.surajprathikkumar.assignment2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class playservice  extends Service{
    public  MediaPlayer mediaPlayer;


    @Override
    public void onCreate(){
        super.onCreate();
    }
    public playservice(){

    }

    public int onStartCommand(Intent intent, int flags, int startid) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        Bundle bundle=intent.getExtras();
        int rawval=bundle.getInt("key");
        mediaPlayer = MediaPlayer.create(this,rawval);
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }

        return START_STICKY;
    }

    public void onDestroy(){

        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
