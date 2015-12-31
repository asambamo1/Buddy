package com.smartlifedigital.carpoolbuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartlifedigital.carpoolbuddy.Models.FontAwesomeViewHolder;
import com.smartlifedigital.carpoolbuddy.R;

/**
 * Created by alexanderchiou on 12/20/15.
 */
public class FontAwesomeAdapter extends BaseAdapter {
    private String[] itemNames;
    private String[] itemIcons;
    private Context context;

    public FontAwesomeAdapter(Context context, String[] itemNames, String[] itemIcons) {
        this.context = context;
        this.itemNames = itemNames;
        this.itemIcons = itemIcons;
    }

    @Override
    public int getCount() {
        return itemNames.length;
    }

    @Override
    public String getItem(int position) {
        return itemNames[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        FontAwesomeViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.font_awesome_list_item, parent, false);
            holder = new FontAwesomeViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (FontAwesomeViewHolder) view.getTag();
        }

        holder.itemName.setText(itemNames[position]);
        holder.itemIcon.setText(itemIcons[position]);

        return view;
    }
}
