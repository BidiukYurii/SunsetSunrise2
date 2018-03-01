package com.bidyuk.tasks.sunsetsunrise.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bidyuk.tasks.sunsetsunrise.MainActivity;
import com.bidyuk.tasks.sunsetsunrise.Presenters.ParseJSONPresenter;
import com.bidyuk.tasks.sunsetsunrise.R;
import com.bidyuk.tasks.sunsetsunrise.UsersContractView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;


public class ResultsFragment extends Fragment implements UsersContractView {

    private View root;
    private ParseJSONPresenter presenter;
    private Place place;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_results, container, false);

        init();
        initListeners();

        return root;
    }

    private void init(){
        presenter = new ParseJSONPresenter(place.getLatLng());
        presenter.attachView(this);

        ((TextView)root.findViewById(R.id.got_place_tv)).setText(place.getName());
    }

    private void initListeners() {

        root.findViewById(R.id.show_info_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadData();

                root.findViewById(R.id.linear_layout).setVisibility(View.INVISIBLE);
                root.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }
        });

        root.findViewById(R.id.try_again_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showLaunch();
            }
        });
    }

    public ResultsFragment setPlace(Place place){
        this.place = place;

        return this;
    }

    @Override
    public void setInfo(String first, String second) {
        //set sunrise
        ((TextView)root.findViewById(R.id.sunrise_tv)).setText(first);
        //set sunset
        ((TextView)root.findViewById(R.id.sunset_tv)).setText(second);

        root.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.linear_layout).setVisibility(View.VISIBLE);
    }
}
