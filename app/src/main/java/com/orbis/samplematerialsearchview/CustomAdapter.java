package com.orbis.samplematerialsearchview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orbis.materialsearchview.SearchAdapter;

import java.util.List;

/**
 * Created by Carlos Vargas on 6/24/17.
 */

class CustomAdapter extends SearchAdapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ALARM = 0;
    private static final int TYPE_PROFILE = 1;

    private List<Object> objectList;

    CustomAdapter(List<Object> objectList) {
        this.objectList = objectList;
    }

    private OnSuggestionClickListener onSuggestionClickListener;

    public interface OnSuggestionClickListener {
        void onSuggestionClick(String message);
    }

    public void setOnSuggestionClickListener(OnSuggestionClickListener onSuggestionClickListener) {
        this.onSuggestionClickListener = onSuggestionClickListener;
    }

    @Override
    public int getViewType(int position) {
        if (objectList.get(position) instanceof AlarmEntity) {
            return TYPE_ALARM;
        } else {
            return TYPE_PROFILE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ALARM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
            return new AlarmViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
            return new ProfileViewHolder(view);
        }
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {
        if (objectList.get(position) instanceof AlarmEntity) {
            AlarmEntity alarmEntity = ((AlarmEntity) objectList.get(position));
            ((AlarmViewHolder) holder).lblMessage.setText(alarmEntity.getMessage());
        } else {
            ProfileEntity profileEntity = ((ProfileEntity) objectList.get(position));
            ((ProfileViewHolder) holder).lblMessage.setText(profileEntity.getMessage());
        }
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    private class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llGeneral;
        private TextView lblMessage;

        AlarmViewHolder(View itemView) {
            super(itemView);
            llGeneral = itemView.findViewById(com.orbis.materialsearchview.R.id.llGeneral);
            lblMessage = itemView.findViewById(com.orbis.materialsearchview.R.id.lblMessage);
            llGeneral.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSuggestionClickListener.onSuggestionClick(((AlarmEntity) objectList.get(getAdapterPosition())).getMessage());
        }
    }

    private class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llGeneral;
        private TextView lblMessage;

        ProfileViewHolder(View itemView) {
            super(itemView);
            llGeneral = itemView.findViewById(com.orbis.materialsearchview.R.id.llGeneral);
            lblMessage = itemView.findViewById(com.orbis.materialsearchview.R.id.lblMessage);
            llGeneral.setOnClickListener(this);
            lblMessage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
