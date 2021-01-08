package com.example.udacitybakingapp.recipe;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {


    public String ingredient;
    public double quantity;
    public String measure;




    protected Ingredient(Parcel in) {
        this.quantity = in.readDouble();
        this.measure =in.readString();
        this.ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };


}