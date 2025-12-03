package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.*;
import java.net.*;

public class MainActivityZipCode extends AppCompatActivity {

    EditText zipCode;
    Button next;
    TextView result;
    ProgressBar progressBar;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_zipcode);

        zipCode = findViewById(R.id.editTextZipCode);
        next = findViewById(R.id.button);
        result = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);

        next.setEnabled(false);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(result.getText().toString().equals("Zip Code Found!"))
                    result.setText("");
                next.setEnabled(charSequence.length() == 5);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zipCode.getText().toString().isEmpty()){
                    result.setText("Please enter a valid zip code");
                }
                else {
                    Log.d(TAG, "onClick: " + zipCode.getText().toString());

                    AsyncThread task = new AsyncThread();
                    task.execute(zipCode.getText().toString());
                }
            }
        });
    }

    //BACKGROUND THREAD TO DOWNLOAD DATA
    public class AsyncThread extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //BEFORE task starts, on MAIN THREAD
            next.setEnabled(false);
            result.setText("Finding Zip Code...");
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            // task you want to do, on BACKGROUND THREAD
            //Log.d("TAG","Thread ");
            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?zip=" + strings[0] + "&appid=eb7d203fd1020678bbbcdf32494e1ad1" + "&units=imperial");
                URLConnection urlConnection = url.openConnection();
                InputStream input = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

                String data = bufferedReader.readLine();

                Log.d(TAG, "doInBackground: " + data);

                return data;

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "BACKGROUND");

                //Can't change UI elements from background thread
                /*
                result.setText("Error, Please try again.");
                next.setEnabled(true);
                next.setText("Retry");
                */
                return null;

            }

        }

        @Override
        protected void onPostExecute(String res) {
            //AFTER task ends, on MAIN THREAD
            next.setEnabled(true);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            if(res == null) {

                result.setText("Zip Code Not Found, Please try again.");
                next.setText("Retry");
            }
            else{
                result.setText("Zip Code Found!");
                Log.d(TAG, "onPostExecute: " + res);

                Intent intent = new Intent(MainActivityZipCode.this, MainActivityWeather.class);
                intent.putExtra("Zip Code", res);
                startActivity(intent);

            }
        }
    }
}
