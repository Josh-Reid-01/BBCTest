package org.me.gcu.labstuff.bbctest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<RSSItem> rssList;
    LayoutInflater inflater;

    public CustomBaseAdapter (Context ctx, ArrayList<RSSItem> rssItemList){
this.context=ctx;
this.rssList=rssItemList;
inflater=LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return rssList.size();
    }

    @Override
    public Object getItem(int i) {
        return rssList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
     if(convertView == null) {
         convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
     }
         RSSItem tempRSSItem=(RSSItem) getItem(i);

         TextView tvTitle=(TextView) convertView.findViewById(R.id.titletxt);
         TextView tvDesc=(TextView) convertView.findViewById(R.id.desctxt);
         TextView tvDate=(TextView) convertView.findViewById(R.id.datetxt);


         tvTitle.setText(tempRSSItem.getTitle());
         tvDesc.setText(tempRSSItem.getDesc());
         tvDate.setText(tempRSSItem.getPubDate());


         return convertView;






    }
}
