package com.tickets.ui.routes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.tickets.R;
import com.tickets.databinding.FragmentRoutesBinding;
import com.tickets.models.routes.Place;
import com.tickets.models.routes.PlacesLoader;
import com.tickets.models.routes.RoutesViewModel;
import com.tickets.widgets.BottomSheetDialogFragment;

import java.util.ArrayList;

public class RoutesFragment extends Fragment {

    private FragmentRoutesBinding binding;
    private enum DialogType {
            FROM,
            TO,
    };
    private DialogType dialogType;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RoutesViewModel routesViewModel =
                new ViewModelProvider(requireActivity()).get(RoutesViewModel.class);

        binding = FragmentRoutesBinding.inflate(inflater, container, false);

        BottomSheetDialogFragment bottomSheetDialog = new BottomSheetDialogFragment();
        FragmentManager supportFragmentManager = getChildFragmentManager();

        RoutesSelectPlaceDialogFragment selectPlaceDialog = new RoutesSelectPlaceDialogFragment();
        bottomSheetDialog.setContent(selectPlaceDialog);

        ArrayList<Place> data = routesViewModel.getPlaces().getValue();

        if (data != null && data.size() == 0) {
            routesViewModel.setPlaces(PlacesLoader.readData(getContext()));
        }

        binding.textInputFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogType = DialogType.FROM;
                selectPlaceDialog.setTitle(getString(R.string.fragment_routes_text_from));
                bottomSheetDialog.show(supportFragmentManager, BottomSheetDialogFragment.getStaticTag());
            }
        });

        binding.textInputTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogType = DialogType.TO;
                selectPlaceDialog.setTitle(getString(R.string.fragment_routes_text_to));
                bottomSheetDialog.show(supportFragmentManager, BottomSheetDialogFragment.getStaticTag());
            }
        });

        selectPlaceDialog.setOniItemClickListener(new RoutesPlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Place place) {
                if (dialogType == DialogType.FROM) {
                    routesViewModel.setPlaceFrom(place);
                    binding.textInputFrom.setText(place.getName());
                }

                if (dialogType == DialogType.TO) {
                    routesViewModel.setPlaceTo(place);
                    binding.textInputTo.setText(place.getName());
                }

                routesViewModel.filterPlaces("");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}