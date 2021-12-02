package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Navigation extends AppCompatActivity {

    private class RouteStep{
        public float relLatitude;
        public float relLongitude;

        public RouteStep(float relLat, float relLong){
            this.relLatitude = relLat;
            this.relLongitude = relLong;
        }
    }

    private Queue<RouteStep> route;
    // in degrees clockwise from north
    private float heading;
    private float latPos;
    private float longPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        route = new LinkedBlockingQueue<RouteStep>();
        route.add(new RouteStep(0.0f,0.0f));
        route.add(new RouteStep(0.0f,0.0f));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }
}