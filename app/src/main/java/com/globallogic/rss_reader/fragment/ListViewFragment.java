package com.globallogic.rss_reader.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.globallogic.rss_reader.R;
import com.globallogic.rss_reader.adapter.list.RssAdapter;
import com.globallogic.rss_reader.interfaces.IListViewFragment;
import com.globallogic.rss_reader.model.Item;
import com.globallogic.rss_reader.service.RssService;

import java.util.List;

/**
 * Created by diego.rotondale on 12/05/2015.
 */
public class ListViewFragment extends Fragment implements RssService.RSSCallback {
    private View progress;
    private IListViewFragment callback;
    private RssAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        ListView rssList = (ListView) view.findViewById(R.id.list_rss);
        adapter = new RssAdapter(getActivity());
        rssList.setAdapter(adapter);
        progress = view.findViewById(R.id.list_progress);
        RssService.getRSS(this);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_recycler_view) {
            callback.openRecyclerView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onError(final String error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ListViewFragment.this.getActivity(), error, Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResponse(final List<Item> items) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.addAll(items);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.callback = (IListViewFragment) activity;
    }
}
