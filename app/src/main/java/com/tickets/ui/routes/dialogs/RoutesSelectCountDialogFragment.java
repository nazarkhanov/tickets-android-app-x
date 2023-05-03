package com.tickets.ui.routes.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tickets.databinding.DialogFragmentSelectCountBinding;
import com.tickets.models.routes.RoutesViewModel;
import com.tickets.widgets.BottomSheetDialog;

public class RoutesSelectCountDialogFragment extends Fragment {

    private DialogFragmentSelectCountBinding binding;
    private RoutesCountAdapter.OnItemClickListener listener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DialogFragmentSelectCountBinding.inflate(inflater, container, false);

        RoutesViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(RoutesViewModel.class);

        RoutesCountAdapter adapter = new RoutesCountAdapter();

        adapter.setOniItemClickListener(new RoutesCountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int count) {
                Bundle result = new Bundle();

                result.putSerializable(BottomSheetDialog.ACTION_KEY, BottomSheetDialog.Actions.CLOSE);
                getParentFragmentManager().setFragmentResult(BottomSheetDialog.getStaticTag(), result);

                listener.onItemClick(count);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        binding.recyclePlacesList.setLayoutManager(layoutManager);
        binding.recyclePlacesList.setHasFixedSize(true);
        binding.recyclePlacesList.setAdapter(adapter);

        return binding.getRoot();
    }

    public void setOniItemClickListener(RoutesCountAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}