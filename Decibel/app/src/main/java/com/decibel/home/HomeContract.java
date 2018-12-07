package com.decibel.home;

import com.decibel.model.Room;

import java.util.ArrayList;

public interface HomeContract {

    interface RegionView {

        void fetchRoomsError();

        void fetchRoomsSuccess(ArrayList<Room> rooms);

        void fetchRegionsError(String message);

        void fetchRegionsSuccess(ArrayList<String> regions);
    }

    interface RegionPresenter {

        void fetchRooms(String region);

        void fetchRegions();
    }

    interface NoiseView {

        void fetchRoomsError();

        void fetchRoomsSuccess(ArrayList<Room> rooms);
    }

    interface NoisePresenter {

        void fetchRoomsWithLeastNoise(int cap);
    }

    interface VacantView {

        void fetchRoomsError();

        void fetchRoomsSuccess(ArrayList<Room> rooms);
    }

    interface VacantPresenter {

        void fetchRoomsWithMostVacantSeats(int cap);
    }

    interface HomeView {

    }

    interface HomePresenter {

        void storeRooms();
    }

}
