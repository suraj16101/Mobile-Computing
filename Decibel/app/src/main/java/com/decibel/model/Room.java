package com.decibel.model;

public class Room {

    private String id;
    private String room;
    private String region;
    private String mac;
    private int vacancy;
    private int noiseStrength;
    private int n_devices;

    public Room() {

    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    public int getNoiseStrength() {
        return noiseStrength;
    }

    public void setNoiseStrength(int noiseStrength) {
        this.noiseStrength = noiseStrength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getN_devices() {
        return n_devices;
    }

    public void setN_devices(int n_devices) {
        this.n_devices = n_devices;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", room='" + room + '\'' +
                ", region='" + region + '\'' +
                ", mac='" + mac + '\'' +
                ", vacancy=" + vacancy +
                ", noiseStrength=" + noiseStrength +
                ", n_devices=" + n_devices +
                '}';
    }
}
