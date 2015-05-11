package com.globallogic.rss_reader.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by diego.rotondale on 10/05/2015.
 */
@Root(strict = false)
public class RSS {
    @Element
    private Channel channel;

    public List<Item> getItems() {
        return channel.getItems();
    }
}
