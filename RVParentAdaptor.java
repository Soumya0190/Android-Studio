package com.UCI.redditsystem;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class RVParentAdaptor extends RecyclerView.Adapter<RVParentAdaptor.VerticalRVViewHolder> {


    @NonNull
    @Override
    public VerticalRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalRVViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VerticalRVViewHolder extends RecyclerView.ViewHolder {
        public VerticalRVViewHolder(View itemView){
            super(itemView);
        }
    }
}
