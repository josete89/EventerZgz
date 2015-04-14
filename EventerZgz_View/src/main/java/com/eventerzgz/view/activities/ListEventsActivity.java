package com.eventerzgz.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.listevents.ListEventsIface;
import com.eventerzgz.presenter.listevents.ListEventsPresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.adapter.ExpandableListAdapter;
import com.eventerzgz.view.application.EventerZgzApplication;
import com.eventerzgz.view.share.SocialShare;
import com.eventerzgz.view.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListEventsActivity extends ActionBarActivity implements ListEventsIface {

    //View
    //----
    private ListView listViewEvents;
    private AdapterListEvents adapterListEvents;
    private ProgressBar progressBarLoading;
    private View emptyView;
    private String startDate = null;
    private TextView textViewError;

    //Presenter
    //---------
    private final ListEventsPresenter listEventsPresenter = new ListEventsPresenter(this);

    //Data
    //----
    private boolean flagLoading = false;
    private boolean filterSearch = false;
    private boolean categorySearch = false;
    private static List<Event> listEventsToShow;
    private List<Event> allEventsList;
    private List<Event> filterEventsList;
    private List<Category> categoryList;
    private String categoryLoaded = "Tus eventos";
    private int groupLoaded;
    private String categoryLoadedPrevious;
    private int groupLoadedPrevious;

    // Menu lateral
    // -------------
    private DrawerLayout menuLateral;
    private ListView listMenuLateral;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<Population> populationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        //ActionBar
        //---------
        getSupportActionBar().setHomeButtonEnabled(true);

        //View
        //----
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);
        progressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        emptyView = findViewById(R.id.emptyView);
        textViewError = (TextView) findViewById(R.id.textViewError);

        configPaginacionListView();

        //Presenter
        //---------
        showLoading();
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("list")) {
            this.fetchedEvents((List<Event>) extras.getSerializable("list"));
        } else {
            listEventsPresenter.getEventsByUserPreferences(getBaseContext());
        }

        listEventsPresenter.getCategories();
        listEventsPresenter.getPopulation();

        // CLick Event
        // -----------
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intentDetailEvent(position);
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    //----------------------------------------------------------------------------------------------
    // SET LATERAL MENU
    //----------------------------------------------------------------------------------------------
    private void setLateralMenu() {
        menuLateral = (DrawerLayout) findViewById(R.id.menu_lateral);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        if (listAdapter == null) {

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
            expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return false;
                }
            });

            // Listview Group expanded listener
            expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                }
            });

            // Listview Group collasped listener
            expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {

                }
            });

            // Listview on child click listener
            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    groupLoaded = groupPosition;
                    switch (groupPosition) {
                        case 0:

                            switch (childPosition) {
                                case 0:

                                    categoryLoaded = "Hoy";
                                    searchToday();
                                    break;
                                case 1:
                                    categoryLoaded = "Mañana";
                                    searchTomorrow();

                                    break;
                                case 2:
                                    categoryLoaded = "Esta semana";
                                    searchWeek();
                                    break;

                                default:
                                    Log.i("EventerZgz", "No group position!");
                            }
                            break;
                        case 1:

                            if (ListEventsActivity.this.populationList != null && ListEventsActivity.this.populationList.size() > 0
                                    && ListEventsActivity.this.populationList.get(childPosition) != null) {
                                categoryLoaded = ListEventsActivity.this.populationList.get(childPosition).getsTitle();
                                searchPopulation(ListEventsActivity.this.populationList.get(childPosition).getId());

                            }
                            break;
                        case 2:

                            List<Category> categoryList = getCategoryList();

                            if (categoryList.get(childPosition) != null) {
                                categoryLoaded = categoryList.get(childPosition).getsTitle();
                                searchCategory(categoryList.get(childPosition).getId());
                            }

                            break;

                        default:
                            Log.i("EventerZgz", "No group position!");
                    }


                    return false;
                }
            });


        } else {
            listAdapter.notifyDataSetChanged();
        }
    }

    //----------------------------------------------------------------------------------------------
    // PREPARE LIST DATA
    //----------------------------------------------------------------------------------------------
    private void prepareListData() {
        if (listDataHeader == null) {
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<>();

            // Adding child data
            listDataHeader.add("¿Cuándo?");
            listDataHeader.add("¿Quién?");
            listDataHeader.add("¿Qué?");

            // Adding child data
            List<String> cuando = new ArrayList<>();
            cuando.add("Hoy");
            cuando.add("Mañana");
            cuando.add("Esta semana");

            listDataChild.put(listDataHeader.get(0), cuando);
        }


    }

    //----------------------------------------------------------------------------------------------
    // PREPARE CATEGORIES
    //----------------------------------------------------------------------------------------------
    private void prepareCategories(List<Category> categoryList) {

        List<String> que = new ArrayList<>();
        if (categoryList != null) {
            for (Category category : categoryList) {
                que.add(category.getsTitle());
            }
        }
        listDataChild.put(listDataHeader.get(2), que);
    }

    //----------------------------------------------------------------------------------------------
    // PREPARE POPULATION
    //----------------------------------------------------------------------------------------------
    private void preparePopulation(List<Population> populationList) {
        if (populationList != null) {
            List<String> quien = new ArrayList<>();

            for (Population population : populationList) {
                quien.add(population.getsTitle());
            }

            listDataChild.put(listDataHeader.get(1), quien);
        }
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
                    if (newText == null || newText.isEmpty()) {
                        filterSearch = false;
                        refreshListEvents(getAllEventsList());
                    } else {
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    showLoading();
                    listViewEvents.setVisibility(View.GONE);
                    filterSearch = true;
                    listEventsPresenter.getEventsByTitle(query);
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
            Intent mainIntent = new Intent().setClass(
                    ListEventsActivity.this, TutorialActivity.class);
            startActivity(mainIntent);
            return true;
        }
        if (android.R.id.home == id) {
            openCloseMenuLateral();
        }

        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------------------------------
    //Open CLOSE MENU LATERAL
    //------------------------------------------------------------------------------------------
    private void openCloseMenuLateral() {
        if (menuLateral.isDrawerOpen(Gravity.LEFT)) {
            menuLateral.closeDrawer(Gravity.LEFT);
        } else {
            menuLateral.openDrawer(Gravity.LEFT);
        }


    }

    //------------------------------------------------------------------------------------------
    //Métodos del presenter
    //------------------------------------------------------------------------------------------
    @Override
    public void fetchedEvents(List<Event> listEvents) {
        emptyView.setVisibility(View.GONE);
        listViewEvents.setVisibility(View.VISIBLE);
        hideLoading();
        if (filterSearch) {
            setFilterEventsList(listEvents);
        } else {
            if (listEvents.size() > 0) {
                setAllEventsList(listEvents);
            }
        }

        if (listEvents.size() > 0) {
            categoryLoadedPrevious = categoryLoaded;
            setTitleActionBar(categoryLoaded);
            refreshListEvents(listEvents);
        } else {
            filterSearch = false;
            Toast.makeText(ListEventsActivity.this, getString(R.string.no_result), Toast.LENGTH_SHORT).show();
            if (allEventsList == null) {
                emptyView.setVisibility(View.VISIBLE);
            }
            refreshListEvents(getAllEventsList());
        }


    }

    //-----------------------------------------------------------------
    // SET TITLE ACTION BAR
    //-----------------------------------------------------------------
    private void setTitleActionBar(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void fetchedEventsOrder(Map<Date, List<Event>> orderEvents) {
        //TODO: JARROYO IMPLEMENTAR
        Log.i("EventerZgz", orderEvents.toString());
    }

    @Override

    public void fetchedCategories(List<Category> listCategory) {
        emptyView.setVisibility(View.GONE);
        setCategoryList(listCategory);
        //configureMenuLateral(listCategory);
        prepareListData();
        prepareCategories(listCategory);
        setLateralMenu();
    }

    @Override
    public void fetchedPopulation(List<Population> populationList) {
        this.populationList = populationList;
        prepareListData();
        preparePopulation(populationList);
        setLateralMenu();
    }

    @Override
    public void error(String sMessage) {

        hideLoading();
        if(allEventsList!=null) {
            refreshListEvents(allEventsList);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }
        if (sMessage != null && !sMessage.isEmpty()) {
            Toast.makeText(ListEventsActivity.this, sMessage, Toast.LENGTH_SHORT).show();
        }
        
        textViewError.setText(sMessage);
    }


    //-------------------------------------------------------------------------
    // REFRESH LIST ADAPTER
    //-------------------------------------------------------------------------
    private void refreshListEvents(List<Event> listEvents) {
        if(listEventsToShow!=null) {
            listEventsToShow.clear();
        }else{
            listEventsToShow = new ArrayList<>();
        }
        if(listEvents!=null) {
            listEventsToShow.addAll(listEvents);
        }
        if (adapterListEvents == null) {
            adapterListEvents = new AdapterListEvents();
            listViewEvents.setAdapter(adapterListEvents);
        } else {
            adapterListEvents.notifyDataSetChanged();
            listViewEvents.invalidateViews();
        }
    }

    //-------------------------------------------------------------------------
    // ADAPTER LIST EVENTS
    //-------------------------------------------------------------------------
    private class AdapterListEvents extends BaseAdapter {

        @Override
        public int getCount() {
            if (listEventsToShow != null) {
                return listEventsToShow.size();
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
            LayoutInflater inflater;
            Event event = listEventsToShow.get(position);
            Boolean newDate = false;

            if (event.getdStartDate() != null) {
                if (startDate == null || !startDate.equals(event.getStartDateForPresentantion())) {
                    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    vi = inflater.inflate(R.layout.item_list_events_with_title, viewGroup, false);
                    startDate = event.getStartDateForPresentantion();
                    newDate = true;
                }

            }

            if (!newDate) {
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.item_list_events, viewGroup, false);
            }

            viewholder = new ViewHolder();
            viewholder.tvTitle = (TextView) vi.findViewById(R.id.tvTitle);
            viewholder.tvLugar = (TextView) vi.findViewById(R.id.tvLugar);
            viewholder.textViewFecha = (TextView) vi.findViewById(R.id.textViewFecha);
            viewholder.tvVerMas = (TextView) vi.findViewById(R.id.tvVerMas);
            viewholder.tvCompartir = (TextView) vi.findViewById(R.id.tvCompartir);
            viewholder.imageView = (ImageView) vi.findViewById(R.id.imageView);
            viewholder.progressBarLoading = vi.findViewById(R.id.progressBarLoading);
            viewholder.layoutImage = vi.findViewById(R.id.layoutImage);
            if (newDate) {
                viewholder.header_title_date = (TextView) vi.findViewById(R.id.header_title_date);
            }
            vi.setTag(viewholder);


            viewholder.tvTitle.setText(event.getsTitle());
            if (newDate) {
                if (event.getdStartDate() != null && event.getdEndDate() != null) {
                    if (event.getStartDateForPresentantion().equals(event.getEndDateForPresentation())) {
                        viewholder.header_title_date.setText(event.getStartDateForPresentantion());
                    } else {

                        viewholder.header_title_date.setText(event.getStartDateForPresentantion() + " - " + event.getEndDateForPresentation());
                    }
                } else {
                    viewholder.header_title_date.setText(event.getStartDateForPresentantion());
                }
            }
            try {
                viewholder.tvLugar.setText(event.getSubEvent().getWhere().getsTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (event.getdEndDate() != null || event.getdStartDate() != null) {

                String dateStringfy = "";

                if (event.getdEndDate() != null && event.getdStartDate() != null) {
                    if (event.getStartDateForPresentantion().equals(event.getEndDateForPresentation())) {
                        dateStringfy = String.format("Fecha: %s", event.getStartDateForPresentantion());

                    } else {
                        dateStringfy = String.format("De %s a %s", event.getStartDateForPresentantion(), event.getEndDateForPresentation());
                    }
                } else if (event.getdEndDate() != null) {
                    dateStringfy = String.format("Termina %s", event.getEndDateForPresentation());
                } else if (event.getdStartDate() != null) {
                    dateStringfy = String.format("Empieza %s", event.getdStartDate());
                }

                viewholder.textViewFecha.setText(dateStringfy);
            } else {

                viewholder.textViewFecha.setVisibility(View.GONE);
            }
            //Imagen
            //------
            if (event.getsImage() != null && !event.getsImage().isEmpty()) {
                viewholder.layoutImage.setVisibility(View.VISIBLE);
                viewholder.imageView.setVisibility(View.VISIBLE);
                Utils.displayImageLoading((event.getFieldWithUri(event.getsImage())), viewholder.imageView, viewholder.progressBarLoading);

            } else {
                viewholder.imageView.setVisibility(View.GONE);
                viewholder.layoutImage.setVisibility(View.GONE);
                viewholder.imageView.setImageResource(R.drawable.imagen_cabecera);
            }


            //CLICK COMPARTIR
            viewholder.tvCompartir.setTag(position);
            final String url;
            if (event.getsWeb() != null) {
                url = "Información compartida a través de #EventerZgz: " + listEventsToShow.get(position).getsWeb();
            } else {
                url = "¡¿Qué te parece este evento?!\r\n" + listEventsToShow.get(position).getsTitle() + "\r\n" + listEventsToShow.get(position).getsDescription();
            }

            viewholder.tvCompartir
                    .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int position = (Integer) v.getTag();
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
        TextView header_title_date;
        TextView tvVerMas;
        TextView tvCompartir;
        ImageView imageView;
        View progressBarLoading;
        View layoutImage;
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
                    if (!flagLoading) {

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
        Event event;
        if (filterSearch) {
            event = getFilterEventsList().get(position);
        } else {
            event = getAllEventsList().get(position);
        }
        intentEvent.putExtra(EventerZgzApplication.INTENT_EVENT_SELECTED, event);

        startActivity(intentEvent);
    }

    // --------------------------------------------------------------------------------------
    // SEARCH POPULATION
    // --------------------------------------------------------------------------------------
    public void searchPopulation(String id) {
        hideMenuAndLoad();
        categorySearch = true;
        listEventsPresenter.getEventsByPopulations(id);
    }

    // --------------------------------------------------------------------------------------
    // INTENT EVENT
    // --------------------------------------------------------------------------------------
    public void searchCategory(String id) {
        hideMenuAndLoad();
        categorySearch = true;
        listEventsPresenter.getEventsByCategories(id);
    }

    // --------------------------------------------------------------------------------------
    // SEARCH TODAY
    // --------------------------------------------------------------------------------------
    public void searchToday() {
        hideMenuAndLoad();
        categorySearch = true;
        listEventsPresenter.getEventsToday();
    }

    // --------------------------------------------------------------------------------------
    // SEARCH TOMORROW
    // --------------------------------------------------------------------------------------
    public void searchTomorrow() {
        hideMenuAndLoad();
        categorySearch = true;
        listEventsPresenter.getEventsTomorrow();
    }

    // --------------------------------------------------------------------------------------
    // SEARCH WEEK
    // --------------------------------------------------------------------------------------
    public void searchWeek() {
        hideMenuAndLoad();
        categorySearch = true;
        listEventsPresenter.getEventsWeek();
    }

    private void hideMenuAndLoad() {
        menuLateral.closeDrawers();
        showLoading();
        listViewEvents.setVisibility(View.GONE);

    }

    //SETTERS AND GETTERS
    public List<Population> getPopulationList() {
        return populationList;
    }

    public void setPopulationList(List<Population> populationList) {
        this.populationList = populationList;
    }

    public List<Event> getAllEventsList() {
        return allEventsList;
    }

    public void setAllEventsList(List<Event> allEventsList) {
        this.allEventsList = allEventsList;
    }

    public List<Event> getFilterEventsList() {
        return filterEventsList;
    }

    public void setFilterEventsList(List<Event> filterEventsList) {
        this.filterEventsList = filterEventsList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
