package com.example.truthordare.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.truthordare.R;
import com.example.truthordare.utils.PreferenceUtils;
import java.util.Arrays;
import java.util.List;

public class SpinningDrawableView extends View {
    private static final float ARC_OF_TOLERANCE = 30.0f;
    private static final float BOUNCE_ENERGY_COEFFICIENT = 0.2f;
    private static final float DEGREES_PER_HALF_PERIOD = 180.0f;
    private static final float DEGREES_PER_PERIOD = 360.0f;
    private static final float DEGREES_PER_QUARTER_PERIOD = 90.0f;
    private static final float DEGREES_PER_THREE_QUARTER_PERIOD = 270.0f;
    private static final float FRICTION = 0.5f;
    private static final boolean LOG = false;
    public static final float MAX_ROTATION_DEGREES = 60.0f;
    public static final float MIN_ROTATION_DEGREES = 0.0f;
    private static final String TAG = "SpinningDrawableView";
    private static final int TOUCH_BOTTOM = 2;
    private static final int TOUCH_NOT = 0;
    private static final int TOUCH_TOP = 1;
    private static final float VELOCITY_MAX = 1.0f;
    static List<Integer> bottles = Arrays.asList(new Integer[]{Integer.valueOf(R.drawable.bottle1), Integer.valueOf(R.drawable.bottle2), Integer.valueOf(R.drawable.bottle3), Integer.valueOf(R.drawable.bottle4), Integer.valueOf(R.drawable.bottle5), Integer.valueOf(R.drawable.bottle6), Integer.valueOf(R.drawable.bottle7), Integer.valueOf(R.drawable.bottle8), Integer.valueOf(R.drawable.bottle9)});
    private static Matrix matrix = new Matrix();
    private float angle1;
    private float angle2;
    private float angle3;
    private int bottleCnt;
    private BitmapDrawable drawable;
    private int drawableId;
    private float formerAngle;
    private long formerTime;
    private boolean obstacleExists;
    private OnStartRotatingListener onStartRotatingListener;
    private OnStopRotatingListener onStopRotatingListener;
    private boolean rotating;
    private float rotationDegrees;
    private int rotationPivotX;
    private float rotationPivotXCoefficient;
    private int rotationPivotY;
    private float rotationPivotYCoefficient;
    private float rotationStepDegrees;
    private float startSpeed;
    private float succeedingAngle;
    private long succeedingTime;
    private int touchState;

    public interface OnStopRotatingListener {
        void onStop(float f, float f2);
    }

    public interface OnStartRotatingListener {
        void onStart(float f);
    }

