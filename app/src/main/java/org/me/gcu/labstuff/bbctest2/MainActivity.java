package org.me.gcu.labstuff.bbctest2;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1="";
    private RadioButton glasgow,london,newYork;

    // Traffic Scotland Planned Roadworks XML link
    private String urlSource="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        glasgow = (RadioButton) findViewById(R.id.glasgow);
        london = (RadioButton) findViewById(R.id.london);
        newYork= (RadioButton) findViewById(R.id.newYork);
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");
        // More Code goes here
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();

    } //

    @Override
    public void onClick(View v)
    {
        if(glasgow.isChecked()){
urlSource="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
        }else if(london.isChecked()){
            urlSource="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
        }else if(newYork.isChecked()){
            urlSource="https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
        }
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }
    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
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

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

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