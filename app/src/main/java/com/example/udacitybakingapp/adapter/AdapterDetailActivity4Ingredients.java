package com.example.udacitybakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.R;
import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Ingredient;

import java.util.List;

public  class AdapterDetailActivity4Ingredients extends RecyclerView.Adapter<AdapterDetailActivity4Ingredients.ViewHolder> {

    private List<Ingredient> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_ingredient_name;
        private final TextView tv_ingredient_quantity;

        protected CompleteRecipe completeRecipe;

        public ViewHolder(View view) {
            super(view);

            tv_ingredient_name = (TextView) view.findViewById(R.id.ingredient_name_tv);
            tv_ingredient_quantity = (TextView) view.findViewById(R.id.ingredient_quantity_tv);
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public AdapterDetailActivity4Ingredients(List<Ingredient> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_detail_ingredient_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tv_ingredient_name.setText(localDataSet.get(position).ingredient);
        viewHolder.tv_ingredient_quantity.setText(localDataSet.get(position).measure + " " +localDataSet.get(position).quantity);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


