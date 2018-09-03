package com.example.surajprathikkumar.assignment2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class Download extends Service {
    public Download() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override


        public int onStartCommand(Intent intent, int flags, int startid) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        try {

            new DownloadMusic().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    private class DownloadMusic extends AsyncTask<String, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("RTE", "onPostExecute: ");
        }

        private String download() throws IOException {
            InputStream is = null;
            OutputStream out = null;
            int len = 500000;
            try {
                URL url = new URL("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                int response = conn.getResponseCode();
                Log.i("Service", "The response is:" + response);
                is = conn.getInputStream();



                String res = readIt(is, len);

                //File file = new File(FILENAME);
                out = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                out.write(res.getBytes());
                out.close();

                return res;

            } finally {
                if (is != null) {
                    Log.i("Download", "Done");
                    is.close();
                }
            }
        }

        private String readIt(InputStream is, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(is, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);

        }


        @Override
        protected String doInBackground(String... strings) {
            try {

               download();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("RTE", "doInBackground: " + e.getMessage());
            }
            return null;
        }
    }

}
