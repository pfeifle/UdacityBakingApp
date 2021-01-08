package com.example.udacitybakingapp;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

public class MediaPlayer {

    public SimpleExoPlayer exoPlayer;
    public PlayerView playerView;
    public MediaSessionCompat mediaSession;
    public Activity callingActivity;
    public String videoURL;
    public long position =0;
    boolean playingPossible =true;



    MediaPlayer (PlayerView player, String videoURL, Activity a) {
        callingActivity =a;
        playerView =player;
        this.videoURL =videoURL;
        initializePlayer(videoURL);

    }

    public void initializePlayer(String videoURL) {


        mediaSession = new MediaSessionCompat(callingActivity.getApplicationContext(), "VideoActivity");
        mediaSession.setMediaButtonReceiver(null);

        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |

                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setActive(true);

        if ((exoPlayer == null )) {

            exoPlayer = new SimpleExoPlayer.Builder(callingActivity.getApplicationContext()).build();
            playerView.setPlayer(exoPlayer);

            String user = Util.getUserAgent(callingActivity, "UdacityBakingApp");

            if (videoURL.length()!=0) {

                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL),
                        new DefaultDataSourceFactory(Objects.requireNonNull(callingActivity.getApplicationContext()), user),
                        new DefaultExtractorsFactory(), null, null);
                if (position != C.TIME_UNSET) exoPlayer.seekTo(position);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(playingPossible);
            }
        }
    }
}
