package com.example.covidtracker.ui.map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covidtracker.R;
import com.example.covidtracker.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MapActivity extends AppCompatActivity implements InfoAdapter.ItemClickListener {
    public static ArrayList<InformationViewModel> data = new ArrayList<InformationViewModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapping);

        try {
            Object result = new InfoLoader().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_information);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        //infoAdapter.setClickListener(this);
        recyclerView.setAdapter(new InfoAdapter(this, data));
        Button button = (Button) findViewById(R.id.button_map);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapActivity.this, SettingsActivity.class));
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private class InfoLoader extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            final String DATA_URL = "https://api.covidtracking.com/v1/states/current.json";
            data = (ArrayList<InformationViewModel>) DataQuery.fetchArticleData(DATA_URL); //Sets up ArrayList with all data for all states
            return null;
        }
    }
}
