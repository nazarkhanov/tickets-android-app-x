package com.tickets.ui.routes.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tickets.databinding.ItemCountBinding;

public class RoutesCountAdapter extends RecyclerView.Adapter<RoutesCountAdapter.RoutesCountViewHolder> {

    private final int COUNT = 25;

    private OnItemClickListener listener;

    @NonNull
    @Override
    public RoutesCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountBinding binding = ItemCountBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false);

        return new RoutesCountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesCountViewHolder holder, int position) {
        holder.bind(position, listener);
    }

    @Override
    public int getItemCount() {
        return COUNT;
    }

    public void setOniItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class RoutesCountViewHolder extends RecyclerView.ViewHolder {

        private final ItemCountBinding binding;

        public RoutesCountViewHolder(ItemCountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position, OnItemClickListener listener) {
            binding.textMain.setText(String.valueOf(position + 1));

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener == null) return;
                    listener.onItemClick(position + 1);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int count);
    }
}