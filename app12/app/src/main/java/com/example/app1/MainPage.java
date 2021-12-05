package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();

        /*
        * Setup Buttons for all the options on the main page.
        * General Syntax: Type Name = (TypeCast) find id of buttons in xml.
        * */
        Button Route_Button = (Button) findViewById(R.id.route_button);
        //Button Nav_Button = (Button) findViewById(R.id.nav_button);
        Button Collision_Button = (Button) findViewById(R.id.collision_button);
        Button Setup_Button = (Button) findViewById(R.id.setup_button);
        Button Exit_Button = (Button) findViewById(R.id.exit_button);

        /* 1.
        * Method: Set Route Click Event
        * Purpose: Redirects to set route page.
         * Mechanism: Click event that redirects user.
        * */
        Route_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, Set_Route.class));
            }
        });

        /* 2.
         * Method: Collision Click Event
         * Purpose: Turns on/off collision detection mode
         * Mechanism: click then show message
         */
        Collision_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainPage.this, Navigation.class));
                Toast.makeText(MainPage.this, "Success!",Toast.LENGTH_SHORT).show();
            }
        });

        /* 3.
         * Method: Collision Click Event
         * Purpose: Turns on/off collision detection feature.
         * Mechanism:  Click event that redirects user.
         * */
        Collision_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainPage.this, Collision.class));
            }
        });

        /* 4.
         * Method: Setting Click Event
         * Purpose: Redirects to set up page.
         * Mechanism:  Click event that redirects user.
         * */
        Setup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, Setup.class));
            }
        });

        /* 5.
         * Method: Exit App Click Event
         * Purpose: Redirects to login page.
         * Mechanism:  Click event that redirects user.
         * */
        Exit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, Login_Page.class));
            }
        });
    }
}