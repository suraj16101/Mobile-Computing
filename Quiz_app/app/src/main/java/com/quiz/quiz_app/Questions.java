package com.quiz.quiz_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Questions extends Fragment {

    public Questions() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi=inflater.inflate(R.layout.fragment_questions, container, false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        Main_A3_2016101 activity = (Main_A3_2016101)getActivity();

        DatabaseHelper myDb =new DatabaseHelper(activity.getBaseContext());
        RecyclerView rv = (RecyclerView) vi.findViewById(R.id.recycle_view);
        ArrayList<Question_details> question_details=new ArrayList();
        rv.setLayoutManager(linearLayoutManager);
        for(int i=1;i<myDb._getRows()+1;i++)
        {
            Question_details q=new Question_details(i,myDb.selectValue(i));

            question_details.add(q);
        }



        QuestionBaseAdapter questionBaseAdapter=new QuestionBaseAdapter();
        questionBaseAdapter.setQd(question_details);
        rv.setAdapter(questionBaseAdapter);

        questionBaseAdapter.notifyDataSetChanged();
        questionBaseAdapter.setOnItemClickListner(new QuestionBaseAdapter.onItemClickListner() {
            @Override
            public void onClick(int position,int id,String str) {
                Log.i("Question",""+str);
                Bundle b = new Bundle();
                b.putString("Key",str);
                b.putInt("YourKey", id);

                Details fragB = new Details();
                fragB.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.fragment2, fragB).commit();

            }
        });



        return vi;
    }


}
