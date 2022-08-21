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