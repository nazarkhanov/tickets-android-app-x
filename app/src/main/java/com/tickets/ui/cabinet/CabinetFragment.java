package com.tickets.ui.cabinet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.tickets.R;
import com.tickets.databinding.FragmentCabinetBinding;

import java.util.function.Consumer;

public class CabinetFragment extends Fragment {

    private FragmentCabinetBinding binding;

    private enum NavItemType {
        ROUTES,
        TOURS,
    }

    private interface NavItemListener {
        View.OnClickListener apply(NavItemType navItemType);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CabinetViewModel cabinetViewModel =
                new ViewModelProvider(this).get(CabinetViewModel.class);

        binding = FragmentCabinetBinding.inflate(inflater, container, false);

        NavItemListener listener = new NavItemListener() {
            @Override
            public View.OnClickListener apply(NavItemType navItemType) {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int resId;

                        switch (navItemType) {
                            case ROUTES:
                                resId = R.id.navigation_routes;
                                break;
                            case TOURS:
                                resId = R.id.navigation_tours;
                                break;
                            default:
                                return;
                        }

                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        NavOptions options = new NavOptions.Builder()
                                .setPopUpTo(navController.getCurrentDestination().getId(), true)
                                .build();
                        navController.navigate(resId, null, options);
                    }
                };
            }
        };

        binding.navItemRoutes.setOnClickListener(listener.apply(NavItemType.ROUTES));
        binding.navItemTours.setOnClickListener(listener.apply(NavItemType.TOURS));

        binding.navItemSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:shtreeets@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Поддержка Tickets");
                intent.putExtra(Intent.EXTRA_TEXT, "Можете мне помочь. У меня проблема с ");
                startActivity(intent);
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