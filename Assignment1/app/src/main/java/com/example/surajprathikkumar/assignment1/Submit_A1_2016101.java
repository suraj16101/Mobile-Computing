package com.example.surajprathikkumar.assignment1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Submit_A1_2016101 extends AppCompatActivity {

    ArrayList Sstate=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit__a1_2016101);
        Bundle bundle=getIntent().getExtras();

        String n1= bundle.getString("n1");
        TextView tv1 = findViewById(R.id.textView1);
        tv1.setText("Name: "+n1);

        String r1=bundle.getString("r1");
        TextView tv2 = findViewById(R.id.textView2);
        tv2.setText("Roll No: "+r1);

        String b1=bundle.getString("b1");
        TextView tv3 = findViewById(R.id.textView3);
        tv3.setText("Branch: "+b1);

        String c1=bundle.getString("c1");
        String c2=bundle.getString("c2");
        String c3=bundle.getString("c3");
        String c4=bundle.getString("c4");

        TextView tv4 = findViewById(R.id.textView4);

        tv4.setText("Courses:  "+" 1."+c1+"\n"+"                   2."+c2+"\n"+"                   3."+c3+"\n"+"                   4."+c4);

        Log.i("lifecycle","State of activity Submit is Created");
        Sstate.add("Created");
        Toast.makeText(this,"Submit is Created",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle","State of activity Submit is Started from "+Sstate.get(Sstate.size()-1));
        Sstate.add("Started");
        Toast.makeText(this,"Submit is Started",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle","State of activity Submit is Resumed from "+Sstate.get(Sstate.size()-1));
        Sstate.add("Resumed");
        Toast.makeText(this,"Submit is Resumed",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle","State of activity Submit is Paused from "+Sstate.get(Sstate.size()-1));
        Sstate.add("Paused");
        Toast.makeText(this,"Submit is Paused",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle","State of activity Submit is Stopped from "+Sstate.get(Sstate.size()-1));
        Sstate.add("Stopped");
        Toast.makeText(this,"Submit is Stopped",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle","State of activity Submit is Destroyed from "+Sstate.get(Sstate.size()-1));
        Sstate.add("Destroyed");
        Toast.makeText(this,"Submit is Destroyed",Toast.LENGTH_SHORT).show();
    }


}
