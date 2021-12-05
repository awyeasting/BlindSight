package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
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

    private ArrayList<RouteStep> route;

    // User position information
    private float heading; // in degrees clockwise from north
    private float relLatPos;
    private float relLongPos;

    // Navigation information
    private float dist;
    private float maxStepDist;
    private float turnHeading;
    String turnDirection;
    private boolean arrived;
    Handler navHandler;

    // User navigation simulation information
    private long lastTime;

    // Navigation communication interfaces
    TextToSpeech tts;
    TextView directionText; // Mostly
    ProgressBar dbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initializeRoute();

        openCommunicationInterfaces();

        runNavigation();

        // Set handler for navigation exit
        Button b1 = (Button)findViewById(R.id.Exit_Navigation);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Navigation.super.onBackPressed();
                navHandler.removeCallbacksAndMessages(null);
            }
        });
    }

    private void initializeRoute() {
        // Create route for debugging
        route = new ArrayList<RouteStep>();
        //route.add(new RouteStep(0.0f,0.0f));
        route.add(new RouteStep(0.0f,50.0f));
        route.add(new RouteStep(50.0f,50.0f));
        route.add(new RouteStep(50.0f,70.0f));

        // Zero user position
        relLatPos = 0.0f;
        relLongPos = 0.0f;
        heading = 90.0f;
    }

    private void openCommunicationInterfaces() {
        directionText = (TextView) findViewById(R.id.Route_Step);
        dbar = (ProgressBar)findViewById(R.id.NavProgress);
        tts = new TextToSpeech(Navigation.this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                    tellNextDirection();
                } else {
                    Toast.makeText(Navigation.this, "Text to speech not supported on this device",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void runNavigation() {
        // Zero navigation information
        arrived = false;
        maxStepDist = (float) Math.sqrt(Math.pow(route.get(0).relLatitude - relLatPos,2) + Math.pow(route.get(0).relLongitude - relLongPos,2))-5.0f;

        // Create and run handler for navigation information to run on UI thread every 10 milliseconds
        navHandler = new Handler();
        Runnable updatePosition = new Runnable() {
            @Override
            public void run() {
                checkArrived();

                if (!arrived)
                    navHandler.postDelayed(this, 10);
            }
        };
        lastTime = System.currentTimeMillis();
        navHandler.post(updatePosition);
    }

    private void moveVirtualUser() {
        // Calculate time since last move
        long curTime = System.currentTimeMillis();
        long elapsed = curTime - lastTime;
        lastTime = curTime;

        // Distance traveled in this virtual move step
        float distanceTraveled = 5.86667f * ((float) elapsed)/1000.0f; // average walking speed = 5.86667 feet per second

        // Set vector towards next route step
        float dirLat = route.get(0).relLatitude - relLatPos;
        float dirLong = route.get(0).relLongitude - relLongPos;

        // Calculate distance to next route step and travel up to that distance towards the step
        float dirDist = (float)Math.sqrt(dirLat * dirLat + dirLong * dirLong);
        if (distanceTraveled > dirDist) {
            distanceTraveled = dirDist;
        }
        relLatPos += dirLat * distanceTraveled/dirDist;
        relLongPos += dirLong * distanceTraveled/dirDist;
    }

    private void checkArrived(){
        moveVirtualUser();

        float arrivalFuzziness = 5.0f;  // Deviation from perfect position that is acceptable
                                        // not a constant because some situations may call for more accuracy than others in the future

        // Calculate distance to next route step and use that to set progress from the last step to the next step
        dist = (float) Math.sqrt(Math.pow(route.get(0).relLatitude - relLatPos,2) + Math.pow(route.get(0).relLongitude - relLongPos,2));
        dbar.setProgress((int)(1000*(1-(dist-arrivalFuzziness)/maxStepDist)));

        // Check distance from next route position to determine step arrival
        if (dist <= arrivalFuzziness) {
            if (route.size() > 1) {
                route.remove(0);
                maxStepDist = (float) Math.sqrt(Math.pow(route.get(0).relLatitude - relLatPos,2) + Math.pow(route.get(0).relLongitude - relLongPos,2)) - arrivalFuzziness;
                dist = maxStepDist;
                tellNextDirection();
            } else {
                destinationArrived();
            }
        }

        updateDirection();
    }

    private void tellNextDirection() {
        // If at a turn then tell user to turn before telling them the next direction
        if (turnDirection != null) {
            Toast.makeText(getApplicationContext(), "Turn " + turnDirection, Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak("Turn " + turnDirection, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak("Turn " + turnDirection, TextToSpeech.QUEUE_FLUSH, null);
            }

            // Turn the virtual user
            heading = turnHeading;
        }

        int userDistance = Math.round(dist);
        String directionMessage;

        // If the next step is the destination no need for angle calculation because user just moves forward
        if (route.size() < 2) {
            directionMessage = "Your destination is in " + userDistance + " feet";
        }
        else {
            // Get direction vector of the next turn to calculate the heading after route step
            float ndist = (float) Math.sqrt(Math.pow(route.get(1).relLatitude - relLatPos, 2) + Math.pow(route.get(1).relLongitude - relLongPos, 2));
            float dirx = (route.get(1).relLatitude - route.get(0).relLatitude) / ndist;
            float diry = (route.get(1).relLongitude - route.get(0).relLongitude) / ndist;

            // Convert from direction vector to heading in degrees clockwise from north
            // dirx = sin(theta), diry = cos(theta)
            // if asin(sin(theta)) < 0 -> theta = 360 - acos(cos(theta))
            // else -> theta = acos(cos(theta))
            if (Math.asin(dirx) < 0.0f)
                turnHeading = 360.0f - (float) Math.toDegrees(Math.acos(diry));
            else
                turnHeading = (float) Math.toDegrees(Math.acos(diry));

            // Left if turnHeading - current heading < 0 degrees
            turnDirection = ((turnHeading - heading) < 0.0f ? "left" : "right");

            directionMessage = "Turn " + turnDirection + " in " + userDistance + " feet";
        }

        // Communicate the next direction
        directionText.setText(directionMessage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(directionMessage, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            tts.speak(directionMessage, TextToSpeech.QUEUE_ADD,null);
        }
    }

    private void destinationArrived() {
        // Communicate arrival message
        arrived = true;
        String completionMessage = "Navigation Complete. You are now at the destination";
        Toast.makeText(getApplicationContext(), completionMessage, Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(completionMessage, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(completionMessage, TextToSpeech.QUEUE_FLUSH,null);
        }
        directionText.setVisibility(View.INVISIBLE);
        findViewById(R.id.Route_Step_Header).setVisibility(View.INVISIBLE);
        findViewById(R.id.Arrived).setVisibility(View.VISIBLE);
    }

    private void updateDirection() {
        if (!arrived) {
            int userDistance = 5 * (Math.round(dist) / 5);
            String directionMessage;
            if (route.size() > 1)
                directionMessage = "Turn " + turnDirection + " in " + userDistance + " feet";
            else
                directionMessage = "Your destination is in " + userDistance + " feet";
            directionText.setText(directionMessage);
        }
    }
}