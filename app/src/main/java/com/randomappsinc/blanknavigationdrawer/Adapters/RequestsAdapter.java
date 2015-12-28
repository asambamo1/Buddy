package com.randomappsinc.blanknavigationdrawer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.randomappsinc.blanknavigationdrawer.API.ApiConstants;
import com.randomappsinc.blanknavigationdrawer.API.Models.UserThumbnail;
import com.randomappsinc.blanknavigationdrawer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    public void setRequests(List<UserThumbnail> requests) {
        this.requests.clear();
        this.requests.addAll(requests);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public UserThumbnail getItem(int position) {
        return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class RequestViewHolder {
        @Bind(R.id.suggestion_name)
        TextView name;
        @Bind(R.id.village) TextView village;
        @Bind(R.id.work_zip) TextView workZip;
        @Bind(R.id.status_icon) IconTextView status_icon;
        @Bind(R.id.status) TextView status;
        @Bind(R.id.status_container) View status_container;

        public RequestViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        RequestViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.user_thumbnail, parent, false);
            holder = new RequestViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (RequestViewHolder) view.getTag();
        }
        holder.name.setText(getItem(position).getName());
        holder.village.setText(getItem(position).getVillage());
        holder.workZip.setText(String.valueOf(getItem(position).getZipCode()));
        holder.status_container.setVisibility(View.VISIBLE);

        switch(getItem(position).getStatus()) {
            case ApiConstants.ACCEPTED:
                holder.status_icon.setText(context.getString(R.string.check_icon));
                holder.status_icon.setTextColor(context.getResources().getColor(R.color.green));
                holder.status.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case ApiConstants.PENDING:
                holder.status_icon.setText(context.getString(R.string.ellipsis_icon));
                holder.status_icon.setTextColor(context.getResources().getColor(R.color.gold));
                holder.status.setTextColor(context.getResources().getColor(R.color.gold));
                break;
            case ApiConstants.REJECTED:
                holder.status_icon.setText(context.getString(R.string.x_icon));
                holder.status_icon.setTextColor(context.getResources().getColor(R.color.red));
                holder.status.setTextColor(context.getResources().getColor(R.color.red));
                break;
            default:
                break;
        }
        holder.status.setText(getItem(position).getStatus());
        return view;
    }
}
