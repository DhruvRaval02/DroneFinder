package com.example.dhruv.dronefinder;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Attack extends AppCompatActivity {

    URL information;
    URLConnection connectInformation;
    InputStream inputStream;
    BufferedReader bufferedReader;
    String jsonInformation = "h";
    JSONObject allData;
    JSONArray allDataArray;
    JSONObject temp;

    TextView date;
    ImageView flag;
    TextView target;
    TextView deaths;
    TextView location;
    ImageView next;
    ImageView back;

    int strikeNumber = 0;

    ArrayList <JSONObject> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);

        date = findViewById(R.id.ID_year);
        flag = findViewById(R.id.ID_flags);
        target = findViewById(R.id.ID_target);
        deaths = findViewById(R.id.ID_deaths);
        location = findViewById(R.id.ID_place);
        next = findViewById(R.id.ID_next);
        back = findViewById(R.id.ID_back);

        new getStrikeInformation().execute();
    }

    public class getStrikeInformation extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            jsonInformation = "";
            try {
                information = new URL("http://api.dronestre.am/data");
                connectInformation = information.openConnection();
                inputStream = connectInformation.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String st;
            try {
                while ((st = bufferedReader.readLine()) != null) {
                    System.out.println(st);
                    jsonInformation += st;
                }

                //Log.d("TAG", jsonInformation);

            }catch(IOException e){}

            try {
                allData = new JSONObject(jsonInformation);
                allDataArray = allData.getJSONArray("strike");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < allDataArray.length(); i++){
                try {
                    temp = (JSONObject) allDataArray.get(i);
                    if(temp.getString("date").contains(getIntent().getStringExtra("YEAR")))
                        dataList.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                date.setText(getIntent().getStringExtra("YEAR"));
                setImageResources(dataList.get(0).getString("country"));
                if((dataList.get(0).getString("target").equals("")))
                    target.setText("Target: Unknown");
                else
                    target.setText("Target: " + dataList.get(0).getString("target"));
                if((dataList.get(0).getString("deaths").equals("")))
                    deaths.setText("Fatalities: Unknown");
                else
                    deaths.setText("Fatalities: " + dataList.get(0).getString("deaths"));
                if((dataList.get(0).getString("location").equals("")))
                    location.setText("Location: Unknown");
                else
                    location.setText("Location: " + dataList.get(0).getString("location"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        back.setVisibility(View.VISIBLE);
                        if(strikeNumber + 1 == dataList.size())
                            next.setVisibility(View.INVISIBLE);
                        else {
                            strikeNumber++;
                            setImageResources(dataList.get(strikeNumber).getString("country"));
                            if ((dataList.get(strikeNumber).getString("target").equals("")))
                                target.setText("Target: Unknown");
                            else
                                target.setText("Target: " + dataList.get(strikeNumber).getString("target"));
                            if ((dataList.get(strikeNumber).getString("deaths").equals("")))
                                deaths.setText("Fatalities: Unknown");
                            else
                                deaths.setText("Fatalities: " + dataList.get(strikeNumber).getString("deaths"));
                            if ((dataList.get(strikeNumber).getString("location").equals("")))
                                location.setText("Location: Unknown");
                            else
                                location.setText("Location: " + dataList.get(strikeNumber).getString("location"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            if(strikeNumber + 1 == dataList.size())
                next.setVisibility(View.INVISIBLE);

            if(strikeNumber == 0){
                back.setVisibility(View.INVISIBLE);
            }
            else
                back.setVisibility(View.VISIBLE);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    next.setVisibility(View.VISIBLE);

                    try {
                        if(strikeNumber - 1 == 0){
                            back.setVisibility(View.INVISIBLE);
                        }
                        else
                            back.setVisibility(View.VISIBLE);

                        strikeNumber--;
                        setImageResources(dataList.get(strikeNumber).getString("country"));
                        if ((dataList.get(strikeNumber).getString("target").equals("")))
                            target.setText("Target: Unknown");
                        else
                            target.setText("Target: " + dataList.get(strikeNumber).getString("target"));
                        if ((dataList.get(strikeNumber).getString("deaths").equals("")))
                            deaths.setText("Fatalities: Unknown");
                        else
                            deaths.setText("Fatalities: " + dataList.get(strikeNumber).getString("deaths"));
                        if ((dataList.get(strikeNumber).getString("location").equals("")))
                            location.setText("Location: Unknown");
                        else
                            location.setText("Location: " + dataList.get(strikeNumber).getString("location"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }


                }
            });

        }
    }

    public void setImageResources(String country){
        if(country.equals("Pakistan")){
            flag.setImageResource(R.mipmap.pakistan);
        }
        else if(country.equals("Yemen")){
            flag.setImageResource(R.mipmap.yemen);
        }
        else{
            flag.setImageResource(R.mipmap.somalia);
        }
    }
}
