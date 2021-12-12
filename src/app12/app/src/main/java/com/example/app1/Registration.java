package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button Register_Button = (Button) findViewById(R.id.registration_button);

        /* 1.
         * Method: Setup Button Click Event
         * Purpose: Setup Click event directs to setup page.
         * Mechanism: If button clicked, user is redirected to the Setup page using
         * start activity method call.
         * TODO: validation logic
         * */
        Register_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(Registration.this, "Success!",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Registration.this, Setup.class));
            }
        });

    }
}