    class SwipeDetector implements OnTouchListener {
        SwipeDetector() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            float f = SpinningDrawableView.DEGREES_PER_PERIOD;
            if (SpinningDrawableView.this.rotating) {
                return false;
            }
            float y;
            SpinningDrawableView spinningDrawableView;
            float access$200;
            switch (event.getAction()) {
                case 0:
                    SpinningDrawableView.this.touchState = isInTouchWithObject(event);
                    switch (SpinningDrawableView.this.touchState) {
                        case 0:
                            y = -(event.getY() - ((float) (SpinningDrawableView.this.getHeight() / 2)));
                            SpinningDrawableView.this.succeedingAngle = (-((float) Math.toDegrees(Math.atan2((double) y, (double) (event.getX() - ((float) (SpinningDrawableView.this.getWidth() / 2))))))) + SpinningDrawableView.DEGREES_PER_QUARTER_PERIOD;
                            spinningDrawableView = SpinningDrawableView.this;
                            access$200 = SpinningDrawableView.this.succeedingAngle;
                            if (SpinningDrawableView.this.succeedingAngle >= 0.0f) {
                                f = 0.0f;
                            }
                            spinningDrawableView.succeedingAngle = f + access$200;
                            SpinningDrawableView.this.succeedingTime = System.currentTimeMillis();
                            SpinningDrawableView.this.obstacleExists = true;
                            break;
                        case 1:
                            SpinningDrawableView.this.stopRotating();
                            y = -(event.getY() - ((float) (SpinningDrawableView.this.getHeight() / 2)));
                            SpinningDrawableView.this.succeedingAngle = (-((float) Math.toDegrees(Math.atan2((double) y, (double) (event.getX() - ((float) (SpinningDrawableView.this.getWidth() / 2))))))) + SpinningDrawableView.DEGREES_PER_QUARTER_PERIOD;
                            spinningDrawableView = SpinningDrawableView.this;
                            access$200 = SpinningDrawableView.this.succeedingAngle;
                            if (SpinningDrawableView.this.succeedingAngle >= 0.0f) {
                                f = 0.0f;
                            }
                            spinningDrawableView.succeedingAngle = f + access$200;
                            SpinningDrawableView.this.succeedingTime = System.currentTimeMillis();
                            SpinningDrawableView.this.rotateTo(SpinningDrawableView.this.succeedingAngle);
                            break;
                        case 2:
                            SpinningDrawableView.this.stopRotating();
                            y = -(event.getY() - ((float) (SpinningDrawableView.this.getHeight() / 2)));
                            SpinningDrawableView.this.succeedingAngle = (-((float) Math.toDegrees(Math.atan2((double) y, (double) (event.getX() - ((float) (SpinningDrawableView.this.getWidth() / 2))))))) - SpinningDrawableView.DEGREES_PER_QUARTER_PERIOD;
                            spinningDrawableView = SpinningDrawableView.this;
                            access$200 = SpinningDrawableView.this.succeedingAngle;
                            if (SpinningDrawableView.this.succeedingAngle >= 0.0f) {
                                f = 0.0f;
                            }
                            spinningDrawableView.succeedingAngle = f + access$200;
                            SpinningDrawableView.this.succeedingTime = System.currentTimeMillis();
                            SpinningDrawableView.this.rotateTo(SpinningDrawableView.this.succeedingAngle);
                            break;
                    }
                    break;
                case 1:
                    if (SpinningDrawableView.this.obstacleExists) {
                        SpinningDrawableView.this.obstacleExists = false;
                    }
                    if (!(SpinningDrawableView.this.touchState == 0 || SpinningDrawableView.this.formerTime == -1)) {
                        float velocity = SpinningDrawableView.this.calculateAngularVelocity();
                        if (velocity > 1.0f) {
                            SpinningDrawableView.this.setRotationStepDegrees(60.0f);
                        } else if (velocity < -1.0f) {
                            SpinningDrawableView.this.setRotationStepDegrees(-60.0f);
                        } else {
                            SpinningDrawableView.this.setRotationStepDegrees(60.0f * velocity);
                        }
                        SpinningDrawableView.this.startRotating();
                    }
                    SpinningDrawableView.this.formerTime = -1;
                    SpinningDrawableView.this.succeedingTime = -1;
                    break;
                case 2:
                    switch (SpinningDrawableView.this.touchState) {
                        case 0:
                            SpinningDrawableView.this.formerAngle = SpinningDrawableView.this.succeedingAngle;
                            SpinningDrawableView.this.formerTime = SpinningDrawableView.this.succeedingTime;
                            y = -(event.getY() - ((float) (SpinningDrawableView.this.getHeight() / 2)));
                            SpinningDrawableView.this.succeedingAngle = (-((float) Math.toDegrees(Math.atan2((double) y, (double) (event.getX() - ((float) (SpinningDrawableView.this.getWidth() / 2))))))) + SpinningDrawableView.DEGREES_PER_QUARTER_PERIOD;
                            spinningDrawableView = SpinningDrawableView.this;
                            access$200 = SpinningDrawableView.this.succeedingAngle;
                            if (SpinningDrawableView.this.succeedingAngle >= 0.0f) {
                                f = 0.0f;
                            }
                            spinningDrawableView.succeedingAngle = f + access$200;
                            SpinningDrawableView.this.succeedingTime = System.currentTimeMillis();
                            SpinningDrawableView.this.obstacleExists = true;
                            break;
                        case 1:
                            SpinningDrawableView.this.formerAngle = SpinningDrawableView.this.succeedingAngle;
                            SpinningDrawableView.this.formerTime = SpinningDrawableView.this.succeedingTime;
                            y = -(event.getY() - ((float) (SpinningDrawableView.this.getHeight() / 2)));
                            SpinningDrawableView.this.succeedingAngle = (-((float) Math.toDegrees(Math.atan2((double) y, (double) (event.getX() - ((float) (SpinningDrawableView.this.getWidth() / 2))))))) + SpinningDrawableView.DEGREES_PER_QUARTER_PERIOD;
                            spinningDrawableView = SpinningDrawableView.this;
                            access$200 = SpinningDrawableView.this.succeedingAngle;
                            if (SpinningDrawableView.this.succeedingAngle >= 0.0f) {
                                f = 0.0f;
                            }
                            spinningDrawableView.succeedingAngle = f + access$200;
                            SpinningDrawableView.this.succeedingTime = System.currentTimeMillis();
                            SpinningDrawableView.this.rotateTo(SpinningDrawableView.this.succeedingAngle);
                            break;
                        case 2:
                            SpinningDrawableView.this.formerAngle = SpinningDrawableView.this.succeedingAngle;
                            SpinningDrawableView.this.formerTime = SpinningDrawableView.this.succeedingTime;
                            y = -(event.getY() - ((float) (SpinningDrawableView.this.getHeight() / 2)));
                            SpinningDrawableView.this.succeedingAngle = (-((float) Math.toDegrees(Math.atan2((double) y, (double) (event.getX() - ((float) (SpinningDrawableView.this.getWidth() / 2))))))) - SpinningDrawableView.DEGREES_PER_QUARTER_PERIOD;
                            spinningDrawableView = SpinningDrawableView.this;
                            access$200 = SpinningDrawableView.this.succeedingAngle;
                            if (SpinningDrawableView.this.succeedingAngle >= 0.0f) {
                                f = 0.0f;
                            }
                            spinningDrawableView.succeedingAngle = f + access$200;
                            SpinningDrawableView.this.succeedingTime = System.currentTimeMillis();
                            SpinningDrawableView.this.rotateTo(SpinningDrawableView.this.succeedingAngle);
                            break;
                        default:
                            break;
                    }
                default:
                    return false;
            }
            return true;
        }

