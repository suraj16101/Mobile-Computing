package com.mic.quiz;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main_A3_2016101 extends AppCompatActivity
{
    DatabaseHelper myDb;
    private ProgressDialog progressDialog;
    public static final int DIALOG_UPLOAD_PROGRESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__a3_2016101);
        myDb = new DatabaseHelper(this);


    }

    public void onSubmitClick(View view)
    {
        ConnectivityManager connManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            Toast.makeText(this,"Network is connected",Toast.LENGTH_SHORT).show();
            Log.i("Network status","Connected");
            File file=convertoCSV();
            new AsynTask(file).execute();

        }
        else{
            Log.i("Network status","Not Connected");
            Toast.makeText(this,"Network connection not available",Toast.LENGTH_SHORT).show();
        }

    }

    public File convertoCSV() {

        DatabaseHelper db = new DatabaseHelper(this);

        File Direct = new File(Environment.getExternalStorageDirectory(), "");
        Log.i("HERE","2");
        if (!Direct.exists()) {
            Direct.mkdirs();
        }
        File file1 = new File(Direct, "Quiz_Result.csv");
        try {
            Log.i("HERE","3");
            file1.createNewFile();
            Log.i("HERE","4");
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file1));

            Cursor curCSV = db.Select_Row();

            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception e) {
            Log.i("HERE","Error");
        }

        return file1;

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_UPLOAD_PROGRESS:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Uploading...");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }



    class AsynTask extends android.os.AsyncTask<String, Void, String> {
        File file1;
        public AsynTask(File file){
            this.file1=file;
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Log.d("HERE", "onPostExecute: ");
            dismissDialog(DIALOG_UPLOAD_PROGRESS);
        }

        private void publishProgress(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        private void download() throws IOException
        {
            String urlString ="http://192.168.57.35:8000/";
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;

            if (file1.isFile()) {

                try {

                    FileInputStream fileInputStream = new FileInputStream(file1);
                    URL url = new URL(urlString);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=***");

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes("--***" + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"myfile\";filename=\"" + file1.getAbsolutePath() + "\"" + "\r\n");

                    dos.writeBytes("\r\n");

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, 1 * 1024 * 1024);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    int sentBytes = 0;
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        sentBytes += bufferSize;
                        publishProgress("" + (int) (sentBytes * 100 / bytesAvailable));
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, 1 * 1024 * 1024);
                        bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                    }
                    dos.writeBytes("\r\n");
                    dos.writeBytes("--***--" + "\r\n");
                    int serverResponseCode = conn.getResponseCode();
                    if (serverResponseCode == 200) ;
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }




        @Override
        protected String doInBackground(String... strings) {
            try {
                download();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("HERE", "doInBackground: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_UPLOAD_PROGRESS);
        }

    }

}
