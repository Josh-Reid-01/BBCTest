package org.me.gcu.labstuff.bbctest2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private ListView lv;
    private ArrayList<RSSItem> rssItemList = new ArrayList<RSSItem>();
    private ArrayAdapter<RSSItem> weatherArrayAdapter;

    private IntentFilter intentFilter;
    // Traffic Scotland Planned Roadworks XML link
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //overwrites the RssList
            ArrayList<RSSItem> tempRssList =  (ArrayList<RSSItem>)intent.getSerializableExtra("stories");
            if (tempRssList != null) {
                rssItemList.clear();
                rssItemList.addAll(tempRssList);
                weatherArrayAdapter.notifyDataSetChanged();
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
lv = (ListView)findViewById(R.id.lv);



        //get spinner from layour
        spinner = findViewById(R.id.spinner);
        //create list of compys objects
        List<Campus> campusList = new ArrayList<>();
        Campus glasgow = new Campus(1, "Glasgow", "2648579");
        Campus london = new Campus(2, "London", "2643743");
        Campus newyork = new Campus(3, "New York", "5128581");
        Campus oman = new Campus(4, "Oman", "287286");
        Campus mauritius = new Campus(5, "Mauritius", "934154");
        Campus bangladesh= new Campus(6, "Bangladesh", "1185241");
        campusList.add(glasgow);
        campusList.add(london);
        campusList.add(newyork);
        campusList.add(oman);
        campusList.add(mauritius);
        campusList.add(bangladesh);




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
        intentFilter = new IntentFilter();
        intentFilter.addAction("RSS_RETRIEVED");
        registerReceiver(intentReceiver,intentFilter);

        //start service


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



    private class Task implements Runnable
    {
        private String data;
        //ive removed url/aurl as not needed here

        public Task()
        {

        }

        @Override
        public void run()
        {
            //extracted your code into RSS Helper

rssItemList=RSSHelper.parseRSS(requestLnk);

            //changed MainActivity.this.runOnUiThread to runOnUiThread
            runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                        CustomBaseAdapter myCustomAdapter = new CustomBaseAdapter(MainActivity.this,rssItemList);
                        lv.setAdapter(myCustomAdapter);

                }
            });
        }

    }


}









