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
}
