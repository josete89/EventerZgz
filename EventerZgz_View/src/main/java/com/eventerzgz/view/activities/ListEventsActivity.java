package com.eventerzgz.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.listevents.ListEventsIface;
import com.eventerzgz.presenter.listevents.ListEventsPresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.adapter.MenuLateralItemsAdapter;
import com.eventerzgz.view.application.EventerZgzApplication;
import com.eventerzgz.view.share.SocialShare;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ListEventsActivity extends ActionBarActivity implements ListEventsIface {

    //View
    //----
    private ListView listViewEvents;
    private AdapterListEvents adapterListEvents;
    private ProgressBar progressBarLoading;
    private View emptyView;
    private TextView textViewError;
    //Presenter
    //---------
    private final ListEventsPresenter listEventsPresenter = new ListEventsPresenter(this);

    //Data
    //----
    private boolean flagLoading = false;

    // Menu lateral
    // -------------
    private DrawerLayout menuLateral;
    private ListView listMenuLateral;
    private String[] opciones_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        //View
        //----
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);
        progressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        emptyView = findViewById(R.id.emptyView);
        textViewError = (TextView) findViewById(R.id.textViewError);

        configPaginacionListView();

        // Menu lateral
        // ------------
        //configureMenuLateral();

        //Presenter
        //---------
        showLoading();
        listEventsPresenter.getAllEvents();

        // CLick Event
        // -----------
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intentDetailEvent(position);
            }
        });
    }

    // ------------------------------------------------------------------------------------
    // CONFIGURE MENU LATERAL
    // ------------------------------------------------------------------------------------
    private void configureMenuLateral() {
        opciones_menu = getResources().getStringArray(R.array.opciones_menu);
        menuLateral = (DrawerLayout) findViewById(R.id.menu_lateral);
        listMenuLateral = (ListView) findViewById(R.id.menu_lateral_list);

        // Set the adapter for the list view
        listMenuLateral.setAdapter(new MenuLateralItemsAdapter(
                ListEventsActivity.this));
        // Set the list's click listener
        listMenuLateral.setOnItemClickListener(new DrawerItemClickListener());
    }

    //-------------------------------------------------------------------------
    //MENU
    //-------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_events, menu);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAction = (SearchView) MenuItemCompat
                .getActionView(searchMenuItem);
        if (searchViewAction != null) {
            searchViewAction.setIconifiedByDefault(true);

            // BUSQUEDA
            // --------
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText == null || newText.equals("")) {

                    } else {
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    //listEventsPresenter.
                    return true;
                }

            };
            searchViewAction.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------------------------------
    //Métodos del presenter
    //------------------------------------------------------------------------------------------
    @Override
    public void fetchedEvents(List<Event> listEvents) {
        hideLoading();
        EventerZgzApplication.eventsList = listEvents;
        refreshListEvents();
    }

    @Override

    public void fetchedCategories(List<Category> listCategory) {
        EventerZgzApplication.categoryList = listCategory;
        configureMenuLateral();
    }

    @Override
    public void error(String sMessage) {

        hideLoading();
        emptyView.setVisibility(View.VISIBLE);
        textViewError.setText(sMessage);
    }


    //-------------------------------------------------------------------------
    // REFRESH LIST ADAPTER
    //-------------------------------------------------------------------------
    private void refreshListEvents() {
        if (adapterListEvents == null) {
            adapterListEvents = new AdapterListEvents();
            listViewEvents.setAdapter(adapterListEvents);
        } else {
            adapterListEvents.notifyDataSetChanged();
            ;
        }
    }

    //-------------------------------------------------------------------------
    // ADAPTER LIST EVENTS
    //-------------------------------------------------------------------------
    private class AdapterListEvents extends BaseAdapter {

        @Override
        public int getCount() {
            if (EventerZgzApplication.eventsList != null) {
                return EventerZgzApplication.eventsList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
            ViewHolder viewholder;
            View vi = contentView;

            if (contentView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.item_list_events, viewGroup, false);

                viewholder = new ViewHolder();
                viewholder.tvTitle = (TextView) vi.findViewById(R.id.tvTitle);
                viewholder.tvLugar = (TextView) vi.findViewById(R.id.tvLugar);
                viewholder.textViewFecha = (TextView) vi
                        .findViewById(R.id.textViewFecha);
                viewholder.tvVerMas = (TextView) vi.findViewById(R.id.tvVerMas);
                viewholder.tvCompartir = (TextView) vi
                        .findViewById(R.id.tvCompartir);
                viewholder.imageView = (ImageView) vi.findViewById(R.id.imageView);

                vi.setTag(viewholder);
            }
            viewholder = (ViewHolder) vi.getTag();

            Event event = EventerZgzApplication.eventsList.get(position);
            viewholder.tvTitle.setText(event.getsTitle());
            try {
                viewholder.tvLugar.setText(event.getSubEvent().getWhere().getsTitle());
            }catch (Exception e){

            }
            if (event.getdEndDate() != null) {
                viewholder.textViewFecha.setText(event.getdEndDate().toString());
            } else {
                viewholder.textViewFecha.setVisibility(View.GONE);
            }
            //Imagen
            //------
            if (event.getsImage() != null && !event.getsImage().equals("")) {
                ImageLoader.getInstance().displayImage((event.getFieldWithUri(event.getsImage())), viewholder.imageView);
            } else {
                viewholder.imageView.setVisibility(View.GONE);
            }


            //CLICK COMPARTIR
            viewholder.tvCompartir.setTag(position);
            viewholder.tvCompartir
                    .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int position = (Integer) v.getTag();
                                                String url = "www.marca.com";
                                                SocialShare.share(ListEventsActivity.this, url);
                                            }
                                        }
                    );

            return vi;
        }
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvLugar;
        TextView textViewFecha;
        TextView tvVerMas;
        TextView tvCompartir;
        ImageView imageView;
        LinearLayout linearLayoutClip;
    }

    // --------------------------------------------------------------------------------------
    // DRAWER ITEM CLICK LISTENER
    // --------------------------------------------------------------------------------------
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position,
                                long id) {
            switch (position) {
                case 0:
                    // Ocultar menu

                    menuLateral.closeDrawers();
                    break;

                default:
                    break;
            }
        }

    }

    // --------------------------------------------------------------------------------------
    // CONFIG PAGINACION
    // --------------------------------------------------------------------------------------
    private void configPaginacionListView() {
        listViewEvents.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount
                        && totalItemCount != 0) {
                    if (flagLoading == false) {
                        flagLoading = true;
                        // TODO - ANADIR ITEMS AL LISTADO PARA CARGARLOS //
                        //adapter.notifyDataSetChanged();
                        // listView.invalidateViews();
                        //swipeLayout.setRefreshing(false);
                    }
                }
                /*int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ? 0
                        : listView.getChildAt(0).getTop();
                swipeLayout.setEnabled(topRowVerticalPosition >= 0);*/

                //Realizar peticion de otros
                //--------------------------
                /*if(loading){
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }


                boolean loadMore =  firstVisibleItem + visibleItemCount >= totalItemCount-1;
                if(!searchingList && !loading && loadMore && urlPeticionWebService != null && !isEventos){
                    new RESTfulAsyncTask().execute(urlPeticionWebService);
                    posItemActual = firstVisibleItem;
                    loading = true;
                }*/

            }
        });
    }

    private void showLoading() {
        progressBarLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBarLoading.setVisibility(View.GONE);
    }

    // --------------------------------------------------------------------------------------
    // INTENT EVENT
    // --------------------------------------------------------------------------------------
    private void intentDetailEvent(int position) {
        Intent intentEvent = new Intent(ListEventsActivity.this, DetailEventActivity.class);
        intentEvent.putExtra(EventerZgzApplication.INTENT_EVENT_SELECTED, position);
        startActivity(intentEvent);
    }
}
