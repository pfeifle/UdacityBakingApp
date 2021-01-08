package com.example.udacitybakingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.adapter.AdapterDetailActivity4Ingredients;
import com.example.udacitybakingapp.adapter.AdapterDetailActivity4Steps;
import com.example.udacitybakingapp.recipe.CompleteRecipe;

public class DetailActivity extends AppCompatActivity {
    public CompleteRecipe completeRecipe;
    public RecyclerView rv_ingredients;
    public RecyclerView rv_steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            completeRecipe = data.getParcelable(getString(R.string.ID_CompleteRecipe));
        }

        setContentView(R.layout.activity_detail);

        rv_ingredients =findViewById(R.id.rv_ingredients);
        AdapterDetailActivity4Ingredients adapterDetailActivity4Ingredients = new AdapterDetailActivity4Ingredients(completeRecipe.ingredients);
        rv_ingredients.setAdapter(adapterDetailActivity4Ingredients);
        rv_ingredients.setNestedScrollingEnabled(false);

        rv_steps =findViewById(R.id.rv_steps);
        AdapterDetailActivity4Steps adapterDetailActivity4Steps = new AdapterDetailActivity4Steps(completeRecipe);
        rv_steps.setAdapter(adapterDetailActivity4Steps);
        rv_steps.setNestedScrollingEnabled(false);

    }

}
