package com.example.udacitybakingapp;

import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Step;
import com.google.android.exoplayer2.ui.PlayerView;

@SuppressWarnings("deprecation")
public class VideoActivity extends AppCompatActivity {

    public CompleteRecipe completeRecipe;
    public PlayerView playerView;
    public MediaSessionCompat mediaSession;
    public MediaPlayer myMediaPlayer;
    public Step step = null;
    public  TextView description_tv;
    public TextView description_detail_tv;
    public long position=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong("position");
            step =savedInstanceState.getParcelable("step");
            completeRecipe = savedInstanceState.getParcelable("completeRecipe");
        } else if (data != null) {
            step = data.getParcelable(getString(R.string.ID_Step));
            completeRecipe = data.getParcelable(getString(R.string.ID_CompleteRecipe));
        }

        setContentView(R.layout.activity_video_phone);


        description_tv = findViewById(R.id.step_description_tv);
        if (step != null)
            description_tv.setText(step.shortDescription);

        description_detail_tv = findViewById(R.id.step_description_detail_tv);
        if (step != null)
            description_detail_tv.setText(step.description);

        playerView = findViewById(R.id.videoPlayerView);

        myMediaPlayer = new MediaPlayer (playerView, step.videoURL, this,position);

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
        if (step.videoURL.length() > 0) {
            findViewById(R.id.video_iv).setVisibility(View.INVISIBLE);
            findViewById(R.id.videoPlayerView).setVisibility(View.VISIBLE);
            myMediaPlayer.initializePlayer(step.videoURL);
            myMediaPlayer.exoPlayer.seekTo(position);
        } else {
            findViewById(R.id.video_iv).setVisibility(View.VISIBLE);
            findViewById(R.id.videoPlayerView).setVisibility(View.INVISIBLE);
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        if (myMediaPlayer.exoPlayer != null) {
            myMediaPlayer.playingPossible = myMediaPlayer.exoPlayer.getPlayWhenReady();
            myMediaPlayer.position = myMediaPlayer.exoPlayer.getCurrentPosition();
            myMediaPlayer.exoPlayer.stop();
            myMediaPlayer.exoPlayer.release();
            position = myMediaPlayer.exoPlayer.getCurrentPosition();
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("step", step);
        outState.putLong("position", myMediaPlayer.position);
        outState.putParcelable("completeRecipe", completeRecipe);
        super.onSaveInstanceState(outState);
    }

}
