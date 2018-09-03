package com.example.surajprathikkumar.assignment2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.surajprathikkumar.assignment2.R.id;




public class Music_song extends BaseAdapter
{
    public Context context;
    public ArrayList<String> song;
    public ArrayList<Integer> musiclist;
    public int layout;




    public Music_song(Context context,int layout,ArrayList<String> song,ArrayList<Integer> musiclist)
    {
        this.context=context;
        this.layout=layout;
        this.song=song;
        this.musiclist=musiclist;
    }
    public class Layout{
        TextView songname;
        Button play1,pause1;
    }



    @Override
    public int getCount() {
        return song.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        final Layout lay;
        if(view==null){
            lay=new Layout();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =layoutInflater.inflate(layout,null);
            lay.songname = (TextView) view.findViewById(id.songname);
            lay.play1 = (Button) view.findViewById(id.play1);
            lay.pause1 = (Button) view.findViewById(id.pause1);

            view.setTag(lay);
        } else
        {
            lay = (Layout) view.getTag();
        }

        final String song_name = song.get(i);
        final Integer rawval = musiclist.get(i);

        lay.songname.setText(song_name);
        final Intent in = new Intent(view.getContext(), playservice.class);

        final Bundle bundle=new Bundle();
        bundle.putInt("key",rawval);

        lay.play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                in.putExtras(bundle);
                context.startService(in);


            }
        });

        lay.pause1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.stopService(in);

            }
        });

        return view;
    }

}

