package com.globallogic.rss_reader.service;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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
    public static void getRSS(final RSSCallback callback) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Serializer serializer = new Persister();
                try {
                    RSS rss = serializer.read(RSS.class, response);
                    callback.onResponse(rss.getItems());
                } catch (Exception e) {
                    callback.onError(e.toString());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
            }
        };

        StringRequest request = new StringRequest(Request.Method.GET,
                Constants.CLUB_FEED, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RSSApplication.getInstance().getRequestQueue().add(request);
    }

    public interface RSSCallback {

        void onError(String error);

        void onResponse(List<Item> items);
    }
}
