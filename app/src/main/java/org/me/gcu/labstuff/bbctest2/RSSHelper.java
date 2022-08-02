package org.me.gcu.labstuff.bbctest2;

import android.widget.ArrayAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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

    public static ArrayList<String> parseRSS(String link){
        ArrayList<String> titles = null;
        ArrayList<String> links = null;
        try {
            titles = new ArrayList<>();
            links = new ArrayList<>();
            URL url= new URL(link);
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(getInputStream(url),"UTF_8");
            boolean insideItem = false;

            int eventType = xpp.getEventType();
            while(eventType!= XmlPullParser.END_DOCUMENT){
                if(eventType== XmlPullParser.START_TAG){
                    switch(xpp.getName()){
                        case "item": case "ITEM":
                            insideItem=true;
                            break;
                        case "title":
                            if(insideItem){
                                titles.add(xpp.nextText());
                            }
                            break;
                        case "link":
                            if(insideItem){
                                links.add(xpp.nextText());
                            }
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

        return titles;
    }
}