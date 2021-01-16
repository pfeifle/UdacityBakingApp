package com.example.udacitybakingapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.R;
import com.example.udacitybakingapp.VideoActivity;
import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Step;

import java.util.List;

public  class AdapterDetailActivity4Steps extends RecyclerView.Adapter<AdapterDetailActivity4Steps.ViewHolder> {

    public  List<Step> localDataSet;
    public static CompleteRecipe completeRecipe;
    public static boolean isTablet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_step_name;
        private Step step;

        public ViewHolder(View view) {
            super(view);
            tv_step_name = (TextView) view.findViewById(R.id.step_name_tv);
            view.setOnClickListener(v -> {
                Toast.makeText(view.getContext(),"Have fun carrying out step " + step.shortDescription +" :-)",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(view.getContext(), VideoActivity.class);
                intent.putExtra(view.getContext().getString(R.string.ID_Step), step);
                intent.putExtra(view.getContext().getString(R.string.ID_CompleteRecipe), completeRecipe); // passed to enable forward and backward moving between steps

                view.getContext().startActivity(intent);
            });
            isTablet = DecideTablet(view);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public AdapterDetailActivity4Steps(Activity a, CompleteRecipe completeRecipe) {
        localDataSet = completeRecipe.steps;
        this.completeRecipe =completeRecipe;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_detail_step_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tv_step_name.setText(localDataSet.get(position).shortDescription);

        viewHolder.step = localDataSet.get(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Detects whether device is phone or table. returns true if tablet.
     */
    public static boolean DecideTablet(View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        view.getContext().getResources().getDisplayMetrics();
        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            return true;
        }else{
            return false;
        }
    }
}


