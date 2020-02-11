package com.example.truthordare.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.example.truthordare.utils.Utils;

public class TODTextView extends TextView {
    public TODTextView(Context context) {
        super(context);
        Utils.setFont(this);
    }

    public TODTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Utils.setFont(this);
    }

    public TODTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Utils.setFont(this);
    }
}
