package com.example.truthordare.base;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);

    }


    /*public void setButtonsHeightWidhth(LinearLayout lay){
        int width = getWindowManager().getDefaultDisplay().getWidth()-500;
        ViewGroup.LayoutParams params = lay.getLayoutParams();
        params.width = width;
        params.height = width/4;
        lay.setLayoutParams(params);
    }*/
}