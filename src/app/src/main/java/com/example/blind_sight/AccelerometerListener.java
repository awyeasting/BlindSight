package com.example.blind_sight;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class AccelerometerListener extends PositionListener {

    MainActivity cb;

    AccelerometerListener(MainActivity activity) {
        super();
        cb = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        this.acc.x = sensorEvent.values[0];
        this.acc.y = sensorEvent.values[1];
        this.acc.z = sensorEvent.values[2];

        SensorManager sm = (SensorManager) cb.getSystemService(Context.SENSOR_SERVICE);
        sm.getOrientation();
        cb.UpdatePosition();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
