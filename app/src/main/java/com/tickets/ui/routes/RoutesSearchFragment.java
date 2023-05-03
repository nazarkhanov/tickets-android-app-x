package com.tickets.ui.routes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tickets.databinding.FragmentSearchBinding;
import com.tickets.models.routes.Card;
import com.tickets.models.routes.Place;
import com.tickets.ui.ChildFragment;
import com.tickets.ui.routes.dialogs.RoutesCardsAdapter;
import com.tickets.ui.routes.dialogs.RoutesPlacesAdapter;
import com.tickets.widgets.BottomSheetDialog;

import java.util.ArrayList;

public class RoutesSearchFragment extends ChildFragment {

    private FragmentSearchBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        RoutesCardsAdapter adapter = new RoutesCardsAdapter();

        adapter.updateList(new ArrayList<Card>() {{
            add(new Card(2800, "KZT"));
            add(new Card(7200, "KZT"));
            add(new Card(3500, "KZT"));
        }});

        adapter.setOniItemClickListener(new RoutesCardsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Card card) {
                Toast.makeText(getContext(), card.getPrice() + card.getCurrency(), Toast.LENGTH_LONG).show();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        binding.recycleCardList.setLayoutManager(layoutManager);
        binding.recycleCardList.setHasFixedSize(true);
        binding.recycleCardList.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}