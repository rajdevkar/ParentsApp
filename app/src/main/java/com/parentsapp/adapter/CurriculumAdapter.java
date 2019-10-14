package com.parentsapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.parentsapp.R;
import com.parentsapp.models.Item;

import java.util.List;

public class CurriculumAdapter extends RecyclerView.Adapter<CurriculumAdapter.MyViewHolder> {
    private List<Item> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
        }

    }

    public CurriculumAdapter(List<Item> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.curriculum_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = list.get(position);

        holder.title.setText(item.getItem());
        holder.content.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}