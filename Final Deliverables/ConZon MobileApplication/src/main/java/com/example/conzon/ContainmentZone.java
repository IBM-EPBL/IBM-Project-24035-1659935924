package com.example.conzon;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.conzon.backgroud.alerting;
import com.example.conzon.backgroud.current_location;
import com.example.conzon.models.containment_API_request;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ContainmentZone extends AppCompatActivity implements OnMapReadyCallback {

    private static final String CHANNEL_ID = "ContainmentZone Alert";
    private static final int NOTIFICATION_ID = 01;
    @SuppressLint("StaticFieldLeak")
    static NotificationCompat.Builder builder;
    @SuppressLint("StaticFieldLeak")
    static NotificationManagerCompat notificationManagerCompat;
    static NotificationManager notificationManager;
    private GoogleMap mMap;
    WorkRequest uploadWorkRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containment_zone);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        uploadWorkRequest = new OneTimeWorkRequest.Builder(alerting.class).build();
        builder = new NotificationCompat.Builder(ContainmentZone.this, CHANNEL_ID);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        WorkManager.getInstance(this).enqueue(uploadWorkRequest);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        process();
    }

    public void process() {
        // creating array list for adding all our locations.
        ArrayList<LatLng> locationArrayList = containment_API_request.getLocationArrayList();
        LatLng current = new LatLng(current_location.getLati(), current_location.getLongi());
        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addCircle(new CircleOptions()
                    .center(locationArrayList.get(i))
                    .radius(20)
                    .strokeColor(Color.RED)
                    .fillColor(Color.YELLOW));
            // below lin is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.8f));
            mMap.setMinZoomPreference(16);

            // below line is use to move our camera to the specific location.
        }
        mMap.addMarker(new MarkerOptions().position(current));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    }

    public static void sentAlert(String message) {
        createNotificationChannel();

        builder.setSmallIcon(R.drawable.ic_alert);
        builder.setContentTitle("ConZon Alert");
        //description
        builder.setContentText(message);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    private static void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//swipe notifications
            CharSequence name = "ConZon Alert";
            String description = "ALERTING WHEN USER ENTER INTO CONTAINMENT ZONE";
//importance of your notification
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationchannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationchannel.setDescription(description);

            notificationManager.createNotificationChannel(notificationchannel);

        }
    }

    @Override
    public void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }
}
