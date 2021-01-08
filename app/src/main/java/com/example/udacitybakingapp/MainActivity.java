package com.example.udacitybakingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.adapter.AdapterMainActivity;
import com.example.udacitybakingapp.recipe.CompleteRecipe;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_mainActivity;
    // cf. https://developer.android.com/guide/topics/ui/layout/recyclerview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_mainActivity =findViewById(R.id.rv_mainActivity);

//        Context context = rv_mainActivity.getContext();

        getAllRecipesFromInternet();
    }

    private void getAllRecipesFromInternet() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RecipesFromInternet service = restAdapter.create(RecipesFromInternet.class);

        service.getRecipes(new Callback<List<CompleteRecipe>>() {
            @Override
            public void success(List<CompleteRecipe> allRecipes, Response response) {
                AdapterMainActivity adapterMainActivity = new AdapterMainActivity(allRecipes);
                rv_mainActivity.setAdapter(adapterMainActivity);
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

}