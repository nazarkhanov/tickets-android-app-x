package com.tickets.ui.routes.dialogs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tickets.databinding.DialogFragmentSelectPlaceBinding;
import com.tickets.models.routes.Place;
import com.tickets.models.routes.RoutesViewModel;
import com.tickets.widgets.BottomSheetDialog;

import java.util.ArrayList;

public class RoutesSelectPlaceDialogFragment extends Fragment {

    private DialogFragmentSelectPlaceBinding binding;
    private RoutesPlacesAdapter.OnItemClickListener listener;
    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DialogFragmentSelectPlaceBinding.inflate(inflater, container, false);

        RoutesViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(RoutesViewModel.class);

        binding.textInput.setText(viewModel.getFilterText().getValue());

        binding.textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.filterPlaces(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        RoutesPlacesAdapter adapter = new RoutesPlacesAdapter();

        viewModel.getFilteredPlaces().observe(getViewLifecycleOwner(), new Observer<ArrayList<Place>>() {
            @Override
            public void onChanged(ArrayList<Place> places) {
                adapter.updateList(places);
            }
        });

        adapter.setOniItemClickListener(new RoutesPlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Place place) {
                binding.textInput.setText(viewModel.getFilterText().getValue());

                Bundle result = new Bundle();

                result.putSerializable(BottomSheetDialog.ACTION_KEY, BottomSheetDialog.Actions.CLOSE);
                getParentFragmentManager().setFragmentResult(BottomSheetDialog.getStaticTag(), result);

                listener.onItemClick(place);}
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        binding.recyclePlacesList.setLayoutManager(layoutManager);
        binding.recyclePlacesList.setHasFixedSize(true);
        binding.recyclePlacesList.setAdapter(adapter);

        if (title != null && !title.isEmpty()) {
            binding.textTitle.setText(title);
        }

        return binding.getRoot();
    }

    public void setOniItemClickListener(RoutesPlacesAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setTitle(String text) {
        title = text;
    }
}