        private int isInTouchWithObject(MotionEvent event) {
            float degrees = (-((float) Math.toDegrees(Math.atan2((double) (-(event.getY() - ((float) (SpinningDrawableView.this.getHeight() / 2)))), (double) (event.getX() - ((float) (SpinningDrawableView.this.getWidth() / 2))))))) + SpinningDrawableView.DEGREES_PER_QUARTER_PERIOD;
            float currentObjectAngle = SpinningDrawableView.this.rotationDegrees < 0.0f ? SpinningDrawableView.this.rotationDegrees + SpinningDrawableView.DEGREES_PER_PERIOD : SpinningDrawableView.this.rotationDegrees;
            if (Math.abs(currentObjectAngle - degrees) < 30.0f || Math.abs((currentObjectAngle - degrees) - SpinningDrawableView.DEGREES_PER_PERIOD) < 30.0f) {
                return 1;
            }
            if (Math.abs(Math.abs(currentObjectAngle - degrees) - 180.0f) < 30.0f) {
                return 2;
            }
            return 0;
        }
    }

    public SpinningDrawableView(Context context) {
        super(context);
        this.bottleCnt = 0;
        this.rotationDegrees = 0.0f;
        this.rotationPivotXCoefficient = FRICTION;
        this.rotationPivotYCoefficient = FRICTION;
        this.rotating = false;
        this.obstacleExists = false;
        this.drawableId = -1;
        this.drawable = null;
        this.formerAngle = 0.0f;
        this.formerTime = -1;
        this.succeedingAngle = 0.0f;
        this.succeedingTime = -1;
        setOnTouchListener(new SwipeDetector());
    }

    public SpinningDrawableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        setOnTouchListener(new SwipeDetector());
    }

    public SpinningDrawableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bottleCnt = 0;
        this.rotationDegrees = 0.0f;
        this.rotationPivotXCoefficient = FRICTION;
        this.rotationPivotYCoefficient = FRICTION;
        this.rotating = false;
        this.obstacleExists = false;
        this.drawableId = -1;
        this.drawable = null;
        this.formerAngle = 0.0f;
        this.formerTime = -1;
        this.succeedingAngle = 0.0f;
        this.succeedingTime = -1;
        init(attrs);
        setOnTouchListener(new SwipeDetector());
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = Math.min(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        setMeasuredDimension(height, height);
        if (this.drawableId != -1 && this.drawable == null) {
            setResourceDrawable(this.drawableId);
        }
    }

    private void init(AttributeSet attrs) {
        this.bottleCnt = PreferenceUtils.getInteger(getContext(), "BottleCnt").intValue();
//        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.spinthebottle);
        this.drawableId = ((Integer) bottles.get(this.bottleCnt)).intValue();
//        a.recycle();
    }

    public void changeBottle() {
        this.bottleCnt++;
        if (this.bottleCnt >= bottles.size()) {
            this.bottleCnt = 0;
        }
        setResourceDrawable(((Integer) bottles.get(this.bottleCnt)).intValue());
        PreferenceUtils.setInt(getContext(), "BottleCnt", Integer.valueOf(this.bottleCnt));
    }

    private DisplayMetrics initializeMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    protected void onDraw(Canvas canvas) {
        if (this.drawable != null) {
            synchronized (this) {
                updateRotationDegree();
            }
            matrix.reset();
            matrix.setRotate(this.rotationDegrees, (float) this.rotationPivotX, (float) this.rotationPivotY);
            canvas.drawBitmap(this.drawable.getBitmap(), matrix, null);
            if (this.rotating) {
                invalidate();
            }
        }
    }

    private void updateRotationDegree() {
        float f = DEGREES_PER_PERIOD;
        if (!this.obstacleExists) {
            this.rotationDegrees += this.rotationStepDegrees;
            if (this.rotationDegrees < 0.0f) {
                this.rotationDegrees = DEGREES_PER_PERIOD + this.rotationDegrees;
            } else {
                this.rotationDegrees %= DEGREES_PER_PERIOD;
            }
            applyFriction();
        } else if (this.rotationStepDegrees > 0.0f) {
            this.angle1 = Float.valueOf((this.rotationDegrees + 30.0f) % DEGREES_PER_PERIOD).floatValue();
            this.angle2 = Float.valueOf(this.succeedingAngle).floatValue();
            this.angle3 = Float.valueOf(((this.rotationDegrees + 30.0f) + 180.0f) % DEGREES_PER_PERIOD).floatValue();
            if (this.angle2 < DEGREES_PER_QUARTER_PERIOD && (this.angle1 >= 270.0f || this.angle3 >= 270.0f)) {
                this.angle1 += DEGREES_PER_QUARTER_PERIOD;
                this.angle1 %= DEGREES_PER_PERIOD;
                this.angle3 += DEGREES_PER_QUARTER_PERIOD;
                this.angle3 %= DEGREES_PER_PERIOD;
                this.angle2 += DEGREES_PER_QUARTER_PERIOD;
            }
            if (this.angle2 > this.angle1 && this.angle2 < this.angle1 + this.rotationStepDegrees) {
                this.rotationDegrees = this.succeedingAngle - 30.0f;
                float f2 = this.rotationDegrees;
                if (this.rotationDegrees >= 0.0f) {
                    f = 0.0f;
                }
                this.rotationDegrees = f + f2;
                this.rotationStepDegrees = -(this.rotationStepDegrees * BOUNCE_ENERGY_COEFFICIENT);
            } else if (this.angle2 <= this.angle3 || this.angle2 >= this.angle3 + this.rotationStepDegrees) {
                this.rotationDegrees += this.rotationStepDegrees;
                if (this.rotationDegrees < 0.0f) {
                    this.rotationDegrees = DEGREES_PER_PERIOD + this.rotationDegrees;
                } else {
                    this.rotationDegrees %= DEGREES_PER_PERIOD;
                }
                applyFriction();
            } else {
                this.rotationDegrees = ((this.succeedingAngle - 30.0f) + 180.0f) % DEGREES_PER_PERIOD;
                this.rotationStepDegrees = -(this.rotationStepDegrees * BOUNCE_ENERGY_COEFFICIENT);
            }
        } else if (this.rotationStepDegrees < 0.0f) {
            this.angle1 = Float.valueOf(this.rotationDegrees - 30.0f).floatValue();
            this.angle1 = (this.angle1 < 0.0f ? DEGREES_PER_PERIOD : 0.0f) + this.angle1;
            this.angle2 = Float.valueOf(this.succeedingAngle).floatValue();
            this.angle3 = Float.valueOf(((this.rotationDegrees - 30.0f) + 180.0f) % DEGREES_PER_PERIOD).floatValue();
            if (this.angle2 > 270.0f && (this.angle1 < DEGREES_PER_QUARTER_PERIOD || this.angle3 < DEGREES_PER_QUARTER_PERIOD)) {
                this.angle2 += DEGREES_PER_QUARTER_PERIOD;
                this.angle2 %= DEGREES_PER_PERIOD;
                this.angle1 += DEGREES_PER_QUARTER_PERIOD;
                this.angle3 += DEGREES_PER_QUARTER_PERIOD;
            }
            if (this.angle2 < this.angle1 && this.angle2 > this.angle1 + this.rotationStepDegrees) {
                this.rotationDegrees = (this.succeedingAngle + 30.0f) % DEGREES_PER_PERIOD;
                this.rotationStepDegrees = -(this.rotationStepDegrees * BOUNCE_ENERGY_COEFFICIENT);
            } else if (this.angle2 >= this.angle3 || this.angle2 <= this.angle3 + this.rotationStepDegrees) {
                this.rotationDegrees += this.rotationStepDegrees;
                if (this.rotationDegrees < 0.0f) {
                    this.rotationDegrees = DEGREES_PER_PERIOD + this.rotationDegrees;
                } else {
                    this.rotationDegrees %= DEGREES_PER_PERIOD;
                }
                applyFriction();
            } else {
                this.rotationDegrees = ((this.succeedingAngle + 30.0f) + 180.0f) % DEGREES_PER_PERIOD;
                this.rotationStepDegrees = -(this.rotationStepDegrees * BOUNCE_ENERGY_COEFFICIENT);
            }
        }
    }

    private void applyFriction() {
        if (0.0f == ((float) Math.round(this.rotationStepDegrees))) {
            stopRotating();
            return;
        }
        this.rotationStepDegrees = (this.rotationStepDegrees < 0.0f ? FRICTION : -0.5f) + this.rotationStepDegrees;
    }

    public void setBitmap(Bitmap bitmap, boolean optimizeBitmap) {
        setDrawable(new BitmapDrawable(bitmap), optimizeBitmap);
    }

    public void setDrawable(BitmapDrawable drawable, boolean optimizeDrawable) {
        if (this.drawable != null) {
            if (this.drawable.getBitmap() != null) {
                this.drawable.getBitmap().recycle();
            }
            this.drawable.setCallback(null);
            unscheduleDrawable(this.drawable);
            this.drawable = null;
            System.gc();
        }
        Bitmap originalBitmap = drawable.getBitmap();
        if (originalBitmap != null) {
            int targetWidth = getMeasuredWidth();
            int targetHeight = getMeasuredHeight();
            if (!optimizeDrawable || (originalBitmap.getWidth() == targetWidth && originalBitmap.getHeight() == targetHeight)) {
                this.drawable = drawable;
                this.drawable.setCallback(this);
            } else {
                Bitmap optimizedBitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true);
                this.drawable = new BitmapDrawable(optimizedBitmap);
                this.drawable.setCallback(this);
                originalBitmap.recycle();
                drawable.setCallback(null);
                unscheduleDrawable(drawable);
                drawable = null;
                optimizedBitmap.recycle();
                System.gc();
            }
            this.rotationPivotX = (int) (((float) drawable.getBitmap().getWidth()) * this.rotationPivotXCoefficient);
            this.rotationPivotY = (int) (((float) drawable.getBitmap().getHeight()) * this.rotationPivotYCoefficient);
        }
        invalidate();
    }

    public void setResourceDrawable(int resourceId) {
        this.drawableId = resourceId;
        Options options = new Options();
        int imageDimension = getImageDimension(resourceId);
        DisplayMetrics metrics = initializeMetrics();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        options.inScaled = true;
        options.inTargetDensity = (int) (((float) metrics.densityDpi) * calculateFrameAndDrawableRatioCoefficient(getMeasuredWidth(), imageDimension));
        options.inPurgeable = false;
        options.inDensity = metrics.densityDpi;
        setBitmap(BitmapFactory.decodeResource(getResources(), this.drawableId, options), false);
    }

    private int getImageDimension(int resourceId) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resourceId, options);
        return Math.max(options.outWidth, options.outHeight);
    }

    private float calculateFrameAndDrawableRatioCoefficient(int preferredDimension, int drawableDimension) {
        return ((float) preferredDimension) / ((float) drawableDimension);
    }

    public void setRotationStepDegrees(float degrees) {
        this.rotationStepDegrees = degrees;
    }

    private float getRotationStepDegrees() {
        return this.rotationStepDegrees;
    }

    public void rotateTo(float degrees) {
        this.rotationDegrees = degrees;
        this.rotationStepDegrees = 0.0f;
        invalidate();
    }

    public void startRotating() {
        this.rotating = true;
        if (this.onStartRotatingListener != null) {
            this.startSpeed = getRotationStepDegrees();
            this.onStartRotatingListener.onStart(this.startSpeed);
        }
        invalidate();
    }

    public void stopRotating() {
        if (this.rotating && this.onStopRotatingListener != null) {
            this.onStopRotatingListener.onStop(this.rotationDegrees, this.startSpeed);
        }
        this.rotating = false;
    }

    private float calculateAngularVelocity() {
        if (this.formerTime == -1) {
            return 0.0f;
        }
        float angularDistance = this.succeedingAngle - this.formerAngle;
        if (angularDistance < -180.0f) {
            angularDistance += DEGREES_PER_PERIOD;
        } else if (angularDistance > 180.0f) {
            angularDistance = Math.abs(angularDistance - DEGREES_PER_PERIOD);
        }
        return angularDistance / ((float) (this.succeedingTime - this.formerTime));
    }

    public void setOnStartRotatingListener(OnStartRotatingListener listener) {
        this.onStartRotatingListener = listener;
    }

    public void setOnStopRotatingListener(OnStopRotatingListener listener) {
        this.onStopRotatingListener = listener;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationStepDegrees = rotationSpeed;
    }

    public float getRotationDegree() {
        return this.rotationDegrees;
    }
}
