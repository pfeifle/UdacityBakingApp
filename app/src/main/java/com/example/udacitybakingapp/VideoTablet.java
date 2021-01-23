package com.example.udacitybakingapp;

import android.app.Activity;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.TextView;

import com.example.udacitybakingapp.adapter.AdapterDetailTabletActivity4Steps;
import com.example.udacitybakingapp.recipe.CompleteRecipe;
import com.example.udacitybakingapp.recipe.Step;
import com.google.android.exoplayer2.ui.PlayerView;

@SuppressWarnings("deprecation")
public class VideoTablet {

    public CompleteRecipe completeRecipe;
    public PlayerView playerView;
    public MediaSessionCompat mediaSession;
    public MediaPlayer myMediaPlayer;

    public  TextView description_tv;
    public TextView description_detail_tv;
    public Activity activity;


    public VideoTablet(Activity activity,Step step) {
        this.activity =activity;
        description_tv = activity.findViewById(R.id.step_description_tv);

        if (step != null)
            description_tv.setText(step.shortDescription);

        description_detail_tv = activity.findViewById(R.id.step_description_detail_tv);
        if (step != null)
            description_detail_tv.setText(step.description);
        playerView = activity.findViewById(R.id.videoPlayerView);
        if (step !=null)
            myMediaPlayer = new MediaPlayer (playerView, step.videoURL, activity);

    }




    public void onResume() {
        if (AdapterDetailTabletActivity4Steps.step!=null)
            myMediaPlayer.initializePlayer(AdapterDetailTabletActivity4Steps.step.videoURL);

        if (AdapterDetailTabletActivity4Steps.step.videoURL.length() > 0) {
            activity.findViewById(R.id.video_iv).setVisibility(View.INVISIBLE);
            activity.findViewById(R.id.videoPlayerView).setVisibility(View.VISIBLE);
            myMediaPlayer.initializePlayer(AdapterDetailTabletActivity4Steps.step.videoURL);
        } else {
            activity.findViewById(R.id.video_iv).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.videoPlayerView).setVisibility(View.INVISIBLE);
        }


    }

    public void onPause() {
        if (myMediaPlayer !=null)
        if (myMediaPlayer.exoPlayer != null) {
            myMediaPlayer.playingPossible = myMediaPlayer.exoPlayer.getPlayWhenReady();
            myMediaPlayer.position = myMediaPlayer.exoPlayer.getCurrentPosition();
            myMediaPlayer.exoPlayer.stop();
            myMediaPlayer.exoPlayer.release();
            myMediaPlayer.exoPlayer = null;
        }
    }

    public void onDestroy() {

        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    public void releasePlayer() {
        if (myMediaPlayer.exoPlayer != null) {
            myMediaPlayer.exoPlayer.stop();
            myMediaPlayer.exoPlayer.release();
            myMediaPlayer.exoPlayer = null;
        }
    }


}
