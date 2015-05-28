package com.globallogic.rss_reader.model;

import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by diego.rotondale on 11/05/2015.
 */
@Root(strict = false)
public class Item {
    private static String LOG_TAG = Item.class.getSimpleName();

    @Element
    public String title;
    @Element
    public String link;
    @Element
    public String pubDate;
    @Element
    public String description;

    public String getDescription() {
        String desc = this.description;
        desc = removeFooter(desc);
        desc = removeImage(desc);
        return desc;
    }

    private String removeFooter(String desc) {
        String descriptionWithoutFooter = desc;
        String readHere = "Leer nota completa";
        if (desc.contains(readHere)) {
            descriptionWithoutFooter = desc.substring(0, desc.indexOf(readHere));
        } else {
            descriptionWithoutFooter = desc.substring(0, desc.indexOf("<p>La entrada"));
        }
        return descriptionWithoutFooter;
    }

    private String removeImage(String desc) {
        String descriptionWithoutImage = desc;
        String tagToRemove = "</div><div>";
        if (desc.contains(tagToRemove)) {
            descriptionWithoutImage = desc.substring(desc.indexOf(tagToRemove), desc.length());
            descriptionWithoutImage = "<div>" + descriptionWithoutImage;
        }
        return descriptionWithoutImage;
    }

    public String getPubDate() {
        if (pubDate == null)
            return "";
        SimpleDateFormat format = new SimpleDateFormat("ccc, dd LLLL yyyy HH:mm:ss Z", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(pubDate);
            System.out.println(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("ccc dd LLLL yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}
