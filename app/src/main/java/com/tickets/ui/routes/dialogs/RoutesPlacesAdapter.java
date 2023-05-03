package com.tickets.ui.routes.dialogs;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tickets.databinding.ItemPlaceBinding;
import com.tickets.models.routes.Place;

import java.util.ArrayList;

public class RoutesPlacesAdapter extends RecyclerView.Adapter<RoutesPlacesAdapter.RoutesPlaceViewHolder> {

    private ArrayList<Place> list;

    private OnItemClickListener listener;

    @NonNull
    @Override
    public RoutesPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPlaceBinding binding = ItemPlaceBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false);

        return new RoutesPlaceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesPlaceViewHolder holder, int position) {
        Place item = list.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Place> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOniItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class RoutesPlaceViewHolder extends RecyclerView.ViewHolder {

        private final ItemPlaceBinding binding;

        public RoutesPlaceViewHolder(ItemPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Place item, OnItemClickListener listener) {
            binding.textPlaceName.setText(item.getName());
            binding.textPlaceSource.setText(item.getSource());
            binding.textPlaceCode.setText(item.getCode());

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener == null) return;
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Place place);
    }
}