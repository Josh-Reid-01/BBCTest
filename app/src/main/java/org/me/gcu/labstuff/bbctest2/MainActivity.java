package org.me.gcu.labstuff.bbctest2;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
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

    private String result = "";
    private String url1 = "";
    private RadioButton glasgow, london, newYork;
    private String requestLnk = "";
    // Traffic Scotland Planned Roadworks XML link
    private String urlSource = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);

        //get spinner from layour
        spinner = findViewById(R.id.spinner);
        //create list of compys objects
        List<Campus> campusList = new ArrayList<>();
        Campus glasgow = new Campus(1, "Glasgow", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579");
        Campus london = new Campus(2, "London", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743");
        Campus newyork = new Campus(3, "NewYork", "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581");
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

        String location = campus.getLocation();



        requestLnk=location;
        startProgress(requestLnk);
    }

    public void startProgress(String requestLnk)
    {
        // Run network access on a separate thread;
        new Thread(new Task(requestLnk)).start();

    }

    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }

        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Now read the data. Make sure that there are no specific hedrs
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }



            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    rawDataDisplay.setText(result);
                }
            });
        }

    }
}








