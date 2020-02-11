package com.example.truthordare.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.truthordare.utils.Utils;

public class BounceButton extends Button {
    private float amplitude = 0.2f;
    private int frequency = 20;

    public BounceButton(Context context) {
        super(context);
    }

    public BounceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setExtraAttribs(context, attrs);
    }

    public BounceButton(Context context, AttributeSet attrs, int defStyle) {
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
        Utils.performBounceClick(this, this.amplitude, this.frequency);
        return super.performClick();
    }
}
