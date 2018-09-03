package com.example.surajprathikkumar.assignment2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download extends Service {
    public Download()
    {

    }

    @Override
    public IBinder onBind(Intent intent)
    {

        return null;
    }

    public String startdownload() throws IOException
    {
        InputStream is=null;
        int len =500000;
        try{
            URL url=new URL("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.i("Service","The response is:"+response);
            is=conn.getInputStream();

            String res= readIt(is,len);
            Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show();
            return res;

        }
        finally {
            if(is!=null){
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                is.close();
            }
        }



    }

    private String readIt(InputStream is, int len) throws IOException,UnsupportedEncodingException
    {
        Reader reader=null;
        reader=new InputStreamReader(is,"UTF-8");
        char[] buffer=new char[len];
        reader.read(buffer);
        return new String(buffer);

    }

    public int onStartCommand(Intent intent, int flags, int startid)
    {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        try {
            String file=startdownload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    public void onDestroy(){

        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}
