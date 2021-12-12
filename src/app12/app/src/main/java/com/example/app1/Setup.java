package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button Setup_Button = (Button) findViewById(R.id.Setup_Button);

        /* 1.
         * Method: Main Page Button Click Event
         * Purpose: Main Page Click event directs to setup page.
         * Mechanism: If button clicked, user is redirected to the Main Page using
         * start activity method call.
         * TODO: validation logic
         * */
        Setup_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(Setup.this, "Success!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Setup.this, MainPage.class));
            }
        });
    }
}