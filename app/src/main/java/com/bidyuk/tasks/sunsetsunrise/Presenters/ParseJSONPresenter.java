package com.bidyuk.tasks.sunsetsunrise.Presenters;

import android.util.Pair;

import com.bidyuk.tasks.sunsetsunrise.Fragments.ResultsFragment;
import com.bidyuk.tasks.sunsetsunrise.Models.ParseJSONModel;
import com.bidyuk.tasks.sunsetsunrise.UsersContractView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Yura on 01.03.2018.
 */

public class ParseJSONPresenter {
    private final ParseJSONModel model;
    private final LatLng latLng;
    private UsersContractView view;

    public ParseJSONPresenter(LatLng latLng){
        this.latLng = latLng;

        model = new ParseJSONModel();
    }

    public void attachView(UsersContractView view){
        this.view = view;
    }

    public void loadData(){
        model.loadData(latLng, new ParseJSONModel.ParseJSONCallback() {
            @Override
            public void onParsed(Pair<String, String> pairOfResult) {
                //first is Sunrise, second is Sunset
                if (pairOfResult != null)
                    view.setInfo(pairOfResult.first, pairOfResult.second);
                else
                    return;
            }
        });
    }
}
