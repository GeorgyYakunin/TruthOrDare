package com.example.truthordare.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.example.truthordare.utils.Utils;

public class TODCheckbox extends CheckBox {
    public TODCheckbox(Context context) {
        super(context);
        Utils.setFont(this);
    }

    public TODCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        Utils.setFont(this);
    }

    public TODCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Utils.setFont(this);
    }
}
