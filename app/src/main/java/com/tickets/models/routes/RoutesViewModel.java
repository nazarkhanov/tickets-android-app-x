package com.tickets.models.routes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class RoutesViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Place>> places;
    private final MutableLiveData<ArrayList<Place>> filteredPlaces;
    private final MutableLiveData<String> filterText;
    private final MutableLiveData<Place> placeFrom;
    private final MutableLiveData<Place> placeTo;

    public RoutesViewModel() {
        places = new MutableLiveData<>();
        filteredPlaces = new MutableLiveData<>();
        filterText = new MutableLiveData<>();
        placeFrom = new MutableLiveData<>();
        placeTo = new MutableLiveData<>();

        places.setValue(PlacesService.fetch());
        filteredPlaces.setValue(PlacesService.fetch());
    }

    public MutableLiveData<ArrayList<Place>> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places.setValue(places);
        this.filteredPlaces.setValue(places);
        filterPlaces("");
    }

    public MutableLiveData<ArrayList<Place>> getFilteredPlaces() {
        return filteredPlaces;
    }

    public MutableLiveData<String> getFilterText() {
        return filterText;
    }

    public MutableLiveData<Place> getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(Place place) {
        placeFrom.setValue(place);
    }

    public MutableLiveData<Place> getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(Place place) {
        placeTo.setValue(place);
    }

    public void filterPlaces(String text) {
        ArrayList<Place> _places = places.getValue();
        ArrayList<Place> temp = new ArrayList<>();

        if (_places == null) return;

        for(Place place: _places){
            boolean isNameContainsInput = place.getName().toLowerCase().contains(text.toString().toLowerCase());
            boolean isSourceContainsInput = place.getSource().toLowerCase().contains(text.toString().toLowerCase());
            boolean isCodeContainsInput = place.getCode().toLowerCase().contains(text.toString().toLowerCase());

            if(isNameContainsInput || isSourceContainsInput || isCodeContainsInput){
                temp.add(place);
            }
        }

        filteredPlaces.setValue(temp);
        filterText.setValue(text);
    }
}