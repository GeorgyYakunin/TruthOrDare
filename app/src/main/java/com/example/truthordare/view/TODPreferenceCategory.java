package com.example.truthordare.view;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.example.truthordare.utils.Utils;

public class TODPreferenceCategory extends PreferenceCategory {
    public TODPreferenceCategory(Context context) {
        super(context);
    }

    public TODPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TODPreferenceCategory(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        Utils.setFont((TextView) view.findViewById(16908310));
        ((TextView) view.findViewById(16908310)).setTextSize(25.0f);
    }
}
