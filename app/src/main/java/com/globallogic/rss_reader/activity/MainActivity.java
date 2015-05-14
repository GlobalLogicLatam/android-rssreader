package com.globallogic.rss_reader.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.globallogic.rss_reader.R;
import com.globallogic.rss_reader.fragment.ListViewFragment;
import com.globallogic.rss_reader.fragment.RecyclerViewFragment;
import com.globallogic.rss_reader.interfaces.IListViewFragment;
import com.globallogic.rss_reader.interfaces.IRecyclerViewFragment;


public class MainActivity extends AppCompatActivity implements IRecyclerViewFragment, IListViewFragment {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof RecyclerViewFragment)
            getMenuInflater().inflate(R.menu.recycler_view, menu);
        else
            getMenuInflater().inflate(R.menu.list_view, menu);
        return true;
    }

    @Override
    public void openRecyclerView() {
        openFragment(new RecyclerViewFragment());
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void openListView() {
        openFragment(new ListViewFragment());
    }
}
