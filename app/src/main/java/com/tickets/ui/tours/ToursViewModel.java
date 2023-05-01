package com.tickets.ui.tours;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToursViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ToursViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это фрагмент туров");
    }

    public LiveData<String> getText() {
        return mText;
    }
}