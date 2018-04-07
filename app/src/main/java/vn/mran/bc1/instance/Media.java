package vn.mran.bc1.instance;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import vn.mran.bc1.R;

/**
 * Created by Mr An on 28/11/2017.
 */

public class Media {
    public MediaPlayer mediaPlayer;
    public MediaPlayer shakePlayer;
    private Context context;

    public Media(Context context) {
        this.context = context;
    }

    private String TAG = "Media";

    public void playBackgroundMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bc_background_music);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.seekTo(2000);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public void stopBackgroundMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public void pausePlayBackgroundMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    public void stopShortSound() {
        if (shakePlayer != null) {
            shakePlayer.release();
            shakePlayer = null;
        }
    }

    public void playShortSound(int id) {
        stopShortSound();

        shakePlayer = MediaPlayer.create(context, id);
        shakePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopShortSound();
            }
        });

        shakePlayer.start();
    }
}
