package com.example.truthordare.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.example.truthordare.R;
import com.example.truthordare.adapter.SimplestAdapter;
import com.example.truthordare.adapter.SimplestAdapter.ViewHolder;
import com.example.truthordare.base.BaseActivity;
import com.example.truthordare.database.DBTruthDareAdapter;
import com.example.truthordare.database.IDatabaseEntry;
import com.example.truthordare.model.Player;
import com.example.truthordare.model.TruthOrDare;
import com.example.truthordare.types.GameMode;
import com.example.truthordare.types.QuestionType;
import com.example.truthordare.utils.PreferenceUtils;
import com.example.truthordare.utils.SoundUtils;
import com.example.truthordare.utils.Utils;
import com.example.truthordare.view.BounceImageButton;
import com.example.truthordare.view.SpinningDrawableView;
import com.example.truthordare.view.SpinningDrawableView.OnStartRotatingListener;
import com.example.truthordare.view.SpinningDrawableView.OnStopRotatingListener;

import java.util.Collections;
import java.util.List;

public class ActivityGamePlay extends BaseActivity implements OnInitListener {
    private static String TAG = "tag-->>";
    private static double rotateAngle;
    int adCnt = 0;
    Dialog backPressDialog;
    SpinningDrawableView bottleView;
    ImageButton btnDare;
    ImageButton btnDone;
    ImageButton btnForfeit;
    BounceImageButton btnSound;
    ImageButton btnTruth;
    List<TruthOrDare> customDares;
    List<TruthOrDare> customTruths;
    List<TruthOrDare> dares;
    GameMode gameMode;
    boolean isRotating = false;
    RelativeLayout layout;
    ListView lvScoreCard;
    TextView playerName;
    TextView playerNameDialog2;
    Dialog questionDialog;
    Dialog scoreDialog;
    Dialog truthDareDialog;
    List<TruthOrDare> truths;
    private TextToSpeech tts;
    TextView tvQuestion;
    TextView txtSound;


    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$1 */
    class C04901 implements OnClickListener {
        C04901() {
        }

