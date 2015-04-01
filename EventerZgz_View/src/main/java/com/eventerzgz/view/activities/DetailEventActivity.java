package com.eventerzgz.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.detail.DetailEventPresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.share.SocialShare;
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
    private TextView textViewFecha;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private ImageView imageViewDetail;
    private TextView textViewDireccion;
    private TextView textViewTelefono;
    private TextView textViewConexion;
    private TextView textViewLugar;
    private TextView textViewWeb;
    private TextView textViewCategoria;

    //Data intent
    //-----------
    private int posEventSelected;
    private Event eventSelected;
    private boolean eventFiltered = false;

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
            eventSelected = (Event) extras.getSerializable("eventObject");
        }

        //View
        //----
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewFecha = (TextView) findViewById(R.id.textViewFecha);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewDireccion = (TextView) findViewById(R.id.textViewDireccion);
        textViewTelefono = (TextView) findViewById(R.id.textViewTelefono);
        textViewConexion = (TextView) findViewById(R.id.textViewConexion);
        imageViewDetail = (ImageView)findViewById(R.id.imageViewDetail);
        textViewLugar = (TextView)findViewById(R.id.textViewLugar);
        textViewWeb = (TextView)findViewById(R.id.textViewWeb);
        textViewCategoria = (TextView)findViewById(R.id.textViewCategoria);
        setInfoEvent();

        try {
            mapView = (MapView) findViewById(R.id.mapview);
            mapView.onCreate(savedInstanceState);
            configMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Click WEB
        //---------
        textViewWeb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((TextView) v).getText() != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(((TextView) v).getText().toString())));
                }
            }
        });
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
        if(eventSelected.getsDescription()!=null) {
            textViewDescription
                    .setText(Html.fromHtml(eventSelected.getsDescription()));
        }
        if(eventSelected.getsImage()!=null && !eventSelected.getsImage().equals("")){
            ImageLoader.getInstance().displayImage((eventSelected.getFieldWithUri(eventSelected.getsImage())), imageViewDetail);
        }else{
            imageViewDetail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        try {
            if(eventSelected.getdStartDate()!=null && eventSelected.getdEndDate()!=null){
                if(eventSelected.getStartDateForPresentantion().equals(eventSelected.getEndDateForPresentation())){
                    textViewFecha.setText(eventSelected.getEndDateForPresentation());
                }else{
                    textViewFecha.setText(eventSelected.getStartDateForPresentantion()+" - "+eventSelected.getEndDateForPresentation());
                }
            }else {
                textViewFecha.setText(eventSelected.getEndDateForPresentation());
            }
            textViewCategoria.setText(eventSelected.getCategoryList().get(0).getsTitle());
            if(eventSelected.getSubEvent().getWhere().getsAddress()!= null && !"".equals(eventSelected.getSubEvent().getWhere().getsAddress())){
                findViewById(R.id.layoutFecha).setVisibility(View.VISIBLE);
                textViewDireccion.setText(eventSelected.getSubEvent().getWhere().getsAddress());
            }

            if(eventSelected.getSubEvent().getWhere().getsTelephone()!= null && !"".equals(eventSelected.getSubEvent().getWhere().getsTelephone())){
                findViewById(R.id.layoutTelefono).setVisibility(View.VISIBLE);
                textViewTelefono.setText(eventSelected.getSubEvent().getWhere().getsTelephone());
            }

            if(eventSelected.getSubEvent().getWhere().getsBus()!= null && !"".equals(eventSelected.getSubEvent().getWhere().getsBus())){
                findViewById(R.id.layoutConexion).setVisibility(View.VISIBLE);
                textViewConexion.setText(eventSelected.getSubEvent().getWhere().getsBus());
            }

            if(eventSelected.getSubEvent().getWhere().getsTitle()!= null && !"".equals(eventSelected.getSubEvent().getWhere().getsTitle())){
                findViewById(R.id.layoutLugar).setVisibility(View.VISIBLE);
                textViewLugar.setText(eventSelected.getSubEvent().getWhere().getsTitle());
            }

            if(eventSelected.getsWeb()!=null) {
                findViewById(R.id.layoutWeb).setVisibility(View.VISIBLE);
                textViewWeb.setText(eventSelected.getsWeb());
            }
            Spannable spanna = new SpannableString(eventSelected.getCategoryList().get(0).getsTitle());
            spanna.setSpan(new BackgroundColorSpan(0xBFc0392b), 0, eventSelected.getCategoryList().get(0).getsTitle().length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewCategoria.setText(spanna);
        }catch (Exception e){
            e.printStackTrace();
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

    // -----------------------------------------------------------------------------------------------------
    // ONCLICK DETAIL
    // -----------------------------------------------------------------------------------------------------
    public void onClickDetail(View view){
        switch (view.getId()){
            case R.id.btnCreate:
                new DetailEventPresenter().addCalendarEvent(eventSelected, DetailEventActivity.this);
                break;

            case R.id.btnShare:
                String url;
                if (eventSelected.getsWeb() != null) {
                    url = "Evento enviado a través de EventerZgz: " + eventSelected.getsWeb();
                } else {
                    url = "¡¿Qué te parece este evento?!\r\n" + eventSelected.getsTitle() + "\r\n" + eventSelected.getsDescription();
                }
                SocialShare.share(DetailEventActivity.this, url);
                break;
            case R.id.textViewTelefono:
                if (((TextView) view).getText() != null) {
                    callPhone(((TextView) view).getText().toString());
                }
                break;
        }

    }

    // ----------------------------------------------------------------------
    // CALL PHONE
    // ----------------------------------------------------------------------
    private void callPhone(String telefono) {
        try {

            if (telefono != null) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + telefono));
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
