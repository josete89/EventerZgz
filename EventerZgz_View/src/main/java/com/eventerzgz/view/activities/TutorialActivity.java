package com.eventerzgz.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eventerzgz.view.R;
import com.eventerzgz.view.adapter.DiffAdapter;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

/**
 * Created by jesus_000 on 21/03/2015.
 */
public class TutorialActivity extends Activity {

    private ViewFlow viewFlow;
    private ListView listView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_layout);

        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        DiffAdapter adapter = new DiffAdapter(this);
        viewFlow.setAdapter(adapter);
        TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
        indicator.setTitleProvider(adapter);
        viewFlow.setFlowIndicator(indicator);

        /** To populate ListView in diff_view1.xml */
        listView = (ListView) findViewById(R.id.listView1);
        String[] names = new String[] { "Teatro", "Conferencias", "Charlas", "Cursos",
                "Concursos", "Fiestas", "Infantiles" };
        // Create an ArrayAdapter, that will actually make the Strings above
        // appear in the ListView
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names));

    }
}
