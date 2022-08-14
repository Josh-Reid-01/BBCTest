package org.me.gcu.labstuff.bbctest2;

import java.util.Date;

public class RSSItem {

    private String title,desc;
    private String pubDate;

    public RSSItem(String title, String desc, String pubDate) {
        this.title = title;
        this.desc = desc;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
