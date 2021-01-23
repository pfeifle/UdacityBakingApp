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
import com.example.udacitybakingapp.adapter.AdapterDetailTabletActivity4Steps;
import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Step;
import com.google.gson.Gson;

public class DetailTabletActivity extends AppCompatActivity {
    public CompleteRecipe completeRecipe;
    public RecyclerView rv_ingredients;
    public RecyclerView rv_steps;
    AdapterDetailTabletActivity4Steps adapterDetailTabletActivity4Steps;
    public int position_in_view_holder;
    public Step step;
    public long position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();

        if (savedInstanceState != null) {
            AdapterDetailTabletActivity4Steps.videoTablet.myMediaPlayer.position = savedInstanceState.getLong("position");
            AdapterDetailTabletActivity4Steps.step =savedInstanceState.getParcelable("step");
            completeRecipe = savedInstanceState.getParcelable("completeRecipe");
            position_in_view_holder =savedInstanceState.getInt("positionInViewHolder");
            position =savedInstanceState.getLong("position");
            step =savedInstanceState.getParcelable("step");
        } else if (data != null) {
            completeRecipe = data.getParcelable(getString(R.string.ID_CompleteRecipe));
            position_in_view_holder=0;
        }


        setContentView(R.layout.activity_detail);

        rv_ingredients =findViewById(R.id.rv_ingredients);
        AdapterDetailActivity4Ingredients adapterDetailActivity4Ingredients = new AdapterDetailActivity4Ingredients(completeRecipe.ingredients);
        rv_ingredients.setAdapter(adapterDetailActivity4Ingredients);


        rv_steps =findViewById(R.id.rv_steps);
        adapterDetailTabletActivity4Steps = new AdapterDetailTabletActivity4Steps(this,completeRecipe,position_in_view_holder,position,step);
        rv_steps.setAdapter(adapterDetailTabletActivity4Steps);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.step_navigator, menu);
        getMenuInflater().inflate(R.menu.widget, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapterDetailTabletActivity4Steps.videoTablet!=null) {
            adapterDetailTabletActivity4Steps.recover(position);
            adapterDetailTabletActivity4Steps.videoTablet.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapterDetailTabletActivity4Steps.videoTablet!=null) {
            adapterDetailTabletActivity4Steps.videoTablet.onPause();
            if (adapterDetailTabletActivity4Steps.videoTablet.myMediaPlayer!=null)
                position = adapterDetailTabletActivity4Steps.videoTablet.myMediaPlayer.position;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapterDetailTabletActivity4Steps.videoTablet!=null)
        adapterDetailTabletActivity4Steps.videoTablet.onDestroy();
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("step", (AdapterDetailTabletActivity4Steps.step));
        outState.putLong("position", AdapterDetailTabletActivity4Steps.videoTablet.myMediaPlayer.position);
        outState.putParcelable("completeRecipe", completeRecipe);
        outState.putInt("positionInViewHolder", position_in_view_holder);
        outState.putLong("position", position);
        outState.putParcelable("step",adapterDetailTabletActivity4Steps.step );
        super.onSaveInstanceState(outState);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        //TODO
        if (item.getItemId()==R.id.prev_step) {
            adapterDetailTabletActivity4Steps.videoTablet.position =0;
            if (adapterDetailTabletActivity4Steps.step.id> 0)
                adapterDetailTabletActivity4Steps.step = completeRecipe.steps.get(--adapterDetailTabletActivity4Steps.step.id);
            else
                adapterDetailTabletActivity4Steps.step =completeRecipe.steps.get(completeRecipe.steps.size()-1); // we go to the end.
            Toast.makeText(this.getApplicationContext(), "Have fun carrying out the next step " + adapterDetailTabletActivity4Steps.step.shortDescription + " :-)", Toast.LENGTH_LONG).show();
            adapterDetailTabletActivity4Steps.videoTablet.description_tv.setText(adapterDetailTabletActivity4Steps.step.shortDescription);
            adapterDetailTabletActivity4Steps.videoTablet.description_detail_tv.setText(adapterDetailTabletActivity4Steps.step.description);
            adapterDetailTabletActivity4Steps.videoTablet.releasePlayer();
            onResume();
        }
        else if (item.getItemId()==R.id.next_step){
            adapterDetailTabletActivity4Steps.videoTablet.position =0;
            if (adapterDetailTabletActivity4Steps.step.id <completeRecipe.steps.size()-1)
                adapterDetailTabletActivity4Steps.step = completeRecipe.steps.get(++adapterDetailTabletActivity4Steps.step.id);
            else
                adapterDetailTabletActivity4Steps.step =completeRecipe.steps.get(0); // we start at the beginning
            Toast.makeText(getApplicationContext(), "Have fun carrying out the next step " + adapterDetailTabletActivity4Steps.step.shortDescription + " :-)", Toast.LENGTH_LONG).show();
            adapterDetailTabletActivity4Steps.videoTablet.description_tv.setText(adapterDetailTabletActivity4Steps.step.shortDescription);
            adapterDetailTabletActivity4Steps.videoTablet.description_detail_tv.setText(adapterDetailTabletActivity4Steps.step.description);
            adapterDetailTabletActivity4Steps.videoTablet.releasePlayer();
            onResume();
        } else if (item.getItemId()==R.id.widget) {
            updateWidget();
            return true;
        }
        return true;
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
