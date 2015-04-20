package com.eventerzgz.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eventerzgz.presenter.BasePresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.utils.TextViewEventer;

public class Tut1Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.tuto_step1, container, false);

        TextViewEventer textViewWelcome = (TextViewEventer)rootView.findViewById(R.id.textViewWelcome);
        TextViewEventer textViewSettings = (TextViewEventer)rootView.findViewById(R.id.textViewSettings);

        textViewWelcome.setmDuration(3000);
        textViewWelcome.setIsVisible(true);
        textViewSettings.setmDuration(3000);
        textViewSettings.setIsVisible(true);

        textViewWelcome
                .setText(getString(R.string.welcome));
        textViewWelcome.hide();
        textViewWelcome.toggle();
        textViewSettings
                .setText(getString(R.string.configure));
        textViewSettings.hide();
        textViewSettings.toggle();

        Button buttonClose = (Button) rootView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (getActivity()).finish();
                openListEvents();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void openListEvents() {
        BasePresenter.saveTutorialMade(Tut1Fragment.this.getActivity());
        Intent intent = new Intent(Tut1Fragment.this.getActivity(), ListEventsActivity.class);
        Tut1Fragment.this.getActivity().startActivity(intent);
    }
}