package com.example.truthordare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.truthordare.R;
import com.example.truthordare.base.BaseActivity;
import com.example.truthordare.utils.Utils;
import java.util.Random;

public class ActivityMain extends BaseActivity {


    public boolean isFirstClicked = true;
    public static final Random RANDOM = new Random();
    private View main;
    private ImageView bottle;
    private int lastAngle = -1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_truth_or_dare);
        Utils.setVariableDefault(this);

        this.bottle = (ImageView) findViewById(R.id.imgBottle);



        Utils.setFont(((TextView) findViewById(R.id.txtTitle)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spinTheBottle();
            }
        }, 1000);

        ((LinearLayout) findViewById(R.id.layPlay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivityGame.class));

                //        Utils.mainButtonClick(this, ActivityGame.class);   -------original
            }
        });
        ((LinearLayout) findViewById(R.id.laySetting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(ActivityMain.this, ActivitySetting.class));
            }
        });
//        ((LinearLayout) findViewById(R.id.layRateUs)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri uri = Uri.parse("market://details?id=" + getPackageName());
//                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//                // To count with Play market backstack, After pressing back button,
//                // to taken back to our application, we need to add following flags to intent.
//                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
//                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                try {
//                    startActivity(goToMarket);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
//                }
//            }
//        });


    }


    public void onBackPressed() {
        if (this.isFirstClicked) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG).show();
            this.isFirstClicked = false;
            return;
        }
        super.onBackPressed();
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
