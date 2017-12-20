package com.ahmedelouha.telfaza.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

import com.ahmedelouha.telfaza.R;
import com.ahmedelouha.telfaza.utils.FontCache;

/**
 * Created by raaja on 18-12-2017.
 */

public class CustomFontTextView extends AppCompatTextView {

    static String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        String fontName = typedArray.getString(R.styleable.CustomFontTextView_font);
        typedArray.recycle();
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        Typeface typeface = getCustomTypeFace(context,textStyle,fontName);
        setTypeface(typeface);
    }

    private Typeface getCustomTypeFace(Context context, int typefaceStyle, String font){

        switch (typefaceStyle) {
            case Typeface.BOLD:
                return FontCache.getTypeface(context,font);

            case Typeface.ITALIC:
                return FontCache.getTypeface(context,font);

            case Typeface.BOLD_ITALIC:
                return FontCache.getTypeface(context,font);

            case Typeface.NORMAL:
            default:
                return FontCache.getTypeface(context,font);
        }
    }
}
