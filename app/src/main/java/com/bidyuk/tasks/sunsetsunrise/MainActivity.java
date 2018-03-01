package com.bidyuk.tasks.sunsetsunrise;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bidyuk.tasks.sunsetsunrise.Fragments.LaunchFragment;
import com.bidyuk.tasks.sunsetsunrise.Fragments.ResultsFragment;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLaunch();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void setPlace(Place place){
        this.place = place;
    }

    public void showLaunch() {
        replaceFragment(new LaunchFragment());
    }

    public void showResultsFragment(){
        replaceFragment(new ResultsFragment().setPlace(place));
    }

}
