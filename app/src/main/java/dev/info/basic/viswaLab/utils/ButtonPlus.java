package dev.info.basic.viswaLab.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by RSR on 12-08-2017.
 */

public class ButtonPlus extends Button {
    public ButtonPlus(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public ButtonPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/OpenSans-Regular.ttf", context);
        setTypeface(customFont);
    }
}
