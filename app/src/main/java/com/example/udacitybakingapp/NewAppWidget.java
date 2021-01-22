package com.example.udacitybakingapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Ingredient;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    public static String KEY_PREFERENCES = "KEY_Preferences";
    public static String KEY_RECIPE = "KEY_Recipe";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,String recipeName, List<Ingredient> recipeIngredients) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.recipe_name, recipeName);
        // Instruct the widget manager to update the widget
        for (Ingredient ingredient : recipeIngredients) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            ingredientView.setTextViewText(R.id.ingredient_name_tv, ingredient.ingredient);
            ingredientView.setTextViewText(R.id.ingredient_quantity_tv,
                    ingredient.quantity +" " +ingredient.measure);
            views.addView(R.id.widget_container, ingredientView);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_id, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
        String recipeString = sharedPreferences.getString(KEY_RECIPE, null);
        CompleteRecipe recipe = new Gson().fromJson(recipeString, CompleteRecipe.class);
        if (recipe != null) {
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, recipe.name, recipe.ingredients);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}