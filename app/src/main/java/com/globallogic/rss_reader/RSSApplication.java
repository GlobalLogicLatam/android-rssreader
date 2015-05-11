package com.globallogic.rss_reader;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by diego.rotondale on 10/05/2015.
 */
public class RSSApplication extends Application {

    private static RSSApplication instance;
    private RequestQueue mRequestQueue;

    public static RSSApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return mRequestQueue;
    }
}
