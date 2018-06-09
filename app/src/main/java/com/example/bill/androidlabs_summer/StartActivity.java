package com.example.bill.androidlabs_summer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected final String ACTIVITY_NAME = "StartActivity";
    Button startButton;
    Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In OnCreate()");
         startButton = (Button) findViewById(R.id.button);
         chatButton = (Button) findViewById(R.id.chatButton);
        startButton.setOnClickListener(t->{
            Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
            startActivityForResult(intent, 50);
        });
        chatButton.setOnClickListener(t->{
            Log.i(ACTIVITY_NAME, "User clicked start chat");
            Intent intent = new Intent(StartActivity.this, ChatWindow.class);
            startActivity(intent);
        });
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 50){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.OnActivityResult");
        }
        if (responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_SHORT);
            toast.show();
        }
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
