package com.parentsapp.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.parentsapp.R;
import com.parentsapp.models.Item;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<Item> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item, value;

        public MyViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.item);
            value = view.findViewById(R.id.value);
        }

    }

    public ListAdapter(List<Item> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = list.get(position);

        holder.item.setText(item.getItem());
        holder.value.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}