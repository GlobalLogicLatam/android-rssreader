package com.globallogic.rss_reader.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.globallogic.rss_reader.R;
import com.globallogic.rss_reader.adapter.RssAdapter;
import com.globallogic.rss_reader.decoration.DividerItemDecoration;
import com.globallogic.rss_reader.model.Item;
import com.globallogic.rss_reader.service.RssService;

import java.util.List;


public class MainActivity extends AppCompatActivity implements RssService.RSSCallback, RssAdapter.OnItemClickListener {

    private RecyclerView mRssRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RssAdapter mAdapter;
    private View progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RssService.getRSS(this);

        mRssRecyclerView = (RecyclerView) findViewById(R.id.main_rss_rv);
        mRssRecyclerView.setHasFixedSize(true);
        mRssRecyclerView.addItemDecoration(new DividerItemDecoration(1, Color.BLACK));
        mRssRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mLayoutManager = new LinearLayoutManager(this);
        mRssRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RssAdapter();
        mAdapter.setOnItemClickListener(this);
        mRssRecyclerView.setAdapter(mAdapter);

        progress = findViewById(R.id.main_progress);
    }

    @Override
    public void onError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResponse(final List<Item> items) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addAll(items);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClickListener(View view, Item item) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(item.link));
        startActivity(i);
    }
}
