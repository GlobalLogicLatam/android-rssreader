package com.globallogic.rss_reader;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.globallogic.rss_reader.volley.LruBitmapCache;

/**
 * Created by diego.rotondale on 10/05/2015.
 */
public class RSSApplication extends Application {
    private static final String LOG_TAG = RSSApplication.class.getSimpleName();
    private static RSSApplication instance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static RSSApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        logVersions();
    }

    private void logVersions() {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            Log.d(LOG_TAG, Integer.toString(packageInfo.versionCode));
            Log.d(LOG_TAG, packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            mRequestQueue.getCache().clear();
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null)
            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache());
        return this.mImageLoader;
    }
}
