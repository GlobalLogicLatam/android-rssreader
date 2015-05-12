package com.globallogic.rss_reader.service;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.globallogic.rss_reader.Constants;
import com.globallogic.rss_reader.RSSApplication;
import com.globallogic.rss_reader.model.Item;
import com.globallogic.rss_reader.model.RSS;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by diego.rotondale on 10/05/2015.
 */
public class RssService {
    private static String LOG_TAG = RssService.class.getSimpleName();

    public static void getRSS(final RSSCallback callback) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setResponse(response, callback);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
            }
        };

        RequestQueue requestQueue = RSSApplication.getInstance().getRequestQueue();
        Cache.Entry entry = requestQueue.getCache().get(Constants.CLUB_FEED);
        if (entry != null) {
            String cachedResponse = new String(entry.data);
            setResponse(cachedResponse, callback);
        } else {
            StringRequest request = new StringRequest(Request.Method.GET,
                    Constants.CLUB_FEED, listener, errorListener);

            request.setRetryPolicy(new DefaultRetryPolicy(5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        }
    }

    private static void setResponse(String response, RSSCallback callback) {
        Serializer serializer = new Persister();
        try {
            RSS rss = serializer.read(RSS.class, response);
            callback.onResponse(rss.getItems());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            callback.onError(e.toString());
        }
    }

    public interface RSSCallback {

        void onError(String error);

        void onResponse(List<Item> items);
    }
}
