package com.example.udacitybakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.adapter.AdapterDetailActivity4Ingredients;
import com.example.udacitybakingapp.adapter.AdapterDetailActivity4Steps;
import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.google.gson.Gson;

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


        rv_steps =findViewById(R.id.rv_steps);
        AdapterDetailActivity4Steps adapterDetailActivity4Steps = new AdapterDetailActivity4Steps(this,completeRecipe);
        rv_steps.setAdapter(adapterDetailActivity4Steps);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.widget, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (item.getItemId()==R.id.widget) {
            updateWidget();
            return true;
        } else
            return super.onOptionsItemSelected(item);

    }
    private void updateWidget() {
        SharedPreferences sharedPreferences = getSharedPreferences(NewAppWidget.KEY_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(NewAppWidget.KEY_RECIPE, new Gson().toJson(completeRecipe)).apply();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, NewAppWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        Intent intent = new Intent(this, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        this.sendBroadcast(intent);
        Toast.makeText(this, "Added " + completeRecipe.name+ " to Widget.", Toast.LENGTH_SHORT).show();
    }

}
