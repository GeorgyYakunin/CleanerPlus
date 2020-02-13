package com.fchatnet;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fchatnet.ramboost.R;

import java.util.List;

public class Memory_Adapter extends RecyclerView.Adapter<Memory_Adapter.MyViewHolder> {
    public List<Apps> apps;

    public Memory_Adapter(List<Apps> getapps) {
        apps = getapps;
    }

    @Override
    public Memory_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mem_custom, parent, false);
        return new Memory_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Memory_Adapter.MyViewHolder holder, int position) {

        Apps app = apps.get(position);
        holder.size.setText(app.getSize());
        holder.image.setImageDrawable(app.getImage());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView size;
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            size = (TextView) view.findViewById(R.id.apptext);
            image = (ImageView) view.findViewById(R.id.appimage);

        }
    }



}

