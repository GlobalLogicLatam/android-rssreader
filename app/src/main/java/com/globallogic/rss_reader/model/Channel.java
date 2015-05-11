package com.globallogic.rss_reader.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by diego.rotondale on 11/05/2015.
 */
@Root(strict = false)
public class Channel {
    @ElementList(inline = true)
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
}
