package com.example.collect.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collect.ObjectDetails;
import com.example.collect.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<String> objects;

    public  Adapter(List<String> titles, List<String> objects){
        this.titles = titles;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collections,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.objectTitle.setText(titles.get(position));
        holder.objectContent.setText(objects.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ObjectDetails.class);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView objectTitle, objectContent;
        View view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            objectTitle = itemView.findViewById(R.id.title);
            objectContent = itemView.findViewById(R.id.content);
            view = itemView;
        }
    }
}
