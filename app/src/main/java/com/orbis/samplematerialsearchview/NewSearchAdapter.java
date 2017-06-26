package com.orbis.samplematerialsearchview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orbis.materialsearchview.SearchAdapter;

import java.util.List;

/**
 * Created by Carlos Vargas on 6/24/17.
 */

public class NewSearchAdapter extends SearchAdapter {
    @Override
    public RecyclerView.ViewHolder getCount() {
        return null;
    }


   /* private static final int TYPE_ALARM = 0;
    private static final int TYPE_PROFILE = 1;

    private List<Object> objectList;

    public NewSearchAdapter() {
    }

    @Override
    public void addDataList(List<Object> objectList) {
        this.objectList = objectList;
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof AlarmEntity) {
            return TYPE_ALARM;
        } else {
            return TYPE_PROFILE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ALARM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_1, parent, false);
            return new ItemViewHolder1(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_2, parent, false);
            return new ItemViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ALARM) {
            ((ItemViewHolder1) holder).lblMessage.setText("HOLA1");

        } else {
            ((ItemViewHolder2) holder).lblMessage.setText("HOLA2");
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class ItemViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llGeneral;
        private TextView lblMessage;

        ItemViewHolder1(View itemView) {
            super(itemView);
            llGeneral = (LinearLayout) itemView.findViewById(com.orbis.materialsearchview.R.id.llGeneral);
            lblMessage = (TextView) itemView.findViewById(com.orbis.materialsearchview.R.id.lblMessage);
            llGeneral.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    class ItemViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llGeneral;
        private TextView lblMessage;

        ItemViewHolder2(View itemView) {
            super(itemView);
            llGeneral = (LinearLayout) itemView.findViewById(com.orbis.materialsearchview.R.id.llGeneral);
            lblMessage = (TextView) itemView.findViewById(com.orbis.materialsearchview.R.id.lblMessage);
            llGeneral.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }*/
}
