package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Set_Route extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_route);

        //only button I'll implement
        Button Back_Button = (Button) findViewById(R.id.Back_Button);

        /* 1.
         * Method: Back Button Click Event
         * Purpose: Redirects to main page.
         * Mechanism: Click event that redirects user.
         * */
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Set_Route.super.onBackPressed();
            }
        });

        Button Start_Nav = (Button) findViewById(R.id.Start_Navigation_Button);
        Start_Nav.setEnabled(false);
        Start_Nav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Set_Route.this, Navigation.class));
            }
        });

        ListView lv = (ListView)findViewById(R.id.Routes_List);
        String[] routeList = {"Main Floor Bathroom","Basement Bathroom","Second Floor Bathroom", "Basement Bedroom", "Main Floor Bedroom", "Second Floor Bedroom 1", "Second Floor Bedroom 2", "Main Floor Office", "Second Floor Office", "Main Floor Kitchen", "Main Floor Living Room", "Basement Living Room"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, routeList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Start_Nav.setEnabled(true);
            }
        });
    }
}