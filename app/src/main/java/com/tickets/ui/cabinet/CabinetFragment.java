package com.tickets.ui.cabinet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tickets.databinding.FragmentCabinetBinding;

public class CabinetFragment extends Fragment {

    private FragmentCabinetBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CabinetViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CabinetViewModel.class);

        binding = FragmentCabinetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCabinet;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}