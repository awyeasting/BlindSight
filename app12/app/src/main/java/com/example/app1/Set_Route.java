package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Set_Route extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_route);

        /*
        * The Following is the way the dropdown menu was created. Currently filled with hardcoded values
        * strings.xml contains these.
        * */
        Spinner Cat_Menu = (Spinner) findViewById(R.id.category_menu);
        ArrayAdapter<String> MyAdapter = new ArrayAdapter<String>(Set_Route.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        MyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cat_Menu.setAdapter(MyAdapter);

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
                startActivity(new Intent(Set_Route.this, MainPage.class));
            }
        });
    }
}