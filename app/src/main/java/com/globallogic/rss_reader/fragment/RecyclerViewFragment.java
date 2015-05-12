package com.globallogic.rss_reader.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.globallogic.rss_reader.R;
import com.globallogic.rss_reader.adapter.RssAdapter;
import com.globallogic.rss_reader.decoration.DividerItemDecoration;
import com.globallogic.rss_reader.model.Item;
import com.globallogic.rss_reader.service.RssService;

import java.util.List;

/**
 * Created by diego.rotondale on 12/05/2015.
 */
public class RecyclerViewFragment extends Fragment implements RssService.RSSCallback, RssAdapter.OnItemClickListener {
    private RecyclerView mRssRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RssAdapter mAdapter;
    private View progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_view_fragment, container, false);

        RssService.getRSS(this);

        mRssRecyclerView = (RecyclerView) view.findViewById(R.id.main_rss_rv);
        mRssRecyclerView.setHasFixedSize(true);
        mRssRecyclerView.addItemDecoration(new DividerItemDecoration(1, Color.BLACK));
        mRssRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRssRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RssAdapter();
        mAdapter.setOnItemClickListener(this);
        mRssRecyclerView.setAdapter(mAdapter);

        progress = view.findViewById(R.id.main_progress);
        return view;
    }


    @Override
    public void onError(final String error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RecyclerViewFragment.this.getActivity(), error, Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResponse(final List<Item> items) {
        getActivity().runOnUiThread(new Runnable() {
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
