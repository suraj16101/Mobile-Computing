package com.decibel.home.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.decibel.R;
import com.decibel.home.HomeContract;
import com.decibel.home.adapter.RoomsAdapter;
import com.decibel.home.presenter.NoisePresenter;
import com.decibel.model.Room;
import com.decibel.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoiseFragment extends Fragment implements HomeContract.NoiseView {

    private static final String TAG = NoiseFragment.class.getName();
    private NoisePresenter mPresenter;
    @BindView(R.id.rv_rooms)
    RecyclerView rvRooms;
    private RoomsAdapter mRoomsAdapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_noise, container, false);
        ButterKnife.bind(this, rootView);

        mPresenter = new NoisePresenter(this);

        setupRV();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRoomsAdapter == null || mRoomsAdapter.getItemCount() == 0) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading..");
            progressDialog.show();
        }
    }

    private void setupRV() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvRooms.setLayoutManager(llm);
        rvRooms.setHasFixedSize(true);
        mRoomsAdapter = new RoomsAdapter();
        rvRooms.setAdapter(mRoomsAdapter);

        mPresenter.fetchRoomsWithLeastNoise(Constants.CAP);
    }

    @Override
    public void fetchRoomsError() {
        progressDialog.cancel();
        Log.d(TAG, "Error fetching rooms");
    }

    @Override
    public void fetchRoomsSuccess(ArrayList<Room> rooms) {
        mRoomsAdapter.setData(rooms);
        progressDialog.cancel();
    }

    @OnClick(R.id.tv_reload)
    public void onReloadClicked() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Refreshing..");
        progressDialog.show();
        mPresenter.fetchRoomsWithLeastNoise(Constants.CAP);
    }

}
