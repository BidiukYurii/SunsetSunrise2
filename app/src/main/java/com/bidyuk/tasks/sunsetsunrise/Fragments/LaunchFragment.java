package com.bidyuk.tasks.sunsetsunrise.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bidyuk.tasks.sunsetsunrise.MainActivity;
import com.bidyuk.tasks.sunsetsunrise.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.app.Activity.RESULT_OK;


public class LaunchFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private View root;
    private GoogleApiClient googleApiClient;
    private final int PLACE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_launch, container, false);

        initListeners();
        init();

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.stopAutoManage(getActivity());
    }

    private void initListeners(){
        root.findViewById(R.id.choos_place_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPlace();
            }
        });
    }

    private void init(){
        if (googleApiClient == null)
            googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_REQUEST && resultCode== RESULT_OK) {
            ((MainActivity) getActivity()).setPlace(PlacePicker.getPlace(data, getContext()));
            ((MainActivity) getActivity()).showResultsFragment();
        }
    }

    public void getPlace() {
        if(googleApiClient != null && !googleApiClient.isConnected())
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(getActivity()), PLACE_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Please allow ACCESS_FINE_LOCATION persmission.",
                    Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
