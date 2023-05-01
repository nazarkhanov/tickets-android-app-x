package com.tickets.ui.cabinet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CabinetViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CabinetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это фрагмент кабинета");
    }

    public LiveData<String> getText() {
        return mText;
    }
}