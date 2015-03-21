package com.eventerzgz.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eventerzgz.model.event.Event;
import com.eventerzgz.view.R;
import com.eventerzgz.view.application.EventerZgzApplication;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailEventActivity extends ActionBarActivity {

    //View
    //----
    private TextView textViewTitle;
    private TextView textViewDescription;
    private ImageView imageViewDetail;

    //Data intent
    //-----------
    private int posEventSelected;
    private Event eventSelected;

    // Mapa
    // ----
    private MapView mapView;
    private GoogleMap map;
    private double latitudePoint;
    private double longitudePoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            posEventSelected = extras.getInt(EventerZgzApplication.INTENT_EVENT_SELECTED);
            eventSelected = EventerZgzApplication.eventsList.get(posEventSelected);
        }

        //View
        //----
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        imageViewDetail = (ImageView)findViewById(R.id.imageViewDetail);
        setInfoEvent();

        try {
            mapView = (MapView) findViewById(R.id.mapview);
            mapView.onCreate(savedInstanceState);
            configMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    //--------------------------------------------------------------------
    // SET INFO EVENT
    //--------------------------------------------------------------------
    private void setInfoEvent(){
        textViewTitle.setText(eventSelected.getsTitle());
        textViewDescription
                .setText(Html.fromHtml(eventSelected.getsDescription()));
        if(eventSelected.getsImage()!=null && !eventSelected.getsImage().equals("")){
            ImageLoader.getInstance().displayImage((eventSelected.getFieldWithUri(eventSelected.getsImage())), imageViewDetail);
        }else{
            imageViewDetail.setVisibility(View.GONE);
        }
    }


    // -----------------------------------------------------------------------------------------------------
    // CONFIG MAP
    // -----------------------------------------------------------------------------------------------------
    private void configMap() {
        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
//		map.setMyLocationEnabled(true);

        try {
            MapsInitializer.initialize(DetailEventActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(eventSelected.getObjCoordinates()!=null){
            addMarkerToMap((double)eventSelected.getObjCoordinates().getfLatitude(),(double)eventSelected.getObjCoordinates().getfLonguide());
        }

        // CLICK MAP
        // ---------
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                intentOpenMap();
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                intentOpenMap();
                return true;
            }
        });

    }

    // -------------------------------------------------------------------------
    // ADD MARKER TO MAP
    // -------------------------------------------------------------------------
    private Marker addMarkerToMap(double latitude, double longitude) {
        latitudePoint = latitude;
        longitudePoint = longitude;
        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(
                latitude, longitude)));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                latitude, longitude), 13));
        mapView.invalidate();
        return marker;

    }

    // -----------------------------------------------------------------------------------------------------
    // INTENT OPEN MAP
    // -----------------------------------------------------------------------------------------------------
    private void intentOpenMap() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + latitudePoint + "," + longitudePoint));
        startActivity(intent);
    }

}
