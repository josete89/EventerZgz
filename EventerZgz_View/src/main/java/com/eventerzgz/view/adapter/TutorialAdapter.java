package com.eventerzgz.view.adapter;

import org.taptwo.android.widget.TitleProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.presenter.tutorial.TutorialIface;
import com.eventerzgz.presenter.tutorial.TutorialPresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.activities.ListEventsActivity;
import com.eventerzgz.view.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TutorialAdapter extends BaseAdapter implements TitleProvider, TutorialIface {

    // Mapa
    // ----
    public static MapView mapView;
    private GoogleMap map;
    private Context context;
    private Marker marker = null;
    private View viewCategories;
    private View viewCategoriesPush;
    private CheckBox[] listCheckbox;
    private CheckBox[] listCheckboxPush;
    ArrayList<String> arrayIdsCategories = new ArrayList<String>();
    ArrayList<String> arrayIdsCategoriesPush = new ArrayList<String>();

    private static final int VIEW1 = 0;
    private static final int VIEW2 = 1;
    private static final int VIEW3 = 2;
    private static final int VIEW4 = 3;
    private static final int VIEW_MAX_COUNT = VIEW4 + 1;
    private final String[] names = {"Bienvenido", "Tus intereses", "Tu Zona", "Notificaciones"};

    private LayoutInflater mInflater;
    private TutorialPresenter presenter = new TutorialPresenter(this);

    public TutorialAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        presenter.getCategories();
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
                    viewCategories = convertView;
                    configViewStart(convertView);
                    break;
                case VIEW3:
                    convertView = mInflater.inflate(R.layout.tuto_step3, null);
                    configViewPosition(convertView);
                    Log.e("TAG", "1");
                    break;
                case VIEW4:
                    convertView = mInflater.inflate(R.layout.tuto_step4, null);
                    viewCategoriesPush = convertView;
                    configViewFinish(convertView);
                    Log.e("TAG", "2");
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
        Button buttonClose = (Button) convertView.findViewById(R.id.buttonFinish);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GUARDAR DATOS //
                for (int i = 0; i < names.length; i++) {
                    final int j = i;
                    if (listCheckbox[i].isChecked()) {
                        arrayIdsCategories.add("" + listCheckbox[j].getId());
                    }
                    if (listCheckboxPush[i].isChecked()) {
                        arrayIdsCategoriesPush.add("" + listCheckboxPush[j].getId());
                    }
                }

                Utils.saveCategoriesSelectedInPreferences(arrayIdsCategories, context);
                Utils.saveCategoriesSelectedInPreferences(arrayIdsCategoriesPush, context);

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

    @Override
    public void fechedCategories(List<Category> categoryList) {
        Log.e("TAG", "3: " + categoryList.size());
        LinearLayout layoutCategories = (LinearLayout) viewCategories.findViewById(R.id.layoutCategories);
        LinearLayout layoutCategoriesPush = (LinearLayout) viewCategoriesPush.findViewById(R.id.layoutCategoriesPush);

        listCheckbox = new CheckBox[categoryList.size()];
        listCheckboxPush = new CheckBox[categoryList.size()];

        for (int i = 0; i < categoryList.size(); i++) {

            listCheckbox[i] = new CheckBox(context);
            listCheckboxPush[i] = new CheckBox(context);

            listCheckbox[i].setId(categoryList.get(i).getId());
            listCheckboxPush[i].setId(categoryList.get(i).getId());

            listCheckbox[i].setText(categoryList.get(i).getsTitle());
            listCheckboxPush[i].setText(categoryList.get(i).getsTitle());

            layoutCategories.addView(listCheckbox[i]);
            layoutCategoriesPush.addView(listCheckboxPush[i]);
        }
    }

    @Override
    public void error(String sMessage) {

    }
}
