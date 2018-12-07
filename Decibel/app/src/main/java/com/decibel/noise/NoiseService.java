package com.decibel.noise;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NoiseService extends Service {

    private static final String TAG = NoiseService.class.getName();
    private static final int REST_TIME = 100;
    private static final int INTERVAL_TIME = 1000 * 30;
    private static final int ONE_GO_TIME = 10_000;

    private Thread thread;
    private MediaRecorder mediaRecorder = null;
    private WifiManager mWifiManager;

    public NoiseService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startJob();
            }
        });
        thread.start();

        return START_STICKY;
    }

    private void startJob() {
        //do job here
        Log.d(TAG, "Recording Audio..");
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.reset();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile("/dev/null"); // Not saving the audio

            int runtime = ONE_GO_TIME / REST_TIME;
            int sum = 0;
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();

                long currTime = System.currentTimeMillis();
                for (int i = 0; i < runtime; i++) {
                    int raw_amp_val = getAmplitudeLog();
                    final int amp_val;
                    if (raw_amp_val < 0) {
                        amp_val = 0;
                    } else {
                        amp_val = raw_amp_val;
                    }

                    sum += amp_val;
                    String amp_val_string = amp_val + "";
//                    Log.d(TAG, "AMP LOG: " + amp_val_string + " AMP : " + getAmplitude()
//                            + " AMP EMA: " + getAmplitudeEMA());

                    try {
                        // Sleep for next value
                        Thread.sleep(REST_TIME);
                    } catch (InterruptedException e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
                Log.d(TAG, "Ran for " + (System.currentTimeMillis() - currTime) / 1000 + " secs");
            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            }

            // Upload to Firebase
            mWifiManager = (WifiManager) getApplicationContext().getSystemService(
                    Context.WIFI_SERVICE);

            if (mWifiManager.isWifiEnabled()) {
                final WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
                if (wifiInfo.getNetworkId() == -1) {
                    Log.d(TAG, "You are not connected to any access point");
                } else {

                    String bssid = wifiInfo.getBSSID();
                    String macAdrres = getMacAddr();
                    Log.d(TAG, bssid + " " + getMacAddr());
//                    bssid = "c4:0a:cb:25:b5:50";

                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    final int avg = sum / runtime;
                    Log.d(TAG, "Noise: " + avg);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("room", bssid);
                    hashMap.put("noise", avg);

                    db.collection("users")
                            .document(macAdrres)
                            .set(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //job completed. Rest
                                    Log.d(TAG, "DATA COLLECTED and UPLOADED. Stopping MIC..");

                                }
                            });
                    stopMIC();
                    try {
                        Thread.sleep(INTERVAL_TIME);
                    } catch (InterruptedException e) {
                        Log.d(TAG, e.getMessage());
                    }

                    //do job again
                    startJob();
                }
            } else {
                Log.d(TAG, "Wifi not enabled");
            }
        }
    }

    public double getAmplitude() {
        if (mediaRecorder != null) {
            return (mediaRecorder.getMaxAmplitude() / 2700.0);
        } else {
            return 0;
        }
    }

    public double getAmplitudeEMA() {
        double amp = getAmplitude();
        double EMA_FILTER = 0.6;
        double mEMA = 0.0;
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }

    private int getAmplitudeLog() {
        double amp_ref = 3.27;
        if (mediaRecorder != null) {
            double y = 20 * Math.log10(mediaRecorder.getMaxAmplitude() / amp_ref);
            return (int) y;
        } else {
            return -1;
        }
    }

    private void stopMIC() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
