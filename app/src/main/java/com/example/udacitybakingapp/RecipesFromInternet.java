package com.example.udacitybakingapp;

import com.example.udacitybakingapp.recipe.CompleteRecipe;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;


public interface RecipesFromInternet {
    @GET("/baking.json")
    void getRecipes(Callback<List<CompleteRecipe>> cb);
}
