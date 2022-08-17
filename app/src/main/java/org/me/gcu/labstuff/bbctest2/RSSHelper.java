package org.me.gcu.labstuff.bbctest2;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.DatabaseMetaData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RSSHelper {


    private static Exception exception=null;

    public static InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public static String showLink(String link){
        System.out.println(link);
        return link;
    }

    public static String getRawData(String url){
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";
        String result=null;


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
        return result;
    }
    public static ArrayList<RSSItem> parseRSS(String link){
        ArrayList<RSSItem> rssItemList = new ArrayList<>();

        String rssTitle = "";
        String rssDesc = "";
        String rssDate="";

        try {

            URL url= new URL(link);
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(getInputStream(url),"UTF_8");
            boolean insideItem = false;

            int eventType = xpp.getEventType();
            while(eventType!= XmlPullParser.END_DOCUMENT){
                if(eventType== XmlPullParser.START_TAG){
                    if(xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;

                    }

                        if (xpp.getName().equalsIgnoreCase("title") && insideItem) {

                            rssTitle = xpp.nextText();


                            Log.d("MyTag", "title is " + rssTitle);

                        }
                        if (xpp.getName().equalsIgnoreCase("description") && insideItem) {
                            rssDesc = xpp.nextText();
                            Log.d("MyTag", "desc is " + rssDesc);

                        }
                        if (xpp.getName().equalsIgnoreCase("pubDate") && insideItem) {
                            rssDate = xpp.nextText();
                            Log.d("MyTag", "date is " + rssDate);

                            RSSItem rssItem = new RSSItem(rssTitle,rssDesc,rssDate);
                            rssItemList.add(rssItem);

                        }


                }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                    insideItem=false;


                }
                eventType= xpp.next();
            }

        }catch (MalformedURLException e){
            exception=e;
        }
        catch (XmlPullParserException e){
            exception=e;
        } catch (IOException e) {
            exception=e;
            e.printStackTrace();
        }

        return rssItemList;

    }


}