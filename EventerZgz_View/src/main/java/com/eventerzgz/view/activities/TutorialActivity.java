package com.eventerzgz.view.activities;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.widget.CheckBox;
import com.eventerzgz.view.R;
import com.eventerzgz.view.adapter.TutorialAdapter;


/**
 * Created by jesus_000 on 21/03/2015.
 */
public class TutorialActivity extends ActionBarActivity implements
        ActionBar.TabListener{


    // View
    // ----
    private ViewPager pagerTutorial;
    private ActionBar actionBar;
    private PagerAdapter pagerTutorialAdapter;

    private List<CheckBox> categoriesSelected;
    private String[] tabs = {"Bienvenido", "Tus intereses", "Tu Zona", "Perfil"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);


        // View
        // ----
        actionBar = getSupportActionBar();
        configActionBar();
        configViewPager();
    }

    // ----------------------------------------------------------------------
    // CONFIG ACTION BAR
    // ----------------------------------------------------------------------
    private void configActionBar() {

        actionBar = getSupportActionBar();

        actionBar.setCustomView(findViewById(R.id.action_bar));
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);

        // Add tabs to actionBar
        // ----------------------
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
    }

    // ----------------------------------------------------------------------
    // CONFIG VIEW PAGER
    // ----------------------------------------------------------------------
    private void configViewPager() {
        pagerTutorial = (ViewPager) findViewById(R.id.pagerTutorial);
        pagerTutorialAdapter = new ScreenSlidePagerAdapter(
                getSupportFragmentManager());
        pagerTutorial.setAdapter(pagerTutorialAdapter);

        // On page listener
        // ----------------
        pagerTutorial
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        // on changing the page
                        // make respected tab selected
                        actionBar.setSelectedNavigationItem(position);
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {
                    }
                });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (pagerTutorial != null) {
            pagerTutorial.setCurrentItem(tab.getPosition());
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    // ----------------------------------------------------------------------
    // SCREEN SLIDE PAGER ADAPTER
    // ----------------------------------------------------------------------
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 3:
                    Tut4Fragment tut4Fragment = new Tut4Fragment();
                    tut4Fragment.setListCheckboxCat(getCategoriesSelected());
                    return tut4Fragment;
                case 2:
                    return new Tut3Fragment();
                case 1:
                    return new Tut2Fragment();
                case 0:
                    return new Tut1Fragment();
                default:
                    return new Tut1Fragment();
            }

        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (TutorialAdapter.mapView != null) {
            TutorialAdapter.mapView.onLowMemory();
        }
    }

    @Override
    public void onResume() {
        if (TutorialAdapter.mapView != null) {
            TutorialAdapter.mapView.onResume();
        }
        super.onResume();
    }


    //GETTERS AND SETTERS

    public List<CheckBox> getCategoriesSelected() {
        return categoriesSelected;
    }

    public void setCategoriesSelected(List<CheckBox> categoriesSelected) {
        this.categoriesSelected = categoriesSelected;
    }
}
