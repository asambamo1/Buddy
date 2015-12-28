package com.randomappsinc.blanknavigationdrawer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.blanknavigationdrawer.API.Models.UserThumbnail;
import com.randomappsinc.blanknavigationdrawer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class UserThumbnailsAdapter extends BaseAdapter {
    private Context context;
    private List<UserThumbnail> suggestions;

    public UserThumbnailsAdapter(Context context) {
        this.context = context;
        this.suggestions = new ArrayList<>();
    }

    public void setSuggestions(List<UserThumbnail> suggestions) {
        this.suggestions.clear();
        this.suggestions.addAll(suggestions);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public UserThumbnail getItem(int position) {
        return suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public class SuggestionViewHolder {
        @Bind(R.id.suggestion_name) TextView name;
        @Bind(R.id.village) TextView village;
        @Bind(R.id.work_zip) TextView workZip;

        public SuggestionViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SuggestionViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.suggestion_cell, parent, false);
            holder = new SuggestionViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (SuggestionViewHolder) view.getTag();
        }
        holder.name.setText(getItem(position).getName());
        holder.village.setText(getItem(position).getVillage());
        holder.workZip.setText(String.valueOf(getItem(position).getZipCode()));
        return view;
    }
}
