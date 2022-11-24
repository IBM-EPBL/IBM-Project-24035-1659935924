package com.example.conzon.backgroud;

public class current_location {
    private static double lati;
    private static double longi;

    public static double getLati() {
        return lati;
    }

    public static void setLati(double lati) {
        current_location.lati = lati;
    }

    public static void setLongi(double longi) {
        current_location.longi = longi;
    }

    public static double getLongi() {
        return longi;
    }

}
