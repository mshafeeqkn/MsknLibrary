package com.mshafeeq.msknlib.views;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mshafeeq.msknlib.common.LibConstants;


public class MsknTestView extends AppCompatTextView {

    public MsknTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface fontTypeface = Typeface.createFromAsset(context.getAssets(), LibConstants.FONT_NAME);
        setTypeface(fontTypeface);
    }
}
