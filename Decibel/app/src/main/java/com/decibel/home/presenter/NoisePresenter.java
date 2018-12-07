package com.decibel.home.presenter;
import android.support.annotation.NonNull;
import android.util.Log;
import com.decibel.home.HomeContract;
import com.decibel.model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NoisePresenter implements HomeContract.NoisePresenter {

    private HomeContract.NoiseView mNoiseView;

    public NoisePresenter(HomeContract.NoiseView noiseView) {
        mNoiseView = noiseView;
    }

    @Override
    public void fetchRoomsWithLeastNoise(final int cap) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("locations")
//                .orderBy("noiseStrength")
//                .orderBy("vacancy", Query.Direction.DESCENDING)
//                .limit(cap)
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
                                if(doc.getId().equals("AAA111")){
                                    Log.d("lol", room.toString());
                                }
                            }
                            fetchNoise(rooms, cap);
                        } else {
                            String message = Objects.requireNonNull(
                                    task.getException()).getMessage();
                            Log.d("FB", message);
                            mNoiseView.fetchRoomsError();
                        }
                    }
                });

    }

    private void fetchNoise(final ArrayList<Room> rooms, final int cap) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final HashMap<String, ArrayList<Integer>> hashMap = new HashMap<>();
        db.collection("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Map<String, Object> data = doc.getData();
                                Long noise = (Long) data.get("noise");
                                String mac = (String) data.get("room");

                                if (hashMap.containsKey(mac)) {
                                    ArrayList<Integer> noises = hashMap.get(mac);
                                    noises.add(noise.intValue());
                                } else {
                                    hashMap.put(mac, new ArrayList<Integer>());
                                    ArrayList<Integer> noises = hashMap.get(mac);
                                    noises.add(noise.intValue());
                                }
                            }

                            Log.d("lol", hashMap.toString());

                            for (Room r : rooms) {
                                if(r.getMac().equals("74:a2:e6:3f:69:78")){
                                    Log.d("lol", r.toString());
                                }
                                if (hashMap.containsKey(r.getMac())) {

                                    ArrayList<Integer> noises = hashMap.get(r.getMac());
                                    int dc = noises.size();
                                    float noise = 0;
                                    for (Integer i : noises) {
                                        noise += i;
                                    }
                                    noise /= 1;
                                    r.setN_devices(dc);
                                    r.setNoiseStrength((int) noise + 1);
                                    Log.d("lol2", String.valueOf(noise));
                                }
                            }

                            Collections.sort(rooms, new Comparator<Room>() {
                                @Override
                                public int compare(Room room, Room t1) {
                                    if (room.getNoiseStrength() > t1.getNoiseStrength()) {
                                        return -1;
                                    } else if (room.getNoiseStrength() < t1.getNoiseStrength()) {
                                        return +1;
                                    } else {
                                        if (room.getVacancy() > t1.getVacancy()) {
                                            return -1;
                                        } else if (room.getVacancy() < t1.getVacancy()) {
                                            return +1;
                                        } else {
                                            return 0;
                                        }
                                    }
                                }
                            });

                            mNoiseView.fetchRoomsSuccess(new ArrayList<>(rooms.subList(0, cap)));
                        } else {
                            String message = "";
                            mNoiseView.fetchRoomsError();
                        }
                    }
                });
    }
}
