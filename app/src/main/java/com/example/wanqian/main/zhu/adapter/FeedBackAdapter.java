package com.example.wanqian.main.zhu.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanqian.R;

import java.util.ArrayList;
import java.util.List;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.Myholder> {
    private Context context;
    private List<Uri> list = new ArrayList();
    private Listener listener;

    public FeedBackAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, final int position) {
        holder.img.setImageURI(list.get(position));
        holder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                listener.onSuccess(list.size(), list);
                return false;
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onSuccess(int id, List<Uri> list);
    }

    public void setData(List<Uri> list) {
        this.list.addAll(list);
        listener.onSuccess(this.list.size(), this.list);
        notifyDataSetChanged();
    }

    public void setCamearData(Uri data) {
        this.list.add(data);
        listener.onSuccess(this.list.size(), this.list);
        notifyDataSetChanged();
    }

    public void clearData() {
        list.removeAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {

        ImageView img;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}
