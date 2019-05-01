package com.example.dhruv.dronefinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    EditText yearEntry;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.ID_logoClick);
        yearEntry = findViewById(R.id.ID_yearEntry);

        intent = new Intent(this, Attack.class);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Attack.class);
                if(yearEntry.getText().toString().equals("2005") || yearEntry.getText().toString().equals("2006") || yearEntry.getText().toString().equals("2007") || yearEntry.getText().toString().equals("2008") || yearEntry.getText().toString().equals("2009") || yearEntry.getText().toString().equals("2010") || yearEntry.getText().toString().equals("2011") || yearEntry.getText().toString().equals("2012") || yearEntry.getText().toString().equals("2013") || yearEntry.getText().toString().equals("2014") || yearEntry.getText().toString().equals("2015") || yearEntry.getText().toString().equals("2016") || yearEntry.getText().toString().equals("2017")) {
                    intent.putExtra("YEAR", yearEntry.getText().toString());
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "Please Enter a Valid Year", Toast.LENGTH_LONG).show();
            }
        });
    }

}
