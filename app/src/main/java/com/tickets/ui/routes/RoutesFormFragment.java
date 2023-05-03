package com.tickets.ui.routes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.tickets.R;
import com.tickets.databinding.FragmentRoutesBinding;
import com.tickets.models.routes.Place;
import com.tickets.models.routes.PlacesLoader;
import com.tickets.models.routes.RoutesViewModel;
import com.tickets.ui.routes.dialogs.RoutesCountAdapter;
import com.tickets.ui.routes.dialogs.RoutesPlacesAdapter;
import com.tickets.ui.routes.dialogs.RoutesSelectCountDialogFragment;
import com.tickets.ui.routes.dialogs.RoutesSelectPlaceDialogFragment;
import com.tickets.utils.Date;
import com.tickets.widgets.BottomSheetDialog;
import com.tickets.widgets.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class RoutesFormFragment extends Fragment {

    private FragmentRoutesBinding binding;
    private enum DialogType {
            FROM,
            TO,
    };
    private DialogType dialogType;

    private enum DatePickerType {
        FORWARD,
        BACK,
    };
    private DatePickerType datePickerType;
    private RoutesViewModel viewModel;
    private FragmentManager fragmentManager;
    private BottomSheetDialog bottomSheetDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(RoutesViewModel.class);
        binding = FragmentRoutesBinding.inflate(inflater, container, false);

        fragmentManager = getChildFragmentManager();
        bottomSheetDialog = new BottomSheetDialog();

        initPlaceSelectBottomSheetDialog();
        initDatePickDialog();
        initCountSelectDialog();
        initVaildateWatchers();
        initSeachButton();

        return binding.getRoot();
    }

    private void initPlaceSelectBottomSheetDialog() {
        RoutesSelectPlaceDialogFragment selectPlaceDialog = new RoutesSelectPlaceDialogFragment();

        ArrayList<Place> data = viewModel.getPlaces().getValue();

        if (data != null && data.size() == 0) {
            viewModel.setPlaces(PlacesLoader.readData(getContext()));
        }

        binding.textInputFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogType = DialogType.FROM;
                selectPlaceDialog.setTitle(getString(R.string.fragment_routes_text_from));
                bottomSheetDialog.setContent(selectPlaceDialog);
                bottomSheetDialog.show(fragmentManager, BottomSheetDialog.getStaticTag());
            }
        });

        binding.textInputTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogType = DialogType.TO;
                selectPlaceDialog.setTitle(getString(R.string.fragment_routes_text_to));
                bottomSheetDialog.setContent(selectPlaceDialog);
                bottomSheetDialog.show(fragmentManager, BottomSheetDialog.getStaticTag());
            }
        });

        selectPlaceDialog.setOniItemClickListener(new RoutesPlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Place place) {
                if (dialogType == DialogType.FROM) {
                    viewModel.setPlaceFrom(place);
                    binding.textInputFrom.setText(place.getName());
                }

                if (dialogType == DialogType.TO) {
                    viewModel.setPlaceTo(place);
                    binding.textInputTo.setText(place.getName());
                }

                viewModel.filterPlaces("");
            }
        });
    }

    private void initDatePickDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

        binding.textInputForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerType = DatePickerType.FORWARD;
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        binding.textInputBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerType = DatePickerType.BACK;

                Calendar calendar = viewModel.getDateForward().getValue();
                datePickerDialog.getDatePicker().setMinDate(-2208902400000L);
                if (calendar != null) datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                else datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

                datePickerDialog.show();

            }
        });

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(Calendar calendar) {
                if (datePickerType == DatePickerType.FORWARD) {
                    viewModel.setDateForward((Calendar) calendar.clone());
                    binding.textInputForward.setText(Date.format(calendar));
                    return;
                }

                Calendar forwardCalendar = viewModel.getDateForward().getValue();
                boolean isDateMoreThanForward = forwardCalendar != null && (calendar.getTimeInMillis() >= forwardCalendar.getTimeInMillis());

                if (datePickerType == DatePickerType.BACK && isDateMoreThanForward) {
                    viewModel.setDateBack((Calendar) calendar.clone());
                    binding.textInputBack.setText(Date.format(calendar));
                }
            }
        });
    }

    private void initCountSelectDialog() {
        RoutesSelectCountDialogFragment selectCountDialog = new RoutesSelectCountDialogFragment();

        binding.textInputCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogType = DialogType.TO;
                bottomSheetDialog.setContent(selectCountDialog);
                bottomSheetDialog.show(fragmentManager, BottomSheetDialog.getStaticTag());
            }
        });

        selectCountDialog.setOniItemClickListener(new RoutesCountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int count) {
                viewModel.setCount(count);
                binding.textInputCount.setText(String.valueOf(count));
            }
        });
    }

    private void initVaildateWatchers() {
        viewModel.getPlaceFrom().observe(getViewLifecycleOwner(), new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                validateRoutesData();
            }
        });

        viewModel.getPlaceFrom().observe(getViewLifecycleOwner(), new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                validateRoutesData();
            }
        });

        viewModel.getPlaceFrom().observe(getViewLifecycleOwner(), new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                validateRoutesData();
            }
        });

        viewModel.getPlaceFrom().observe(getViewLifecycleOwner(), new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                validateRoutesData();
            }
        });

        viewModel.getPlaceTo().observe(getViewLifecycleOwner(), new Observer<Place>() {
            @Override
            public void onChanged(Place place) {
                validateRoutesData();
            }
        });

        viewModel.getDateForward().observe(getViewLifecycleOwner(), new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                validateRoutesData();
            }
        });

        viewModel.getDateBack().observe(getViewLifecycleOwner(), new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                validateRoutesData();
            }
        });

        viewModel.getCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                validateRoutesData();
            }
        });
    }

    private void validateRoutesData() {
        boolean isAllFieldsField =
                viewModel.getPlaceFrom().getValue() != null &&
                viewModel.getPlaceTo().getValue() != null &&
                viewModel.getDateForward().getValue() != null &&
                viewModel.getDateBack().getValue() != null &&
                viewModel.getCount().getValue() != null;

        binding.buttonMain.setEnabled(isAllFieldsField);
    }

    private void initSeachButton() {
        binding.buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                NavOptions options = new NavOptions.Builder()
                        .setPopUpTo(navController.getCurrentDestination().getId(), true)
                        .build();
                navController.navigate(R.id.navigation_search, null, options);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}