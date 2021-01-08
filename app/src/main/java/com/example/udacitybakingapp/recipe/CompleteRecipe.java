package com.example.udacitybakingapp.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CompleteRecipe implements Parcelable {

    public int id;
    public String name;
    public int servings;
    public List<Ingredient> ingredients;
    public List<Step> steps;


    protected CompleteRecipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.servings = in.readInt();
        this.ingredients = new ArrayList<>();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        this.steps = new ArrayList<>();
        in.readTypedList(steps, Step.CREATOR);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CompleteRecipe> CREATOR = new Creator<CompleteRecipe>() {
        @Override
        public CompleteRecipe createFromParcel(Parcel in) {
            return new CompleteRecipe(in);
        }

        @Override
        public CompleteRecipe[] newArray(int size) {
            return new CompleteRecipe[size];
        }
    };


}
