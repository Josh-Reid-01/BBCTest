package org.me.gcu.labstuff.bbctest2;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private TextView rawDataDisplay;
    private String LINK = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/";
    private String result = "";
    private String requestLnk = "";
    // Traffic Scotland Planned Roadworks XML link


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);

        //get spinner from layour
        spinner = findViewById(R.id.spinner);
        //create list of compys objects
        List<Campus> campusList = new ArrayList<>();
        Campus glasgow = new Campus(1, "Glasgow", "2648579");
        Campus london = new Campus(2, "London", "2643743");
        Campus newyork = new Campus(3, "NewYork", "5128581");
        campusList.add(glasgow);
        campusList.add(london);
        campusList.add(newyork);

        //create adapter
        ArrayAdapter<Campus> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, campusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                Campus campus = (Campus) parent.getSelectedItem();
                displayCampusData(campus);
                //add the location data from the spinner
                requestLnk= LINK+campus.getLocation();
                new Thread(new Task()).start();
                //call the method to show waether
                showLink(requestLnk);
                //call the method to show waether

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //get listview from layout


    }

    public void getSelectedCampus(View v) {
        Campus campus = (Campus) spinner.getSelectedItem();
        displayCampusData(campus);

    }

    private void displayCampusData(Campus campus){

        int id =campus.getId();
        String name= campus.getName();
        String location = campus.getLocation();
        requestLnk=LINK+location;
        //commented out call from onItemSelected this method just for debugging
        //new Thread(new Task(requestLnk)).start();
        String campusData = "Name "+name +" Location "+location;
        Toast.makeText(this,campusData,Toast.LENGTH_LONG).show();
    }

    private void showLink(String lnk){



        Toast.makeText(this,lnk,Toast.LENGTH_LONG).show();
    }

//    public void startProgress()
//    {
//        // Run network access on a separate thread;
//        new Thread(new Task(requestLnk)).start();
//
//    }

    private class Task implements Runnable
    {
        private String raw;
        //ive removed url/aurl as not needed here

        public Task()
        {

        }

        @Override
        public void run()
        {
            //extracted your code into RSS Helper
            raw= RSSHelper.getRawData(requestLnk);
            //changed MainActivity.this.runOnUiThread to runOnUiThread
            runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    if (raw!=null) {
                        rawDataDisplay.setText(raw);
                    }
                }
            });
        }

    }
}









