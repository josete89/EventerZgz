package com.eventerzgz.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.eventerzgz.presenter.BasePresenter;
import com.eventerzgz.view.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Tut3Fragment extends Fragment {
    private GoogleMap map;
    public static MapView mapView;
    private CheckBox checkBoxAdjuntarPosicion;

    private Marker marker = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.tuto_step3, container, false);

        try {
            mapView = (MapView) rootView.findViewById(R.id.mapview);
            checkBoxAdjuntarPosicion = (CheckBox)rootView.findViewById(R.id.checkBoxAdjuntarPosicion);
            mapView.onCreate(null);
            mapView.setClickable(true);
            ImageView transparentImageView = (ImageView) rootView.findViewById(R.id.transparent_image);

            transparentImageView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
            configMap(Tut3Fragment.this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button buttonClose = (Button) rootView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (getActivity()).finish();
                openListEvents();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // -----------------------------------------------------------------------------------------------------
    // CONFIG MAP
    // -----------------------------------------------------------------------------------------------------

    private void configMap(final Context context) {


        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        LatLng myLocation;
        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (map.getMyLocation() != null) {
            myLocation = new LatLng(map.getMyLocation().getLatitude(),
                    map.getMyLocation().getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                    13));

        }else {
            myLocation = new LatLng(41.654935, -0.875475);
        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                13));

        mapView.invalidate();


        // CLICK MAP
        // ---------
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                if (checkBoxAdjuntarPosicion.isChecked()) {
                    if (marker != null) {
                        removeMarkerFromMap(marker);
                    }
                    marker = addMarkerToMap(point.latitude, point.longitude);
                    BasePresenter.saveLocationPushInPreferences(point.latitude, point.longitude, context);
                }else{
                    if (marker != null) {
                        removeMarkerFromMap(marker);
                    }
                    Toast.makeText(context, context.getResources().getString(R.string.error_indique_check_posicion), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // -------------------------------------------------------------------------
    // ADD MARKER TO MAP
    // -------------------------------------------------------------------------
    private Marker addMarkerToMap(double latitude, double longitude) {

        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(
                latitude, longitude)));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                latitude, longitude), 18));
        mapView.invalidate();
        return marker;

    }

    // -------------------------------------------------------------------------
    // REMOVE MARKER FROM MAP
    // -------------------------------------------------------------------------
    private void removeMarkerFromMap(Marker marker) {
        marker.remove();
    }

    private void openListEvents() {
        BasePresenter.saveTutorialMade(getActivity());
        Intent intent = new Intent(getActivity(), ListEventsActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
        super.onResume();
    }


}