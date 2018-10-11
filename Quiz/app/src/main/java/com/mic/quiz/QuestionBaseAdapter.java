package com.mic.quiz;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class QuestionBaseAdapter extends RecyclerView.Adapter<QuestionBaseAdapter.MyViewHolder>
{
    ArrayList<Question_details> qd;
    Context context;

    public  void setQd(ArrayList<Question_details> qd)
    {
        this.qd=qd;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.item_button, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;

    }





    onItemClickListner onItemClickListner;

    public void setOnItemClickListner(onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner{
        void onClick(int position, int id,String str);//pass your object types.
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i)
    {
        Question_details question_details=qd.get(i);
        viewHolder.question_button.setText("Question " + qd.get(i).ID);

        viewHolder.question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onItemClickListner.onClick(i,qd.get(i).ID,qd.get(i).question);
            }
        });

    }




    @Override
    public int getItemCount() {
        return qd.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        Button question_button;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.question_button = itemView.findViewById(R.id.question_button);

        }
    }
}
