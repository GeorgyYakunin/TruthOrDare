package com.example.truthordare.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.truthordare.R;
import com.example.truthordare.activities.ActivityGamePlay.ScoreHolder;
import com.example.truthordare.adapter.SimplestAdapter;
import com.example.truthordare.model.Player;
import com.example.truthordare.types.QuestionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static int AD_CNT = 1;
    public static int ARC_ALPHA = 230;
    public static List<String> COLOR_PALLET = Arrays.asList(new String[]{"#1A237E", "#00C853", "#FFD600", "#E91E63", "#2196F3", "#FF5722", "#607D8B", "#009688", "#E53935", "#2E7D32", "#795548", "#673AB7", "#006064", "#0D47A1", "#DD2C00", "#4A148C", "#222222", "#0D47A1", "#FF6F00", "#9E9E9E"});
    public static int DIST_TEXT = 75;
    public static int EXTRA_PADDING = 20;
    public static int MAIN_STROKE = 50;
    public static int MAIN_STROKE_COLOR = Color.parseColor("#F0EA3A");
    public static int MAIN_STROKE_COLOR2 = Color.parseColor("#F39F1B");
    public static int MAX_NO_OF_PLAYERS = 15;
    public static int MAX_TXT_LENGTH = 15;
    public static int MIN_DEVICE_WIDTH = 800;
    public static int MIN_SPIN_SPEED = 20;
    public static int PLAYER_NAME_TEXT_COLOR = -1;
    public static int PLAYER_NAME_TEXT_SIZE = 50;
    public static final String PREF_PREFIX = "KEY_SHOW_";
    public static int RADIUS_SMALL_CIRCLE = 15;
    public static String SCORE_PREFIX = "SCORE_PLAYER_";
    public static int SMALL_CIRCLE = Color.parseColor("#F7FD9E");
    public static int SMALL_CIRCLE_STROKE = Color.parseColor("#D47713");
    public static int STROKE_COLOR = Color.parseColor("#EFEFEF");
    public static int STROKE_WIDTH = 10;
    private static final String TAG = Utils.class.getSimpleName();
    public static int cnt = 1;
    public static final int initialRound = 1;
    public static List<Player> players = new ArrayList();
    public static int roundCnt = 1;
    public static QuestionType selectedQuestionType = null;

    public static void setVariableDefault(Context context) {
        PreferenceUtils.setInt(context, "Turn", Integer.valueOf(0));
        for (int i = 0; i <= 10; i++) {
            PreferenceUtils.setInt(context, "ScorePlayer" + i, Integer.valueOf(0));
        }
        cnt = 1;
        roundCnt = 1;
    }






    public static void btnClickSound(Context context) {
        SoundUtils.buttonOnClick(context);
    }

    public static void bottleSond(Context context) {
        SoundUtils.play(context, R.raw.bottlesound, 100);
    }

    public static void goBack(Activity activity) {
        btnClickSound(activity);
        activity.finish();
    }

    public static Player getCurrentPlayer(Context context) {
        return (Player) players.get(PreferenceUtils.getInteger(context, "PlayerTurn", Integer.valueOf(1)).intValue() - 1);
    }

    public static void setFont(TextView view) {
        view.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), String.format(Locale.US, "fonts/%s", new Object[]{"tod.otf"})));
    }

    public static void performBounceClick(View view, float amplitude, int frequency) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
        animation.setInterpolator(new CustomBounceInterpolator((double) amplitude, (double) frequency));
        view.startAnimation(animation);
    }

    public static Dialog getCommonDialog(Context context, int resID) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(resID);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    public static void showDialog(Activity activity, Dialog dialog) {
        if (dialog != null && !activity.isFinishing()) {
            dialog.show();
        }
    }

    public static Dialog setBackPressDialog(final Activity activity) {
        final Dialog backPressDialog = getCommonDialog(activity, R.layout.dialog_back_pressed);
        TextView backYes = (TextView) backPressDialog.findViewById(R.id.backYes);
        ((TextView) backPressDialog.findViewById(R.id.backNo)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Utils.goBack(activity, backPressDialog, false);
            }
        });
        backYes.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Utils.goBack(activity, backPressDialog, true);
            }
        });
        return backPressDialog;
    }

    public static void goBack(Activity activity, Dialog dialog, boolean btn) {
        btnClickSound(dialog.getContext());
        dialog.dismiss();

    }

    public static void changeScore(Context context, int score) {
        String prefName = getCurrentPlayer(context).getPrefName();
        PreferenceUtils.setInt(context, prefName, Integer.valueOf(PreferenceUtils.getInteger(context, prefName).intValue() + score));
    }

    public static Dialog setScoreCardDialog(final Context context) {
        final Dialog scoreDialog = getCommonDialog(context, R.layout.score_item_layout);
        ImageButton backBtn = (ImageButton) scoreDialog.findViewById(R.id.btnBack);
        ((ListView) scoreDialog.findViewById(R.id.lvScoreCard)).setAdapter(new SimplestAdapter(context, ScoreHolder.class, R.layout.score_item_holder, players));
        backBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Utils.btnClickSound(context);
                scoreDialog.dismiss();
            }
        });
        scoreDialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == 4) {
                    scoreDialog.dismiss();
                }
                return true;
            }
        });
        return scoreDialog;
    }

    public static String getElipsedStr(String text) {
        return getElipsedStr(text, MAX_TXT_LENGTH);
    }

    public static String getElipsedStr(String text, int length) {
        text = text.trim();
        return text.length() > MAX_TXT_LENGTH ? text.substring(0, length) : text;
    }
}
