package com.eventerzgz.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Set;

public class Tut4Fragment extends Fragment implements TutorialIface {

    private View viewPopulation;
    private View emptyViewPopulation;
    private View loading;
    private List<CheckBox> listCheckboxPob;
    private List<CheckBox> listCheckboxCat;

    private final TutorialPresenter presenter = new TutorialPresenter(this);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.tuto_step4, container, false);

        viewPopulation = rootView;
        loading = rootView.findViewById(R.id.progressBarLoadingTut4);
        loading.setVisibility(View.VISIBLE);
        emptyViewPopulation = rootView.findViewById(R.id.emptyView);

        presenter.getPopulation();


        Button buttonClose = (Button) rootView.findViewById(R.id.buttonFinish);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // GUARDAR DATOS //
                ArrayList<String> arrayIdsCategoriesPob = new ArrayList<>();
                ArrayList<String> arrayIdsCategories = new ArrayList<>();

                if(listCheckboxCat!=null) {
                   // CATEGORIAS //
                    for (CheckBox aListCheckboxCat : listCheckboxCat) {
                        if (aListCheckboxCat.isChecked()) {
                            arrayIdsCategories.add("" + aListCheckboxCat.getId());
                        }
                    }
               }
                // POBLACION //
                if(listCheckboxPob!=null) {
                    for (CheckBox aListCheckboxPob : listCheckboxPob) {
                        if (aListCheckboxPob.isChecked()) {
                            arrayIdsCategoriesPob.add("" + aListCheckboxPob.getId());
                        }
                    }
                }
                BasePresenter.saveCategoriesSelectedInPreferences(arrayIdsCategories, Tut4Fragment.this.getActivity());
                BasePresenter.savePoblationSelectedInPreferences(arrayIdsCategoriesPob, Tut4Fragment.this.getActivity());

                (Tut4Fragment.this.getActivity()).finish();
                openListEvents();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void fechedCategories(List<Category> categoryList) {

    }

    @Override
    public void fechedPopulation(List<Population> populationList) {

        Log.i("EventerZgz","Population list size -> "+populationList.size());

        View loadingView = viewPopulation.findViewById(R.id.progressBarLoadingTut4);
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        emptyViewPopulation = viewPopulation.findViewById(R.id.emptyView);

        LinearLayout layoutCategoriesPush = (LinearLayout) viewPopulation.findViewById(R.id.layoutCategoriesPush);

        listCheckboxPob = new ArrayList<>(populationList.size());

        Set<String> listaPreferences = BasePresenter.getPoblationInSet(Tut4Fragment.this.getActivity());

        for (Population aPopulationList : populationList) {

            CheckBox checkBox = new CheckBox(Tut4Fragment.this.getActivity());

            checkBox.setId(Integer.parseInt(aPopulationList.getId()));
            checkBox.setText(aPopulationList.getsTitle());

            if (listaPreferences.contains(aPopulationList.getId())) {
                checkBox.setChecked(true);
            }
            layoutCategoriesPush.addView(checkBox);

            listCheckboxPob.add(checkBox);
        }

        if ( populationList.size() == 0 ) {
            emptyViewPopulation.setVisibility(View.VISIBLE);
        } else {
            emptyViewPopulation.setVisibility(View.GONE);
        }
    }

    @Override
    public void error(String sMessage) {
        loading.setVisibility(View.GONE);
        emptyViewPopulation.setVisibility(View.VISIBLE);
    }

    private void openListEvents() {
        BasePresenter.saveTutorialMade(Tut4Fragment.this.getActivity());
        Intent intent = new Intent(Tut4Fragment.this.getActivity(), ListEventsActivity.class);
        Tut4Fragment.this.getActivity().startActivity(intent);
    }

    public void setListCheckboxCat(List<CheckBox> listCheckboxCat) {
        this.listCheckboxCat = listCheckboxCat;
    }
}