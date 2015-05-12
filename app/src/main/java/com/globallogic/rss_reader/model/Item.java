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
        String descriptionWithoutFooter = "";
        String readHere = "Leer nota completa";
        if (description.contains(readHere)) {
            descriptionWithoutFooter = description.substring(0, description.indexOf(readHere));
        } else {
            descriptionWithoutFooter = description.substring(0, description.indexOf("<p>La entrada"));
        }
        return descriptionWithoutFooter;
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
