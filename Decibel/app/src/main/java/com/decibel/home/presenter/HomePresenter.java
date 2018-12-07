package com.decibel.home.presenter;

import android.support.annotation.NonNull;

import com.decibel.home.HomeContract;
import com.decibel.model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomePresenter implements HomeContract.HomePresenter {

    private HomeContract.HomeView mHomeView;

    public HomePresenter(HomeContract.HomeView homeView) {
        mHomeView = homeView;
    }

    @Override
    public void storeRooms() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Room> rooms = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                Room room = doc.toObject(Room.class);
                                room.setId(doc.getId());
                                rooms.add(room);
                            }

                        }
                    }
                });
    }
}