package com.tickets.ui.routes;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.tickets.databinding.FragmentSearchBinding;
import com.tickets.models.routes.Card;
import com.tickets.models.routes.Place;
import com.tickets.models.routes.RoutesViewModel;
import com.tickets.ui.ChildFragment;
import com.tickets.ui.routes.dialogs.RoutesCardsAdapter;
import com.tickets.ui.routes.dialogs.RoutesPlacesAdapter;
import com.tickets.widgets.BottomSheetDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
        RoutesViewModel viewModel = new ViewModelProvider(requireActivity()).get(RoutesViewModel.class);

        RoutesCardsAdapter adapter = new RoutesCardsAdapter();

        adapter.updateList(new ArrayList<Card>() {{
            add(new Card(2800, "KZT"));
            add(new Card(7200, "KZT"));
            add(new Card(3500, "KZT"));
        }});

        Context context = getContext();

        adapter.setOniItemClickListener(new RoutesCardsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Card card) {
                try {
                    Document document = new Document();

                    OutputStream outputStream = null;
                    try {
                        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File file = new File(folder, "ticket.pdf");
                        outputStream = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        PdfWriter.getInstance(document, outputStream);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }
                    document.open();

                    String routeName = viewModel.getPlaceFrom().getValue().getName() + " - " + viewModel.getPlaceTo().getValue().getName();
                    int numPassengers = viewModel.getCount().getValue();
                    int numPeopleOnBus = 24;
                    String content = "Ticket Information: \nRoute Name: " + routeName + "\nNumber of Passengers: " + numPassengers + "\nNumber of People on the Bus: " + numPeopleOnBus;

                    Paragraph ticketInfo = new Paragraph(content);
                    try {
                        document.add(ticketInfo);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }

                    BarcodeQRCode barcodeQRCode = new BarcodeQRCode("Bus Tickets: Route Name, Number of Passengers, Number of People on the Bus", 1000, 1000, null);

                    Image qrcodeImage = null;
                    try {
                        qrcodeImage = barcodeQRCode.getImage();
                    } catch (BadElementException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        document.add(qrcodeImage);
                    } catch (DocumentException e) {
                        throw new RuntimeException(e);
                    }

                    document.close();
                    Toast.makeText(context, "Ticket saved :)", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Ticket is not saved :(", Toast.LENGTH_SHORT).show();
                }
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