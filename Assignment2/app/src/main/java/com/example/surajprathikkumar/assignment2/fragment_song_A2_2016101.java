package com.example.surajprathikkumar.assignment2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;



public class fragment_song_A2_2016101 extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_fragment_song__a2_2016101, container, false);

        ListView songList = (ListView) vi.findViewById(R.id.list);
        ArrayList<String> song = new ArrayList<String>();
        ArrayList<Integer> musicfile=new ArrayList<Integer>();

        song.add("call me a spaceman");
        musicfile.add(R.raw.call_me_a_spaceman);

        song.add("fireflies");
        musicfile.add(R.raw.fireflies);

        song.add("just the way you are");
        musicfile.add(R.raw.just_the_way_you_are);

       Music_song music_song=new Music_song(this.getContext(), R.layout.music_a2_2016101,song,musicfile);

        songList.setAdapter(music_song);
        return vi;
    }


}
