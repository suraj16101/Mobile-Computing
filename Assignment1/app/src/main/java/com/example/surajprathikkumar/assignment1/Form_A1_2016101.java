package com.example.surajprathikkumar.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Form_A1_2016101 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__a1_2016101);
        Log.i("lifecycle", " State of activity Assignment1 is Created");
        Toast.makeText(this,"Assignment1 is Created",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle","State of activity Assignment1 is Started");
        Toast.makeText(this,"Assignment1 is Started",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle","State of activity Assignment1 is Resumed from Pause");
        Toast.makeText(this,"Assignment1 is Resumed",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle","State of activity Assignment1 is Paused from Resume");
        Toast.makeText(this,"Assignment1 is Paused",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("lifecycle","State of activity Assignment1 is Stopped from Pause");
        Toast.makeText(this,"Assignment1 is Stopped",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle","State of activity Assignment1 is Destroyed");
        Toast.makeText(this,"Assignment1 is Destroyed",Toast.LENGTH_SHORT).show();
    }

    public void onButtonClear(View v)
    {
        EditText e1=findViewById(R.id.Name);
        e1.setText("");
        EditText e2=findViewById(R.id.rollno);
        e2.setText("");
        EditText e3=findViewById(R.id.batch);
        e3.setText("");
        EditText e4=findViewById(R.id.course1);
        e4.setText("");
        EditText e5=findViewById(R.id.course2);
        e5.setText("");
        EditText e6=findViewById(R.id.course3);
        e6.setText("");
        EditText e7=findViewById(R.id.course4);
        e7.setText("");

    }

    public void onButtonSubmit(View v)
    {
        Bundle bundle = new Bundle();
        EditText n1=findViewById(R.id.Name);
        String n2=n1.getText().toString();
        bundle.putString("n1",n2);

        EditText r1=findViewById(R.id.rollno);
        String r2=r1.getText().toString();
        bundle.putString("r1",r2);

        EditText b1=findViewById(R.id.batch);
        String b2=b1.getText().toString();
        bundle.putString("b1",b2);

        EditText c1=findViewById(R.id.course1);
        String c12=c1.getText().toString();
        bundle.putString("c1",c12);

        EditText c2=findViewById(R.id.course2);
        String c22=c2.getText().toString();
        bundle.putString("c2",c22);

        EditText c3=findViewById(R.id.course3);
        String c32=c3.getText().toString();
        bundle.putString("c3",c32);

        EditText c4=findViewById(R.id.course4);
        String c42=c4.getText().toString();
        bundle.putString("c4",c42);


        Intent intent =new Intent(Form_A1_2016101.this,Submit_A1_2016101.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }



}
