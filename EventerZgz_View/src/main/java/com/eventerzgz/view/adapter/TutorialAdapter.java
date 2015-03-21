package com.eventerzgz.view.adapter;

import org.taptwo.android.widget.TitleProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eventerzgz.view.R;
import com.eventerzgz.view.activities.ListEventsActivity;
import com.eventerzgz.view.utils.CheckBoxView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class TutorialAdapter extends BaseAdapter implements TitleProvider {

    // Mapa
    // ----
    private MapView mapView;
    private GoogleMap map;
    private Context context;
    private Marker marker = null;

    private static final int VIEW1 = 0;
    private static final int VIEW2 = 1;
    private static final int VIEW3 = 2;
    private static final int VIEW4 = 3;
    private static final int VIEW_MAX_COUNT = VIEW4 + 1;
    private final String[] names = {"Bienvenido", "Tus intereses", "Tu Zona", "Notificaciones"};

    private LayoutInflater mInflater;

    public TutorialAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int view = getItemViewType(position);
        if (convertView == null) {
            switch (view) {
                case VIEW1:
                    convertView = mInflater.inflate(R.layout.tuto_step1, null);
                    configViewStart(convertView);
                    break;
                case VIEW2:
                    convertView = mInflater.inflate(R.layout.tuto_step2, null);
                    configViewCategories(convertView);
                    break;
                case VIEW3:
                    convertView = mInflater.inflate(R.layout.tuto_step3, null);
                    configViewPosition(convertView);
                    break;
                case VIEW4:
                    convertView = mInflater.inflate(R.layout.tuto_step4, null);
                    configViewFinish(convertView);
                    break;
            }
        }

        return convertView;
    }

    private void openListEvents() {
        Intent intent = new Intent(context, ListEventsActivity.class);
        context.startActivity(intent);
    }

    private void configViewFinish(View convertView) {
        Button buttonClose = (Button) convertView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GUARDAR DATOS //
                ((Activity) context).finish();
                openListEvents();
            }
        });
    }

    private void configViewStart(View convertView) {
        Button buttonClose = (Button) convertView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                openListEvents();
            }
        });
    }

    private void configViewCategories(View convertView){
        LinearLayout layoutCategories = (LinearLayout) convertView.findViewById(R.id.layoutCategories);
        CheckBoxView checkBox = new CheckBoxView(context,null);
        layoutCategories.addView(checkBox);
    }

    private void configViewPosition(View convertView) {
        try {
            mapView = (MapView) convertView.findViewById(R.id.mapview);
            mapView.onCreate(null);
            configMap(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button buttonClose = (Button) convertView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                openListEvents();
            }
        });

    }
    // -----------------------------------------------------------------------------------------------------
    // CONFIG MAP
    // -----------------------------------------------------------------------------------------------------

    private void configMap(final Context context) {
        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
//		map.setMyLocationEnabled(true);

        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // CLICK MAP
        // ---------
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                if (marker != null) {
                    removeMarkerFromMap(marker);
                }
                marker = addMarkerToMap(point.latitude, point.longitude);
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

    public String getTitle(int position) {
        return names[position];
    }

}
