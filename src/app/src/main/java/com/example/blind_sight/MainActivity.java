package com.example.blind_sight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private CameraManager cameraManager;

    private Sensor accelerometer;
    private PositionListener aListener;
    private Sensor magnetometer;
    private PositionListener mListener;

    private Thread posUpdateThread;

    private Vector3 pos;
    private Vector3 vel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set position and velocity to 0
        this.pos = new Vector3();
        this.vel = new Vector3();

        // Get Sensor Manager
        this.sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);


        // Get accelerometer
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // Accelerometer exists
            this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            aListener = new AccelerometerListener(this);
            sensorManager.registerListener(aListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Create thread for position updating

        // Get Camera Manager
        this.cameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        //*/
        this.UpdatePosition();
    }

    public void UpdatePosition() {
        TextView aT = (TextView) findViewById(R.id.acceleration_text);

        //aT.setText("Updated Acceleration");
        double aMag = Math.sqrt(aListener.acc.x * aListener.acc.x + aListener.acc.y * aListener.acc.y + aListener.acc.z * aListener.acc.z);
        aT.setText("Acceleration: " + aMag + " (" + aListener.acc.x + ", " + aListener.acc.y + ", " + aListener.acc.z + ")");
    }
}