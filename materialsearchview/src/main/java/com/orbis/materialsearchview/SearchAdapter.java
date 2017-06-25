package com.orbis.materialsearchview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carlos Vargas on 6/24/17.
 *
 */

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder> {

    private List<SearchEntity> searchEntities;

    SearchAdapter(List<SearchEntity> adjectiveEntities) {
        this.searchEntities = adjectiveEntities;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.lblMessage.setText(searchEntities.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return searchEntities.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout llGeneral;
        private TextView lblMessage;

        ItemViewHolder(View itemView) {
            super(itemView);
            llGeneral = (LinearLayout) itemView.findViewById(R.id.llGeneral);
            lblMessage = (TextView) itemView.findViewById(R.id.lblMessage);
            llGeneral.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
