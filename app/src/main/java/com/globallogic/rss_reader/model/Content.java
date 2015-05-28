package com.globallogic.rss_reader.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by diego.rotondale on 5/28/15.
 */
@Root(name = "content", strict = false)
public class Content {
    @Attribute(name = "url")
    public String url;
    @Element(required = false)
    public String title;
}
