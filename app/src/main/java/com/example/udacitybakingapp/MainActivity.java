package com.example.udacitybakingapp;

import android.os.Bundle;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.adapter.AdapterMainActivity;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_mainActivity;
    // cf. https://developer.android.com/guide/topics/ui/layout/recyclerview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_mainActivity =findViewById(R.id.rv_mainActivity);

        Context context = rv_mainActivity.getContext();
        String test[] = {"Rezept 1","Rezept 2","Rezept 3","Rezept 4","Rezept 5"};
        AdapterMainActivity adapterMainActivity = new AdapterMainActivity(test);
        rv_mainActivity.setAdapter(adapterMainActivity);



    }
}