package com.globallogic.rss_reader.adapter.list;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.globallogic.rss_reader.R;
import com.globallogic.rss_reader.model.Item;

/**
 * Created by diego.rotondale on 12/05/2015.
 */
public class RssAdapter extends ArrayAdapter<Item> {
    private final Context context;

    public RssAdapter(Context context) {
        super(context, R.layout.item_rss);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductCategoryHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            holder = new ProductCategoryHolder();
            view = inflater.inflate(R.layout.item_rss, parent, false);
            holder.title = (TextView) view.findViewById(R.id.item_rss_title);
            holder.description = (TextView) view.findViewById(R.id.item_rss_description);
            holder.pubDate = (TextView) view.findViewById(R.id.item_rss_pub_date);
            view.setTag(holder);
        } else {
            holder = (ProductCategoryHolder) view.getTag();
        }
        Item item = getItem(position);
        holder.title.setText(item.title);
        holder.description.setText(Html.fromHtml(item.getDescription()));
        holder.pubDate.setText(item.pubDate);
        return view;
    }

    private static class ProductCategoryHolder {
        TextView title;
        TextView description;
        TextView pubDate;
    }
}
