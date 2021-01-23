package com.example.udacitybakingapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.udacitybakingapp.R;
import com.example.udacitybakingapp.VideoTablet;
import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Step;

import java.util.List;

public  class AdapterDetailTabletActivity4Steps extends RecyclerView.Adapter<AdapterDetailTabletActivity4Steps.ViewHolder> {

    public  List<Step> localDataSet;
    public static CompleteRecipe completeRecipe;
    public static boolean isTablet;
    public static VideoTablet videoTablet;
    public Activity activity;
    public static TextView tv_step_name;
    public static Step step;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Step step;

        public ViewHolder(View view,Activity activity) {
            super(view);
            tv_step_name = (TextView) view.findViewById(R.id.step_name_tv);
            view.setOnClickListener(v -> {
                Toast.makeText(view.getContext(),"Have fun carrying out step " + step.shortDescription +" :-)",Toast.LENGTH_LONG).show();
                AdapterDetailTabletActivity4Steps.step=step;
                videoTablet = new VideoTablet(activity, step);
                videoTablet.description_detail_tv.setText(step.description);
                videoTablet.description_tv.setText(step.shortDescription);
                AdapterDetailTabletActivity4Steps.step =step;
                videoTablet.completeRecipe =completeRecipe;
                videoTablet.onResume();


            });
        }
    }


    public AdapterDetailTabletActivity4Steps(Activity a, CompleteRecipe completeRecipe, Step step) {
        localDataSet = completeRecipe.steps;
        this.completeRecipe =completeRecipe;
        this.activity =a;
        this.step =step;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_detail_step_item, viewGroup, false);
        return new ViewHolder(view,activity);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        tv_step_name.setText(localDataSet.get(position).shortDescription);
        //videoTablet.description_detail_tv.setText(localDataSet.get(position).description);
        //videoTablet.description_tv.setText(localDataSet.get(position).shortDescription);
        viewHolder.step = localDataSet.get(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }





}


