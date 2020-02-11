package com.example.truthordare.view;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.example.truthordare.utils.Utils;

public class TODCheckBoxPreference extends CheckBoxPreference {
    public TODCheckBoxPreference(Context context) {
        super(context);
    }

    public TODCheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TODCheckBoxPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        Utils.setFont((TextView) view.findViewById(16908310));
        Utils.setFont((TextView) view.findViewById(16908304));
    }
}
