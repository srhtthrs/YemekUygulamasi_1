package com.serhatozturk.yemekuygulamasi_10;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serhatozturk.yemekuygulamasi_10.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class AdapterYemek extends RecyclerView.Adapter<AdapterYemek.ModelHolder>{

    private ArrayList<ModelYemek> modelYemekArrayList;
    public AdapterYemek(ArrayList<ModelYemek> modelYemekArrayList) {
        this.modelYemekArrayList = modelYemekArrayList;}



    @NonNull
    @Override
    public ModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ModelHolder(recyclerRowBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ModelHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.recyclerRowBinding.textViewSicil.setText(modelYemekArrayList.get(position).sicil);
        holder.recyclerRowBinding.textViewYil.setText(modelYemekArrayList.get(position).yil);
        holder.recyclerRowBinding.textViewAy.setText(modelYemekArrayList.get(position).ay);

        holder.recyclerRowBinding.textViewGun.setText(modelYemekArrayList.get(position).toplamGun);
        holder.recyclerRowBinding.textViewTutar.setText(modelYemekArrayList.get(position).toplamUcret);


        holder.recyclerRowBinding.buttonSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idGonder=modelYemekArrayList.get(position).id;
                Intent intent = new Intent(holder.itemView.getContext(), ListeleActivity.class);
                intent.putExtra("idGonder",idGonder);
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelYemekArrayList.size();
    }

    class ModelHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;
        public ModelHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;
        }
    }


}
