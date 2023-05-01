package com.tickets.ui.routes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoutesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RoutesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это фрагмент маршрутов");
    }

    public LiveData<String> getText() {
        return mText;
    }
}