package com.decibel.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.decibel.R;
import com.decibel.model.Room;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    private ArrayList<Room> mRoomsArrayList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_room, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Room room = mRoomsArrayList.get(i);
        holder.tvRoom.setText(room.getRoom());
        int noise = room.getNoiseStrength();
        if(noise<=50){
            holder.tvNoiseStrength.setText("Low");
        }
        else if(noise<=70){
            holder.tvNoiseStrength.setText("Medium");
        }
        else{
            holder.tvNoiseStrength.setText("High");
        }

        //holder.tvNoiseStrength.setText(String.valueOf(room.getNoiseStrength()));
        holder.tvVacancy.setText(String.valueOf(room.getVacancy() - room.getN_devices()));

//        holder.tvRegion.setText(room.getRegion());
//        holder.tvFloor
    }

    @Override
    public int getItemCount() {
        if (mRoomsArrayList == null) {
            return 0;
        } else {
            return mRoomsArrayList.size();
        }
    }

    public void setData(ArrayList<Room> roomsArrayList) {
        mRoomsArrayList = roomsArrayList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_room)
        TextView tvRoom;
        @BindView(R.id.tv_region)
        TextView tvRegion;
        @BindView(R.id.tv_floor)
        TextView tvFloor;
        @BindView(R.id.tv_noise_strength)
        TextView tvNoiseStrength;
        @BindView(R.id.tv_vacancy)
        TextView tvVacancy;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
