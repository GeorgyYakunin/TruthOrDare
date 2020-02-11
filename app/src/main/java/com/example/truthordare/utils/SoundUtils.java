package com.example.truthordare.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.util.Log;
import com.example.truthordare.R;

public class SoundUtils {
    public static String TAG = SoundUtils.class.getSimpleName();
    public static BackgroundSound backgroundSound = null;
    public static MediaPlayer mp;

    /* renamed from: com.luckystudio.truthordare.utils.SoundUtils$2 */
    static class C05032 implements OnCompletionListener {
        C05032() {
        }

        public void onCompletion(MediaPlayer mp) {
            SoundUtils.clear();
        }
    }

    /* renamed from: com.luckystudio.truthordare.utils.SoundUtils$3 */
    static class C05043 implements OnErrorListener {
        C05043() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (what == 100 || what == -19) {
                Log.e(SoundUtils.TAG, "Error   >   " + what);
                SoundUtils.clear();
            }
            return false;
        }
    }

    public static class BackgroundSound extends AsyncTask<Void, Void, Void> {
        private MediaPlayer mediaPlayer;
        private int sound;

        public BackgroundSound(MediaPlayer mediaPlayer, int sound) {
            this.mediaPlayer = mediaPlayer;
            this.sound = sound;
        }

        protected Void doInBackground(Void... params) {
            try {
                this.mediaPlayer.setVolume((float) this.sound, (float) this.sound);
                this.mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static void musicPlayerInit(final int sound) {
        if (mp != null) {
            mp.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    if (SoundUtils.backgroundSound != null) {
                        SoundUtils.backgroundSound.cancel(true);
                    }
                    SoundUtils.backgroundSound = new BackgroundSound(mp, sound);
                    SoundUtils.backgroundSound.execute(new Void[0]);
                }
            });
            mp.setOnCompletionListener(new C05032());
            mp.setOnErrorListener(new C05043());
        }
    }

    public static void buttonOnClick(Context context) {
        play(context, R.raw.button_sound);
    }

    public static void buttonForfeitOnClick(Context context) {
        play(context, R.raw.forfeit);
    }

    public static void buttonCompletedOnClick(Context context) {
        play(context, R.raw.completed);
    }

    public static void clear() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.reset();
            }
            mp.release();
            mp = null;
        }
    }

    public static void play(Context context, int resID) {
        play(context, resID, 50);
    }

    public static void play(Context context, int resID, int sound) {
        try {
            clear();
            if (PreferenceUtils.isSoundOn(context).booleanValue()) {
                mp = MediaPlayer.create(context, resID);
                musicPlayerInit(sound);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
