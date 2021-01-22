package com.example.udacitybakingapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.DetailActivity;
import com.example.udacitybakingapp.DetailTabletActivity;
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
        protected CompleteRecipe completeRecipe;
        public boolean isTablet;

        public ViewHolder(View view) {

            super(view);
            // Define click listener for the ViewHolder's View
            tv_name = (TextView) view.findViewById(R.id.id_main_item_name_tv);
            tv_servings = (TextView) view.findViewById(R.id.id_main_item_servings_tv);
            isTablet = view.getContext().getResources().getBoolean(R.bool.isTablet);

            view.setOnClickListener(v -> {
       //         Toast.makeText(view.getContext(),"Have fun baking " + completeRecipe.name +" :-)",Toast.LENGTH_LONG).show();
                if (!isTablet) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra(view.getContext().getString(R.string.ID_CompleteRecipe), completeRecipe);
                    view.getContext().startActivity(intent);
                }
                else {
                    Toast.makeText(view.getContext(),"Tablet",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), DetailTabletActivity.class);
                    intent.putExtra(view.getContext().getString(R.string.ID_CompleteRecipe), completeRecipe);
                    view.getContext().startActivity(intent);

                }

            });
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
        viewHolder.completeRecipe = localDataSet.get(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


