package com.example.conzon.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class containment_API_request {
    private static ArrayList<LatLng> locationArrayList = new ArrayList<>();

    public static void setLocationArrayList(ArrayList<LatLng> locationArrayList) {
        containment_API_request.locationArrayList = locationArrayList;
    }

    public static ArrayList<LatLng> getLocationArrayList() {
        return locationArrayList;
    }

    public static void add_condetails(double lati,double longi)
    {
        locationArrayList.add(new LatLng(lati,longi));
    }
    public static void clear()
    {
        locationArrayList.clear();
    }
}
