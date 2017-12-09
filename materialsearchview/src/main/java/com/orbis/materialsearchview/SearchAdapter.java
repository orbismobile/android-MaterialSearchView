package com.orbis.materialsearchview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Carlos Vargas on 6/24/17.
 *
 */

public abstract class SearchAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    //public abstract String onCreateViewHolder();
    public abstract VH onCreateView(ViewGroup parent, int viewType);

    public abstract void onBindView(RecyclerView.ViewHolder holder, int position);

    public abstract int getViewType(int position);

    public abstract int getCount();

}
