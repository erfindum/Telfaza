package com.ahmedelouha.telfaza.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import java.util.HashMap;

/**
 * Created by raaja on 18-12-2017.
 */

public class FontCache {

    private static HashMap<String,Typeface> fontcache = new HashMap<>();

    public static Typeface getTypeface(Context context,String fontName){
        Typeface typeface = fontcache.get(fontName);
        if(typeface == null){
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            }catch (Exception e){
                return null;
            }
            fontcache.put(fontName,typeface);
            return typeface;
        }
        return typeface;
    }

}
