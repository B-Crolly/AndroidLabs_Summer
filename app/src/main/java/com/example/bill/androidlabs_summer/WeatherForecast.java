package com.example.bill.androidlabs_summer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class WeatherForecast extends Activity {
    private ProgressBar progressBar;
    private static String ACTIVITY_NAME = "WeatherForecast";
    private static final String ns = null;
    protected final String WEATHER_TEMP = "temperature";
    private String tempValue;
    private String tempMin;
    private String tempMax;
    private String weatherIconName;
    private ForecastQuery query;
    private String imageURL;
    private String iconFileName;
    private Bitmap weatherIcon;

    protected ImageView imageView;
    protected TextView minTemp;
    protected TextView maxTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        imageView = (ImageView) findViewById(R.id.weatherImg);
        minTemp = (TextView) findViewById((R.id.minTemp));
        maxTemp = (TextView) findViewById((R.id.maxTemp));


        query = new ForecastQuery();
        query.execute();
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

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String min;
        private String max;
        private String currentTemp;

        @Override
        protected String doInBackground(String... strings) {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

            URL url = null;
            try {
                url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            parse(conn.getInputStream());
            } catch (IOException | XmlPullParserException exc) {
                exc.printStackTrace();
            }
            return "s";
        }


        public void parse(InputStream in) throws XmlPullParserException, IOException {
            Log.i(ACTIVITY_NAME, "In parse()");
            int eventType = -1;
            String name;

            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.next();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    name = parser.getName();
                    if (name.equals("temperature")) {
                        tempValue = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        tempMin = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        tempMax = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    } else if (name.equals("weather")) {
                        weatherIconName = parser.getAttributeValue(null, "icon");
                        imageURL = ("http://openweathermap.org/img/w/" + weatherIconName + ".png");
                        iconFileName = (weatherIconName + ".png");
                        if (fileExistance(iconFileName)) {
                            Log.i(ACTIVITY_NAME, "In parse(): image " + iconFileName + " found locally");
                            weatherIcon = imageFromDisk(iconFileName);
                            publishProgress(100);
                        } else {
                            Log.i(ACTIVITY_NAME, "In parse() image " + iconFileName + " not found locally: downloading");
                            weatherIcon = saveImage(getImage(imageURL));
                            publishProgress(100);

                        }
                    }
                }
                eventType = parser.next();
            }

            System.out.println(tempValue + " " + tempMin + " " + tempMax + " " + weatherIconName);

        }

        @Override
        public void onProgressUpdate(Integer ...value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result){
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(weatherIcon);
            minTemp.setText("Min: "+ tempMin + " C                         ");
            maxTemp.setText("Max: "+ tempMax + " C");

        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap imageFromDisk (String fname) {
            FileInputStream fis = null;
            try {
                fis = openFileInput(fname);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return BitmapFactory.decodeStream(fis);
        }

            public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
        public Bitmap saveImage (Bitmap bmp){
            FileOutputStream outputStream = null;
            try {
                outputStream = openFileOutput( weatherIconName + ".png", Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bmp.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmp;
        }


        protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        }
    }
}
