package com.eventerzgz.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Tut4Fragment extends Fragment implements TutorialIface {

    private View viewPopulation;
    private View emptyViewPopulation;
    private View loading;
    private CheckBox[] listCheckboxPob;
    ArrayList<String> arrayIdsCategoriesPob = new ArrayList<>();


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

               //if(listCheckboxCat!=null) {
               //    // CATEGORIAS //
               //    for (int i = 0; i < listCheckboxCat.length; i++) {
               //        if (listCheckboxCat[i].isChecked()) {
               //            arrayIdsCategories.add("" + listCheckboxCat[i].getId());
               //        }
               //    }
               //}
                // POBLACION //
                if(listCheckboxPob!=null) {
                    for (int i = 0; i < listCheckboxPob.length; i++) {
                        if (listCheckboxPob[i].isChecked()) {
                            arrayIdsCategoriesPob.add("" + listCheckboxPob[i].getId());
                        }
                    }
                }
                //BasePresenter.saveCategoriesSelectedInPreferences(arrayIdsCategories, context);
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
        //Guardamos en memoria
        //--------------------
        EventerZgzApplication.populationList = populationList;

        View loadingView = viewPopulation.findViewById(R.id.progressBarLoadingTut4);
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        emptyViewPopulation = viewPopulation.findViewById(R.id.emptyView);

        LinearLayout layoutCategoriesPush = (LinearLayout) viewPopulation.findViewById(R.id.layoutCategoriesPush);

        listCheckboxPob = new CheckBox[populationList.size()];

        List<String> listaPreferences = BasePresenter.getPoblation(Tut4Fragment.this.getActivity());

        for (int i = 0; i < populationList.size(); i++) {

            listCheckboxPob[i] = new CheckBox(Tut4Fragment.this.getActivity());

            listCheckboxPob[i].setId(Integer.parseInt(populationList.get(i).getId()));

            listCheckboxPob[i].setText(populationList.get(i).getsTitle());
            if (listaPreferences.contains(populationList.get(i).getId())) {
                listCheckboxPob[i].setChecked(true);
            }
            layoutCategoriesPush.addView(listCheckboxPob[i]);
        }

        if (populationList == null || populationList.size() == 0) {
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
}