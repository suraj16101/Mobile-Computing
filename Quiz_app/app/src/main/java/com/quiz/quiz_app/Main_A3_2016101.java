package com.quiz.quiz_app;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__a3_2016101);
        myDb = new DatabaseHelper(this);
//        boolean insert1 = myDb.addvalue("The Language that the computer can understand is called Machine Language.","True");
//        boolean insert2 = myDb.addvalue("Magnetic Tape used random access method.","False");
//        boolean insert3 = myDb.addvalue("Twitter is an online social networking and blogging service.","False");
//        boolean insert4 = myDb.addvalue("Worms and trojan horses are easily detected and eliminated by antivirus software.","True");
//        boolean insert5 = myDb.addvalue("Dot-matrix, Deskjet, Inkjet and Laser are all types of Printers.","True");
//        boolean insert6 = myDb.addvalue("GNU / Linux is a open source operating system.","True");
//        boolean insert7 = myDb.addvalue("Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.","True");
//        boolean insert8 = myDb.addvalue("Freeware is software that is available for use at no monetary cost.","True");
//        boolean insert9 = myDb.addvalue("IPv6 Internet Protocol address is represented as eight groups of four Octal digits.","False");
//        boolean insert10 = myDb.addvalue("The hexadecimal number system contains digits from 1 - 15.","False");
//        boolean insert11 = myDb.addvalue("Octal number system contains digits from 0 - 7.","True");
//        boolean insert12 = myDb.addvalue("MS Word is a hardware.","False");
//        boolean insert13 = myDb.addvalue("CPU controls only input data of computer.","False");
//        boolean insert14 = myDb.addvalue("CPU stands for Central Performance Unit.","False");
//        boolean insert15 = myDb.addvalue("When you include multiple addresses in a message, you should separate each address with a period (.)","False");
//        boolean insert16 = myDb.addvalue("You cannot format text in an e-mail message.","False");
//        boolean insert17 = myDb.addvalue("You must include a subject in any mail message you compose.","False");
//        boolean insert18 = myDb.addvalue("If you want to respond to the sender of a message, click the Respond button.","False");
//        boolean insert19 = myDb.addvalue("You type the body of a reply the same way you would type the body of a new message.","True");
//        boolean insert20 = myDb.addvalue("When you reply to a message, you need to enter the text in the Subject: field.","False");
//        boolean insert21 = myDb.addvalue("You can only print one copy of a selected message.","False");
//        boolean insert22 = myDb.addvalue("You cannot preview a message before you print it.","False");
//        boolean insert23 = myDb.addvalue("There is only one way to print a message.","False");
//        boolean insert24 = myDb.addvalue("When you print a message, it is automatically deleted from your Inbox.","False");
//        boolean insert25 = myDb.addvalue("You need to delete a contact and create a new one to change contact information.","False");
//        boolean insert26 = myDb.addvalue("You must complete all fields in the Contact form before you can save the contact.","False");
//        boolean insert27 = myDb.addvalue("You cannot edit Contact forms.","False");
//        boolean insert28 = myDb.addvalue("You should always open and attachment before saving it.","False");
//        boolean insert29 = myDb.addvalue("You can delete e-mails from a Web-based e-mail account.","True");
//        boolean insert30 = myDb.addvalue("You can store Web-based e-mail messages in online folders.","True");
//
//
//
//        if(insert1==true && insert30==true)
//        {
//            Log.i("Insert","Hogaya");
//        }
//        else{
//            Log.i("Insert","No");
//        }

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
        File file1 = new File(Direct, "QuizResult.csv");
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
