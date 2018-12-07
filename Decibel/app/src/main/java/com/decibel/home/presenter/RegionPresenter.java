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
import java.util.HashMap;
import java.util.Map;

public class RegionPresenter implements HomeContract.RegionPresenter {

    private HomeContract.RegionView mRegionView;

    public RegionPresenter(HomeContract.RegionView regionView) {
        mRegionView = regionView;
    }

    @Override
    public void fetchRooms(String region) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
                .whereEqualTo("region", region)
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
//                            mRegionView.fetchRoomsSuccess(rooms);
                            fetchNoise(rooms);
                        } else {
                            mRegionView.fetchRoomsError();
                        }
                    }
                });
    }

    @Override
    public void fetchRegions() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("regions").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> regions = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                regions.add(doc.getId());
                            }
                            mRegionView.fetchRegionsSuccess(regions);
                        } else {
                            String message = "";
                            mRegionView.fetchRegionsError(message);
                        }
                    }
                });
    }

    private void fetchNoise(final ArrayList<Room> rooms) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> data = doc.getData();
                                String mac = (String) data.get("room");
                                Long noise = (Long) data.get("noise");

                                if (hashMap.containsKey(mac)) {
                                    ArrayList<Integer> noises = hashMap.get(mac);
                                    noises.add(noise.intValue());
                                } else {
                                    hashMap.put(mac, new ArrayList<Integer>());
                                    ArrayList<Integer> noises = hashMap.get(mac);
                                    noises.add(noise.intValue());
                                }

                            }

                            for (Room r : rooms) {
                                if (hashMap.containsKey(r.getMac())) {
                                    ArrayList<Integer> noises = hashMap.get(r.getMac());
                                    int dc = noises.size();
                                    int noise = 0;
                                    for (Integer i : noises) {
                                        noise += i;
                                    }
                                    noise /= dc;
                                    r.setN_devices(dc);
                                    r.setNoiseStrength(noise);
                                }
                            }

                            mRegionView.fetchRoomsSuccess(rooms);
                        } else {
                            String message = "";
                            mRegionView.fetchRoomsError();
                        }
                    }
                });
    }
}
