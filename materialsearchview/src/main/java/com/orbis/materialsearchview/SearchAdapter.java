package com.orbis.materialsearchview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by carlos on 6/24/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder>{

    List<SearchEntity> adjectiveEntities;
    public SearchAdapter(List<SearchEntity> adjectiveEntities){
        this.adjectiveEntities = adjectiveEntities;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return adjectiveEntities.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView lblMessage;
        public ItemViewHolder(View itemView) {
            super(itemView);
            lblMessage = (TextView) itemView.findViewById(R.id.lblMessage);
        }
    }
}
