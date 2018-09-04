package com.fime.fsw.codov2;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Geofication {

    private Map<String, Shape> zones = new HashMap<>();
    private Point currentLocation = new Point(0,0);
    private GeoficationListener geoListener;

    public LocationListener locationListener;

    public Geofication() {
        // long x, lat y
        zones.put("zone1", new Shape(new Point[] {
                new Point(-100.39674734851172, 25.767496219428654),
                new Point(-100.39688749393275, 25.767632704158704),
                new Point(-100.39678232301435, 25.76773167960638),
                new Point(-100.39659792114458, 25.767593754468663)
        }));
        geoListener = null;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("TESTEO", "ONLOCATIONCHANGED");
                currentLocation.setLocation(
                        location.getLongitude(),
                        location.getLatitude()
                );

                geoListener.onLocationReady();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };
    }

    public void setGeoListener(GeoficationListener listener) {
        this.geoListener = listener;
    }

    public boolean isInside(String areaName) {
        Shape area = zones.get("zone1");
        int count = 0;

        for(int i = 0; i < area.points.length; i++) {

            Point a = area.points[i];
            Point b = area.points[(i + 1) % area.points.length];

            if(this.rayCasting(a, b, this.currentLocation)) {
                count++;
            }

        }

        return ((count % 2) == 0) ? false : true;
    }

    public interface GeoficationListener {
        public void onLocationReady();
    }

    /*
    RayCasting Algorithm
    https://en.wikipedia.org/wiki/Point_in_polygon
     */
    private boolean rayCasting(Point a, Point b, Point target) {
        if(a.y <= b.y) {
            if(target.y <= a.y || target.y > b.y || target.x >= a.x && target.x >= b.x) {
                return false;
            } else if(target.x < a.x && target.x < b.x) {
                return true;
            } else {
                return (target.y - a.y) / (target.x - a.x) > (b.y - a.y) / (b.x - a.x);
            }
        } else {
            return rayCasting(b, a, target);
        }
    }

    public String getCoords() {
        return "Lon: " + currentLocation.x + " Lat: "+ currentLocation.y;
    }

    private class Shape {
        Point[] points;

        public Shape(Point[] points) {
            this.points = points;
        }
    }

    private class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}