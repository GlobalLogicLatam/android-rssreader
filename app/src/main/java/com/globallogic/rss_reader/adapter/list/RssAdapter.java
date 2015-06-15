package com.globallogic.rss_reader.adapter.list;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globallogic.rss_reader.R;
import com.globallogic.rss_reader.RSSApplication;
import com.globallogic.rss_reader.model.Item;

/**
 * Created by diego.rotondale on 12/05/2015.
 */
public class RssAdapter extends ArrayAdapter<Item> {
    private final Context context;
    int resource = R.layout.item_rss;

    public RssAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RssHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            holder = new RssHolder();
            view = inflater.inflate(resource, parent, false);
            holder.title = (TextView) view.findViewById(R.id.item_rss_title);
            holder.description = (TextView) view.findViewById(R.id.item_rss_description);
            holder.pubDate = (TextView) view.findViewById(R.id.item_rss_pub_date);
            holder.image = (NetworkImageView) view.findViewById(R.id.item_rss_image);
            view.setTag(holder);
        } else {
            holder = (RssHolder) view.getTag();
        }
        Item item = getItem(position);
        holder.title.setText(item.title);
        holder.description.setText(Html.fromHtml(item.getDescription()));
        holder.pubDate.setText(item.getPubDate());
        if (item.content != null && item.content.url != null) {
            holder.image.setImageUrl(item.content.url, RSSApplication.getInstance().getImageLoader());
            holder.image.setContentDescription(item.content.title);
            holder.image.setVisibility(View.VISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
        }
        return view;
    }

    private static class RssHolder {
        TextView title;
        TextView description;
        TextView pubDate;
        NetworkImageView image;
    }
}
