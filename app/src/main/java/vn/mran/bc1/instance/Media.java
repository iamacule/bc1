package vn.mran.bc1.instance;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import vn.mran.bc1.R;

/**
 * Created by Mr An on 28/11/2017.
 */

public class Media {
    public static MediaPlayer mediaPlayer;
    public static MediaPlayer shakePlayer;

    private static String TAG = "Media";

    public static void playBackgroundMusic(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.bc_background_music);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.seekTo(2000);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public static void stopShortSound() {
        if (shakePlayer != null) {
            shakePlayer.release();
            shakePlayer = null;
        }
    }

    public static void playShortSound(Context c, int id) {
        stopShortSound();

        shakePlayer = MediaPlayer.create(c, id);
        shakePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopShortSound();
            }
        });

        shakePlayer.start();
    }
}
