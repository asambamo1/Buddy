package com.randomappsinc.blanknavigationdrawer.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.randomappsinc.blanknavigationdrawer.API.Models.UserThumbnail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class RequestsAdapter extends BaseAdapter {
    private Context context;
    private List<UserThumbnail> requests;

    public RequestsAdapter(Context context) {
        this.context = context;
        this.requests = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
