package com.example.truthordare.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.truthordare.R;
import com.example.truthordare.adapter.SimplestAdapter;
import com.example.truthordare.base.BaseActivity;
import com.example.truthordare.types.GameMode;
import com.example.truthordare.utils.PreferenceUtils;
import com.example.truthordare.utils.Utils;
import java.util.Random;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class ActivityGame extends BaseActivity {

    public static final Random RANDOM = new Random();
    private View main;
    private ImageView bottle;
    private int lastAngle = -1;

    public static class ViewHolder extends com.example.truthordare.adapter.SimplestAdapter.ViewHolder<GameMode> {
        private TextView txtGameType;
        private ImageView imgGameTYpe;

        public ViewHolder(View view) {
            super(view);
            this.txtGameType = (TextView) view.findViewById(R.id.txtGameType);
            this.imgGameTYpe = (ImageView) view.findViewById(R.id.imgGameTYpe);
        }

        public void setData(final Context context, final GameMode gameMode) {
            this.txtGameType.setText(gameMode.modeName);
            this.txtGameType.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Utils.btnClickSound(context);
                    context.startActivity(new Intent(context, ActivityAddingPlayer.class).addFlags(FLAG_ACTIVITY_NEW_TASK));
                    PreferenceUtils.setString(context, "GameMode", gameMode.getName());
                }
            });

            this.imgGameTYpe.setImageResource(gameMode.modeImg);
        }


    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_game);

        ((ListView) findViewById(R.id.lv_game_modes)).setAdapter(new SimplestAdapter(getApplicationContext(), ViewHolder.class, R.layout.item_cell_mode_of_game, GameMode.getGameModes(getApplication())));
        System.gc();

        ((LinearLayout) findViewById(R.id.layBack)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        this.bottle = (ImageView) findViewById(R.id.imgBottle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spinTheBottle();
            }
        }, 1000);

    }




    private void spinTheBottle() {

        int angle = RANDOM.nextInt(360 - 0) + 360;
        float pivotX = bottle.getWidth() / 2;
        float pivotY = bottle.getHeight() / 2;


        final Animation animRotate = new RotateAnimation(lastAngle == -1 ? 0 : lastAngle, angle, pivotX, pivotY);
        lastAngle = angle;

        animRotate.setDuration(1000);
        animRotate.setFillAfter(true);

        bottle.startAnimation(animRotate);
        animRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                spinTheBottle();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
