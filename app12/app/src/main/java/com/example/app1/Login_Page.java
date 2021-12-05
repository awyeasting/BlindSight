package com.example.app1;
/*
* Riley Hunter
* CARZ
* Basic UI
* Notes: Not focused on security for now
* */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Page extends AppCompatActivity {

    private EditText Email_Prompt;
    private EditText Password_Prompt;

    //Test profile
    private String Email = "non@yahoo.com";
    private String Password = "qwerty";

    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // How to create event with Button... Login button.
        // Var name             object    find          ID as named in xml
        Button Login_Button = (Button) findViewById(R.id.Login_Button);
        Button Register_Button = (Button) findViewById(R.id.Register_Button);

        //Same as button for email and password text boxes
        Email_Prompt = (EditText) findViewById(R.id.Email_Prompt);
        Password_Prompt = (EditText) findViewById(R.id.Password_Prompt);

        /* 1.
         * Method: Register Button Click Event
         * Purpose: Register Click event directs to register page.
         * Mechanism: If button clicked, user is redirected to the Registration page using
         * start activity method call.
         * */
        Register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //directs to new p[age                  from                    to
                startActivity(new Intent(Login_Page.this, Registration.class));
            }
        });


        /* 2.
         * Method: Login Button Click Event
         * Purpose: Login Click event directs to main page if correct credentials, otherwise error message.
         * Mechanism: If is valid returns true, the event redirects user to new page using start activity
         * method call, if not, error message.
         * TODO 1 (stretch goal): Method checks how many times user enters wrong password and locks out
         *  login for particular user.
         * */
        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo: Change from hardcode to database based - not a priority at all

                //Convert to string
                String Input_Email = Email_Prompt.getText().toString();
                String Input_Password = Password_Prompt.getText().toString();

                //validate
                isValid = true; // For testing purposes
                //isValid = validate(Input_Email, Input_Password);

                //if credentials are right
                if (isValid){
                    // inside if statement
                    Toast.makeText(Login_Page.this, "Success!",Toast.LENGTH_SHORT).show();
                    // redirect to main page -priority
                    startActivity(new Intent(Login_Page.this, MainPage.class));

                }
                else{
                    //if not
                    Toast.makeText(Login_Page.this, "Try again!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* 3.
     * Method: Validate Credentials
     * Purpose: Checks if the corresponding email and password matches.
     * Mechanism: If the email and password match the given hardcoded values "non@yahoo.com" and
     * "qwerty", the method returns true. If not, the method returns false.
     * TODO 1 (stretch goal): Fix method so password and email is not hardcoded... Maybe through a database.
     * */
    private boolean validate(String name, String password){
        if (name.equals(Email) && password.equals(Password)){
            return true;
        }
        return false;
    }
}