package com.globallogic.rss_reader.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by diego.rotondale on 11/05/2015.
 */
@Root(strict = false)
public class Item {
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
        String readHere = "Leer nota completa aquí";
        if (description.contains(readHere)) {
            descriptionWithoutFooter = description.substring(0, description.indexOf(readHere));
        } else {
            descriptionWithoutFooter = description.substring(0, description.indexOf("<p>La entrada"));
        }
        return descriptionWithoutFooter;
    }
}
