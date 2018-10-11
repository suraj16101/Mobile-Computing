package com.quiz.quiz_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Fragment {
    RadioGroup radioGroup;
    RadioButton radioButton;
    String questions;
    int ID;
    Button save;

    public Details() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View vi=inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = this.getArguments();
        radioGroup = vi.findViewById(R.id.radioGroup);
        radioGroup.setVisibility(vi.INVISIBLE);

        save=vi.findViewById(R.id.save);
        save.setVisibility(vi.INVISIBLE);


        if(bundle != null){
            final DatabaseHelper db = new DatabaseHelper(getContext());
            ID = bundle.getInt("YourKey");
            questions=bundle.getString("Key");
            TextView tv1 = vi.findViewById(R.id.question);
            tv1.setText(ID+". "+questions);

            String Value = db._getUser(ID);
            if(Value==null) {

            }
            else{
                if (Value.equalsIgnoreCase("True")) {
                    RadioButton tb = vi.findViewById(R.id.true_button);
                    tb.setChecked(true);
                }
                if (Value.equalsIgnoreCase("False")) {
                    RadioButton tb = vi.findViewById(R.id.false_button);
                    tb.setChecked(true);
                }
            }


            radioGroup.setVisibility(vi.VISIBLE);
            save.setVisibility(vi.VISIBLE);




            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if(selectedId==-1)
                    {
                        Toast.makeText(getContext(),"Choose Option",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        radioButton = (RadioButton) vi.findViewById(selectedId);

                        String txt = radioButton.getText().toString();
                        db.updateValue(ID, txt);

                        Toast.makeText(getContext(),""+radioButton.getText(),Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

        return vi;
    }



}
