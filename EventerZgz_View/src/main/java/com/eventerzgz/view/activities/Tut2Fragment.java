package com.eventerzgz.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.presenter.BasePresenter;
import com.eventerzgz.presenter.tutorial.TutorialIface;
import com.eventerzgz.presenter.tutorial.TutorialPresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.application.EventerZgzApplication;

import java.util.ArrayList;
import java.util.List;

public class Tut2Fragment extends Fragment implements TutorialIface {
    private View viewCategories;
    private View emptyViewCategories;
    private View loading;
    private List<CheckBox> listCheckboxCat = new ArrayList<>(0);


    private final TutorialPresenter presenter = new TutorialPresenter(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.tuto_step2, container, false);
        viewCategories = rootView;
        loading = rootView.findViewById(R.id.progressBarLoadingTut2);
        loading.setVisibility(View.VISIBLE);
        emptyViewCategories = rootView.findViewById(R.id.emptyView);
        Button buttonClose = (Button) rootView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (getActivity()).finish();
                openListEvents();
            }
        });

        presenter.getCategories();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        TutorialActivity vp = (TutorialActivity) getActivity();
        vp.setCategoriesSelected(listCheckboxCat);
    }



    @Override
    public void fechedCategories(List<Category> categoryList) {
        //Guardamos en memoria
        //--------------------
        //EventerZgzApplication.categoryList = categoryList;


        Log.i("TAG", "Category list size: " + categoryList.size());
        View loadingView = viewCategories.findViewById(R.id.progressBarLoadingTut2);
        if(loadingView!=null) {
            loadingView.setVisibility(View.GONE);
        }
        emptyViewCategories = viewCategories.findViewById(R.id.emptyView);
        LinearLayout layoutCategories = (LinearLayout) viewCategories.findViewById(R.id.layoutCategories);


        List<String> listaPreferences = BasePresenter.getCategories(Tut2Fragment.this.getActivity());

        for (Category aCategoryList : categoryList) {

            CheckBox checkBox = new CheckBox(Tut2Fragment.this.getActivity());

            checkBox.setId(Integer.parseInt(aCategoryList.getId()));

            checkBox.setText(aCategoryList.getsTitle());

            if (listaPreferences.contains(aCategoryList.getId())) {
                checkBox.setChecked(true);
            }

            listCheckboxCat.add(checkBox);

            layoutCategories.addView(checkBox);
        }

        if(categoryList == null || categoryList.size()==0){
            emptyViewCategories.setVisibility(View.VISIBLE);
        }else{
            emptyViewCategories.setVisibility(View.GONE);
        }

    }

    @Override
    public void fechedPopulation(List<Population> populationList) {

    }

    @Override
    public void error(String sMessage) {
        loading.setVisibility(View.GONE);
        emptyViewCategories.setVisibility(View.VISIBLE);
    }

    private void openListEvents() {
        BasePresenter.saveTutorialMade(getActivity());
        Intent intent = new Intent(getActivity(), ListEventsActivity.class);
        getActivity().startActivity(intent);
    }
}