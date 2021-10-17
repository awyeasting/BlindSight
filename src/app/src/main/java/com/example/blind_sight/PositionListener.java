package com.example.blind_sight;

import android.hardware.SensorEventListener;

public abstract class PositionListener implements SensorEventListener {
    protected Vector3 pos;
    protected Vector3 vel;
    protected Vector3 acc;

    PositionListener() {
        pos = new Vector3();
        vel = new Vector3();
        acc = new Vector3();
    }

    public Vector3 getPosition() {
        return pos;
    }

    public Vector3 getVelocity() {
        return vel;
    }

    public Vector3 getAcceleration() {
        return acc;
    }
}
