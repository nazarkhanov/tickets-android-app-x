package com.tickets.ui.routes.dialogs;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tickets.databinding.ItemCardBinding;
import com.tickets.models.routes.Card;

import java.util.ArrayList;

public class RoutesCardsAdapter extends RecyclerView.Adapter<RoutesCardsAdapter.RoutesCardViewHolder> {

    private ArrayList<Card> list;

    private OnItemClickListener listener;

    @NonNull
    @Override
    public RoutesCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false);

        return new RoutesCardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesCardViewHolder holder, int position) {
        Card item = list.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Card> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOniItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class RoutesCardViewHolder extends RecyclerView.ViewHolder {

        private final ItemCardBinding binding;

        public RoutesCardViewHolder(ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Card item, OnItemClickListener listener) {
            binding.textPrice.setText(String.valueOf(item.getPrice()) + " " + item.getCurrency());

            binding.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener == null) return;
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Card card);
    }
}