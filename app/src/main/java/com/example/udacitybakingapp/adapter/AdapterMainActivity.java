package com.example.udacitybakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.R;
import com.example.udacitybakingapp.recipe.CompleteRecipe;

import java.util.List;

public  class AdapterMainActivity extends RecyclerView.Adapter<AdapterMainActivity.ViewHolder> {

    private List<CompleteRecipe> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_name;
        private final TextView tv_servings;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tv_name = (TextView) view.findViewById(R.id.id_main_item_name_tv);
            tv_servings = (TextView) view.findViewById(R.id.id_main_item_servings_tv);
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public AdapterMainActivity(List<CompleteRecipe> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_main_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tv_name.setText(localDataSet.get(position).name);
        viewHolder.tv_servings.setText("Servings " + localDataSet.get(position).servings);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


