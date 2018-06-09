package com.example.bill.androidlabs_summer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
    protected final String ACTIVITY_NAME = "ListItemsActivity";
    protected ImageButton imageButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected Switch toggle;
    protected boolean isChecked;
    protected CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In OnCreate()");
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        toggle = (Switch) findViewById (R.id.switch1);
        checkBox = (CheckBox) findViewById (R.id.checkBox);

        toggle.setOnClickListener(t->{

            setOnCheckedChange(toggle.isChecked());
        });

        imageButton.setOnClickListener(t->{
            dispatchTakePictureIntent();
        });

        checkBox.setOnClickListener(t->{
            onCheckChanged(checkBox.isChecked());
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void setOnCheckedChange(boolean isChecked) {
        CharSequence text;
        int duration;
        if (isChecked == true) {
            text = "Switch is On";
            duration = Toast.LENGTH_SHORT;
        }
        else {
            text = "Switch is Off";
            duration = Toast.LENGTH_LONG;
        }
        Toast toast = Toast.makeText(this , text, duration);
        toast.show();
    }
    protected void onCheckChanged(boolean isChecked){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml
        .setTitle(R.string.dialog_title)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent resultIntent = new Intent(  );
                resultIntent.putExtra("Response", "ListItemsActivity passed: My Information to Share");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        })
                .show();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
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
