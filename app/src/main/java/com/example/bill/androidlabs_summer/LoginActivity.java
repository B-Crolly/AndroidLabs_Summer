package com.example.bill.androidlabs_summer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class LoginActivity extends Activity {
    protected final String ACTIVITY_NAME = "LoginActivity";
    protected EditText emailText;
    protected Button loginButton;
    protected Switch toggleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In OnCreate()");
        emailText = (EditText) findViewById(R.id.loginBar);
        loginButton = (Button) findViewById(R.id.loginButton);
        SharedPreferences sharedPref = getSharedPreferences("DefaultEmail", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        emailText.setText(sharedPref.getString("DefaultEmail", "email@domain.com" ));
        loginButton.setOnClickListener(t->{
            editor.putString("DefaultEmail", emailText.getText().toString());
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
        });
}

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In OnResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In OnStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In OnPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In OnStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In OnDestroy()");
    }
}
