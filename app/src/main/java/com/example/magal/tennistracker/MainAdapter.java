package com.example.magal.tennistracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<String> infosMatch;
    public MainAdapter(ArrayList<String> infos){
        infosMatch = infos;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {


        holder.numero.setText(infosMatch.get(position));
        holder.nameJ1.setText(infosMatch.get(position));
        holder.nameJ2.setText(infosMatch.get(position));
        holder.scoreJ1.setText(infosMatch.get(position));
        holder.scoreJ2.setText(infosMatch.get(position));
        holder.aceJ1.setText(infosMatch.get(position));
        holder.aceJ2.setText(infosMatch.get(position));
        holder.fauteJ1.setText(infosMatch.get(position));
        holder.fauteJ2.setText(infosMatch.get(position));



    }

    @Override
    public int getItemCount() {
        return infosMatch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameJ1, nameJ2, scoreJ1, scoreJ2, aceJ1, aceJ2, fauteJ1, fauteJ2, numero;

        public ViewHolder(View itemView){
            super(itemView);

            numero = itemView.findViewById(R.id.numero);

            nameJ1 = itemView.findViewById(R.id.match5_J1);
            nameJ2 = itemView.findViewById(R.id.match5_J2);

            scoreJ1 = itemView.findViewById(R.id.scoreJ1);
            scoreJ2 = itemView.findViewById(R.id.scoreJ2);

            aceJ1 = itemView.findViewById(R.id.acesJ1);
            aceJ2 = itemView.findViewById(R.id.acesJ2);

            fauteJ1 = itemView.findViewById(R.id.fauteJ1);
            fauteJ2 = itemView.findViewById(R.id.fauteJ2);


        }
    }
}