        public void onClick(View v) {
            Utils.btnClickSound(ActivityGamePlay.this.getApplicationContext());
            ActivityGamePlay.this.scoreDialog.dismiss();
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$2 */
    class C04912 implements OnKeyListener {
        C04912() {
        }

        public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
            if (keyCode == 4) {
                ActivityGamePlay.this.scoreDialog.dismiss();
            }
            return true;
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$3 */
    class C04923 implements OnClickListener {
        C04923() {
        }

        public void onClick(View v) {
            ActivityGamePlay.this.goBack(false);
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$4 */
    class C04934 implements OnClickListener {
        C04934() {
        }

        public void onClick(View v) {
            ActivityGamePlay.this.goBack(true);
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$5 */
    class C04945 implements OnClickListener {
        C04945() {
        }

        public void onClick(View v) {
            SoundUtils.buttonForfeitOnClick(ActivityGamePlay.this.getApplicationContext());
            ActivityGamePlay.this.changeScore(0);
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$6 */
    class C04956 implements OnClickListener {
        C04956() {
        }

        public void onClick(View v) {
            SoundUtils.buttonCompletedOnClick(ActivityGamePlay.this.getApplicationContext());
            ActivityGamePlay.this.changeScore(1);
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$7 */
    class C04967 implements OnClickListener {
        C04967() {
        }

        public void onClick(View v) {
            ActivityGamePlay.this.openQuestionPopup(QuestionType.TRUTH);
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$8 */
    class C04978 implements OnClickListener {
        C04978() {
        }

        public void onClick(View v) {
            ActivityGamePlay.this.openQuestionPopup(QuestionType.DARE);
        }
    }

    /* renamed from: com.luckystudio.truthordare.activities.ActivityGamePlay$9 */
    class C04989 implements OnStartRotatingListener {
        C04989() {
        }

        public void onStart(float angularSpeed) {
            ActivityGamePlay.this.isRotating = true;
            if (ActivityGamePlay.isValidSpeed(angularSpeed)) {
                Utils.bottleSond(ActivityGamePlay.this.getApplicationContext());
            }
        }
    }

    public static class ScoreHolder extends ViewHolder<Player> {
        TextView playerName;
        TextView score;

        public ScoreHolder(View view) {
            super(view);
            this.playerName = (TextView) view.findViewById(R.id.playerName);
            this.score = (TextView) view.findViewById(R.id.score);
            Utils.setFont(this.playerName);
            Utils.setFont(this.score);
        }

        public void setData(Context context, Player player) {
            this.playerName.setText(player.toString());
            this.score.setText(ActivityGamePlay.getElipsedStr(PreferenceUtils.getInteger(context, player.getPrefName()) + ""));
        }
    }

    public static void createShape(Context context, int width, Canvas canvas, Paint paint) {
        int totalW = width;
        width = totalW - Utils.STROKE_WIDTH;
        List<Player> players = Utils.players;
        RectF rectF = new RectF((float) Utils.STROKE_WIDTH, (float) Utils.STROKE_WIDTH, (float) width, (float) width);
        int halfW = width / 2;
        float sAngle = 360.0f / ((float) players.size());
        float angle = 270.0f;
        int cnt = 0;
        for (Player player : players) {
            rotateAngle = ((((double) ((sAngle / 2.0f) + angle)) - 2.75d) * 3.141592653589793d) / 180.0d;
            Double xDouble = Double.valueOf(((double) halfW) + (((double) (halfW - Utils.DIST_TEXT)) * Math.cos(rotateAngle)));
            Double yDouble = Double.valueOf(((double) halfW) + (((double) (halfW - Utils.DIST_TEXT)) * Math.sin(rotateAngle)));
            int x = xDouble.intValue();
            int y = yDouble.intValue();
            float rotate = ((sAngle / 2.0f) + angle) - 180.0f;
            paint.setColor(Color.parseColor((String) Utils.COLOR_PALLET.get(cnt)));
            paint.setStyle(Style.FILL);
            paint.setAlpha(Utils.ARC_ALPHA);
            canvas.drawArc(rectF, angle, sAngle, true, paint);
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth((float) Utils.STROKE_WIDTH);
            paint.setColor(Utils.STROKE_COLOR);
            canvas.drawArc(rectF, angle, sAngle, true, paint);
            canvas.save();
            paint.setStyle(Style.FILL);
            paint.setTextSize((float) ((Utils.PLAYER_NAME_TEXT_SIZE * width) / Utils.MIN_DEVICE_WIDTH));
            paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/tod.otf"));
            paint.setColor(Utils.PLAYER_NAME_TEXT_COLOR);
            canvas.rotate(rotate, (float) x, (float) y);
            Canvas canvas2 = canvas;
            canvas2.drawText(Utils.getElipsedStr(player.playerName, 9), (float) x, (float) y, paint);
            canvas.restore();
            angle += sAngle;
            cnt++;
            if (cnt >= Utils.COLOR_PALLET.size()) {
                cnt = 0;
            }
        }
        float radius = (((float) (totalW - Utils.MAIN_STROKE)) / 2.0f) - 2.0f;
        float circleW = ((float) (Utils.STROKE_WIDTH + width)) / 2.0f;
        paint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) width, Utils.MAIN_STROKE_COLOR, Utils.MAIN_STROKE_COLOR2, TileMode.MIRROR));
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth((float) Utils.MAIN_STROKE);
        canvas.drawCircle(circleW, circleW, radius, paint);
        canvas.save();
        paint.reset();
        Double tempHalf = Double.valueOf(((double) halfW) + ((((((double) Utils.MAIN_STROKE) - (((double) Utils.RADIUS_SMALL_CIRCLE) * 2.0d)) - 4.0d) / 2.0d) - 4.0d));
        for (Player player2 : players) {
            rotateAngle = (((double) ((sAngle / 2.0f) + angle)) * 3.141592653589793d) / 180.0d;
            Double tempX = Double.valueOf(tempHalf.doubleValue() + ((tempHalf.doubleValue() - ((double) (((float) Utils.MAIN_STROKE) / 2.0f))) * Math.cos(rotateAngle)));
            Double tempY = Double.valueOf(tempHalf.doubleValue() + ((tempHalf.doubleValue() - ((double) (((float) Utils.MAIN_STROKE) / 2.0f))) * Math.sin(rotateAngle)));
            paint.setStyle(Style.FILL);
            paint.setColor(Utils.SMALL_CIRCLE);
            canvas.drawCircle(tempX.floatValue(), tempY.floatValue(), (float) Utils.RADIUS_SMALL_CIRCLE, paint);
            canvas.save();
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(5.0f);
            paint.setColor(Utils.SMALL_CIRCLE_STROKE);
            canvas.drawCircle(tempX.floatValue(), tempY.floatValue(), (float) Utils.RADIUS_SMALL_CIRCLE, paint);
            canvas.save();
            angle += sAngle;
            cnt++;
        }
    }

    private static String getElipsedStr(String text) {
        return getElipsedStr(text, Utils.MAX_TXT_LENGTH);
    }

    private static String getElipsedStr(String text, int length) {
        text = text.trim();
        return text.length() > Utils.MAX_TXT_LENGTH ? text.substring(0, length) : text;
    }

    private static boolean isValidSpeed(float angularSpeed) {
        return angularSpeed >= ((float) Utils.MIN_SPIN_SPEED) || angularSpeed <= ((float) (-Utils.MIN_SPIN_SPEED));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_gameplay);

        Utils.setFont(((TextView) findViewById(R.id.txtTitle)));
        txtSound = findViewById(R.id.soundTxt);
        Utils.players = DBTruthDareAdapter.Get_players(getApplicationContext());
        this.tts = new TextToSpeech(this, this);



        setTruthDareDialog();
        setQuestionDialog();
        setBackPressDialog();
        setScoreCardDialog();
        this.btnSound = (BounceImageButton) findViewById(R.id.imgSound);

        for (Player player : Utils.players) {
            PreferenceUtils.setInt(getApplicationContext(), player.getPrefName(), Integer.valueOf(0));
        }
        createPlayground();
        this.gameMode = GameMode.forName(PreferenceUtils.getString(getApplicationContext(), "GameMode", GameMode.TEEN.getName()));
        this.truths = IDatabaseEntry.getQuestions(this.gameMode, QuestionType.TRUTH);
        this.dares = IDatabaseEntry.getQuestions(this.gameMode, QuestionType.DARE);
        this.customDares = DBTruthDareAdapter.Get_Display_Questions(getApplicationContext(), QuestionType.DARE, Integer.valueOf(1));
        this.customTruths = DBTruthDareAdapter.Get_Display_Questions(getApplicationContext(), QuestionType.TRUTH, Integer.valueOf(1));
        this.truths.removeAll(this.customTruths);
        this.dares.removeAll(this.customDares);


        soundHandler(btnSound, txtSound);


        btnSound.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.toggleSound(btnSound.getContext());
                Utils.btnClickSound(btnSound.getContext());
                soundHandler(btnSound, txtSound);
            }
        });
    }

    protected void onDestroy() {
        if (this.tts != null) {
            this.tts.stop();
            this.tts.shutdown();
        }
        super.onDestroy();
    }

    private void setScoreCardDialog() {
        this.scoreDialog = getCommonDialog(R.layout.score_item_layout);
        this.lvScoreCard = (ListView) this.scoreDialog.findViewById(R.id.lvScoreCard);
        ImageButton backBtn = (ImageButton) this.scoreDialog.findViewById(R.id.btnBack);
        Utils.setFont(((TextView) this.scoreDialog.findViewById(R.id.txtTitle)));
        this.lvScoreCard.setAdapter(new SimplestAdapter(getApplicationContext(), ScoreHolder.class, R.layout.score_item_holder, Utils.players));
        Utils.setFont((TextView) this.scoreDialog.findViewById(R.id.tv_playerTurnName));
        backBtn.setOnClickListener(new C04901());
        this.scoreDialog.setOnKeyListener(new C04912());
    }

    private void setBackPressDialog() {
        this.backPressDialog = getCommonDialog(R.layout.dialog_back_pressed);
        LinearLayout llbackNo = (LinearLayout) this.backPressDialog.findViewById(R.id.llbackNo);
        LinearLayout llbackYes = (LinearLayout) this.backPressDialog.findViewById(R.id.llbackYes);
        Utils.setFont((TextView) this.backPressDialog.findViewById(R.id.txtBackDialog));
        llbackNo.setOnClickListener(new C04923());
        llbackYes.setOnClickListener(new C04934());
    }

    private void goBack(boolean btn) {
        Utils.btnClickSound(getApplicationContext());
        this.backPressDialog.dismiss();
        if (btn) {
                finish();
        }
    }

    private void setQuestionDialog() {
        this.questionDialog = getCommonDialog(R.layout.layout_question);
        this.playerNameDialog2 = (TextView) this.questionDialog.findViewById(R.id.tv_playerTurnName);
        this.tvQuestion = (TextView) this.questionDialog.findViewById(R.id.tv_question);
        this.btnForfeit = (ImageButton) this.questionDialog.findViewById(R.id.btnForfeit);
        this.btnDone = (ImageButton) this.questionDialog.findViewById(R.id.btnDone);
        Utils.setFont(this.playerNameDialog2);
        Utils.setFont(this.tvQuestion);
        this.btnForfeit.setOnClickListener(new C04945());
        this.btnDone.setOnClickListener(new C04956());
    }

    public void changeBottle(View view) {
        if (!this.isRotating) {
            Utils.btnClickSound(getApplicationContext());
            this.bottleView.changeBottle();
        }
    }

    public void openDialog() {
        try {
            this.adCnt++;
            this.playerName.setText(getElipsedStr(Utils.getCurrentPlayer(getApplicationContext()).playerName) + "'soundOn turn");
            showDialog(this.truthDareDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onInit(int status) {
    }

    public void changeScore(int score) {
        String prefName = Utils.getCurrentPlayer(getApplicationContext()).getPrefName();
        PreferenceUtils.setInt(getApplicationContext(), prefName, Integer.valueOf(PreferenceUtils.getInteger(getApplicationContext(), prefName).intValue() + score));
        this.questionDialog.dismiss();
    }

    private void setTruthDareDialog() {
        this.truthDareDialog = getCommonDialog(R.layout.dare_or_truth);
        this.playerName = (TextView) this.truthDareDialog.findViewById(R.id.tv_playerTurnName);
        this.btnTruth = (ImageButton) this.truthDareDialog.findViewById(R.id.btnTruth);
        this.btnDare = (ImageButton) this.truthDareDialog.findViewById(R.id.btnDare);
        Utils.setFont(this.playerName);
        this.btnTruth.setOnClickListener(new C04967());
        this.btnDare.setOnClickListener(new C04978());
    }

    private Dialog getCommonDialog(int resID) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(resID);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_gradiant);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    private void openQuestionPopup(QuestionType type) {
        Utils.btnClickSound(getApplicationContext());
        this.truthDareDialog.dismiss();
        this.playerNameDialog2.setText(getElipsedStr(Utils.getCurrentPlayer(getApplicationContext()).playerName) + "'soundOn turn");
        if (this.dares.isEmpty()) {
            this.dares = IDatabaseEntry.getQuestions(this.gameMode, QuestionType.DARE);
        }
        if (this.truths.isEmpty()) {
            this.truths = IDatabaseEntry.getQuestions(this.gameMode, QuestionType.TRUTH);
        }
        List<TruthOrDare> truthOrDares = type == QuestionType.DARE ? this.customDares.isEmpty() ? this.dares : this.customDares : this.customTruths.isEmpty() ? this.truths : this.customTruths;
        Collections.shuffle(truthOrDares);
        TruthOrDare currentQuestion = (TruthOrDare) truthOrDares.iterator().next();
        truthOrDares.remove(currentQuestion);
        this.tvQuestion.setText(currentQuestion.question);
        showDialog(this.questionDialog);
    }

    private void createPlayground() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        if (height <= width) {
            width = height - (Utils.EXTRA_PADDING * 2);
        }
        this.layout = (RelativeLayout) findViewById(R.id.view1);
        this.bottleView = (SpinningDrawableView) findViewById(R.id.spinBottleView);
        Bitmap b = Bitmap.createBitmap(width, width, Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint paint = new Paint(1);
        ImageView view = new ImageView(getApplicationContext());
        LayoutParams layoutParams = new LayoutParams(width, width);
        try {
            layoutParams.addRule(13, -1);
            view.setScaleType(ScaleType.FIT_CENTER);
            view.setLayoutParams(layoutParams);
            view.setImageBitmap(b);
            createShape(getApplicationContext(), width, canvas, paint);
            this.layout.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.bottleView.setOnStartRotatingListener(new C04989());
        this.bottleView.setOnStopRotatingListener(new OnStopRotatingListener() {
            public void onStop(float stopAngle, float angularSpeed) {
                try {
                    ActivityGamePlay.this.isRotating = false;
                    SoundUtils.clear();
                    if (ActivityGamePlay.isValidSpeed(angularSpeed)) {
                        Double turn = Double.valueOf(Math.ceil((double) (stopAngle / ((float) (360 / Utils.players.size())))));
                        if (turn.doubleValue() > ((double) Utils.players.size())) {
                            turn = new Double((double) Utils.players.size());
                        }
                        String turnStr = ((Player) Utils.players.get(turn.intValue() - 1)).playerName + "'soundOn turn";
                        PreferenceUtils.setInt(ActivityGamePlay.this.getApplicationContext(), "PlayerTurn", Integer.valueOf(turn.intValue()));
                        ActivityGamePlay.this.openDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.gc();
    }

    public void onBackPressed() {
        if (!this.isRotating) {
            showDialog(this.backPressDialog);
        }
    }


    public void openScore(View view) {
        if (!this.isRotating) {
            Utils.btnClickSound(getApplicationContext());
            showDialog(this.scoreDialog);
        }
    }

    private void showDialog(Dialog dialog) {
        if (dialog != null && !isFinishing()) {
            dialog.show();
        }
    }


    public static void soundHandler(ImageButton button, TextView txt) {
        if (PreferenceUtils.isSoundOn(button.getContext()).booleanValue()) {

            txt.setText("SOUND ON");
        } else {
            txt.setText("SOUND OFF");
        }
        button.setBackgroundResource(PreferenceUtils.isSoundOn(button.getContext()).booleanValue() ? R.drawable.background_effect : R.drawable.background_effect);
    }


}
