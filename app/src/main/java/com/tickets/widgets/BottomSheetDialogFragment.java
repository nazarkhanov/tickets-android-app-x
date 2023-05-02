package com.tickets.widgets;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tickets.R;
import com.tickets.databinding.WidgetFragmentBottomSheetDialogBinding;

public class BottomSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    private WidgetFragmentBottomSheetDialogBinding binding;
    private BottomSheetDialog dialog;
    private Fragment fragment;

    public static enum Actions {
        CLOSE,
    }

    public static String ACTION_KEY = "ACTION";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = WidgetFragmentBottomSheetDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog baseDialog = getDialog();
        if (!(baseDialog instanceof BottomSheetDialog)) return;
        dialog = (BottomSheetDialog) baseDialog;

        setDialogHeight();
        setCloseButtonListener();

        FragmentManager childManager = getChildFragmentManager();

        childManager.setFragmentResultListener(BottomSheetDialogFragment.getStaticTag(), this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                Actions action = (Actions) bundle.get(ACTION_KEY);
                if (action == Actions.CLOSE) dialog.dismiss();
            }
        });

        if (fragment == null || fragment.isAdded()) return;
        childManager.beginTransaction().replace(R.id.dialog_content, fragment).commit();
    }

    private void setDialogHeight() {
        BottomSheetBehavior<?> behavior = dialog.getBehavior();

        float SCREEN_EXPAND_RATIO = (float) 0.92;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int screenOffset = (int) (screenHeight * (1 - SCREEN_EXPAND_RATIO));

        behavior.setSkipCollapsed(true);
        behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        behavior.setHalfExpandedRatio(SCREEN_EXPAND_RATIO);

        ViewGroup viewGroup = binding.dialogContent;
        viewGroup.setMinimumHeight(screenHeight - screenOffset);
    }

    private void setCloseButtonListener() {
        binding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void setContent(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public int getTheme() {
        return R.style.ThemeBottomSheetDialog;
    }

    public static String getStaticTag() {
        return BottomSheetDialogFragment.class.getName().toString();
    }
}