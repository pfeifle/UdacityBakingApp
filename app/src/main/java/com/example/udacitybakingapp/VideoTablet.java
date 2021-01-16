package com.example.udacitybakingapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Step;
import com.google.android.exoplayer2.ui.PlayerView;

@SuppressWarnings("deprecation")
public class VideoTabletActivity {

    public CompleteRecipe completeRecipe;
    public PlayerView playerView;
    public MediaSessionCompat mediaSession;
    public MediaPlayer myMediaPlayer;
    public Step step = null;
    public  TextView description_tv;
    public TextView description_detail_tv;


    @Override
    VideoTabletActivity(Context context) {

        Activity currentActivity = (context.getApplicationContext()).getCurrentActivity();

        description_tv = context.findViewById(R.id.step_description_tv);
        if (step != null)
            description_tv.setText(step.shortDescription);

        description_detail_tv = findViewById(R.id.step_description_detail_tv);
        if (step != null)
            description_detail_tv.setText(step.description);

        playerView = findViewById(R.id.videoPlayerView);

        myMediaPlayer = new MediaPlayer (playerView, step.videoURL, this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.step_navigator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        //TODO
        if (item.getItemId()==R.id.prev_step) {
            if (step.id> 0)
                step = completeRecipe.steps.get(--step.id);
            else
                step =completeRecipe.steps.get(completeRecipe.steps.size()-1); // we go to the end.
            Toast.makeText(this.getApplicationContext(), "Have fun carrying out the next step " + step.shortDescription + " :-)", Toast.LENGTH_LONG).show();
            description_tv.setText(step.shortDescription);
            description_detail_tv.setText(step.description);
            releasePlayer();
            onResume();
        }
        else {
            if (step.id <completeRecipe.steps.size()-1)
                step = completeRecipe.steps.get(++step.id);
            else
                step =completeRecipe.steps.get(0); // we start at the beginning
            Toast.makeText(this.getApplicationContext(), "Have fun carrying out the next step " + step.shortDescription + " :-)", Toast.LENGTH_LONG).show();
            description_tv.setText(step.shortDescription);
            description_detail_tv.setText(step.description);
            releasePlayer();
            onResume();
        }
        return true;


    }

    @Override
    public void onResume() {
        super.onResume();
        myMediaPlayer.initializePlayer(step.videoURL);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (myMediaPlayer.exoPlayer != null) {
            myMediaPlayer.playingPossible = myMediaPlayer.exoPlayer.getPlayWhenReady();
            myMediaPlayer.position = myMediaPlayer.exoPlayer.getCurrentPosition();
            myMediaPlayer.exoPlayer.stop();
            myMediaPlayer.exoPlayer.release();
            myMediaPlayer.exoPlayer = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    private void releasePlayer() {
        if (myMediaPlayer.exoPlayer != null) {
            myMediaPlayer.exoPlayer.stop();
            myMediaPlayer.exoPlayer.release();
            myMediaPlayer.exoPlayer = null;
        }
    }


}
