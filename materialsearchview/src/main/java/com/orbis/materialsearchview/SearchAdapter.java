package com.orbis.materialsearchview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carlos Vargas on 6/24/17.
 */

public abstract class SearchAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

  /*  private List<Object> objectList;

    public SearchAdapter() {
    }

    public void addDataList(List<Object> objectList) {
        this.objectList = objectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new BaseItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (objectList.get(position) instanceof SearchEntity) {
            SearchEntity searchEntity = (SearchEntity) objectList.get(position);
            ((BaseItemViewHolder) holder).lblMessage.setText(searchEntity.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class BaseItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout llGeneral;
        private TextView lblMessage;

        BaseItemViewHolder(View itemView) {
            super(itemView);
            llGeneral = (LinearLayout) itemView.findViewById(R.id.llGeneral);
            lblMessage = (TextView) itemView.findViewById(R.id.lblMessage);
            llGeneral.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
*/

    //public abstract String onCreateViewHolder();
    public abstract VH getCount();





}
