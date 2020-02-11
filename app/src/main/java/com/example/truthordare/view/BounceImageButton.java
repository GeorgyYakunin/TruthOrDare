package com.example.truthordare.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import com.example.truthordare.R;
import com.example.truthordare.utils.CustomBounceInterpolator;

public class BounceImageButton extends ImageButton {
    private float amplitude = 0.2f;
    private int frequency = 20;

    public BounceImageButton(Context context) {
        super(context);
    }

    public BounceImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setExtraAttribs(context, attrs);
    }

    public BounceImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setExtraAttribs(context, attrs);
    }

    private void setExtraAttribs(Context context, AttributeSet attrs) {
        /*TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BounceImageButton);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case 0:
                    this.amplitude = a.getFloat(attr, this.amplitude);
                    break;
                case 1:
                    this.frequency = a.getInt(attr, this.frequency);
                    break;
                default:
                    break;
            }
        }
        a.recycle();*/
    }

    public boolean performClick() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        animation.setInterpolator(new CustomBounceInterpolator((double) this.amplitude, (double) this.frequency));
        startAnimation(animation);
        return super.performClick();
    }
}
