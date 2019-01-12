package com.egova.eagleyes.util;

import android.text.TextUtils;

public class StringUtil {

    public static String formatNull(String src){
        if(TextUtils.isEmpty(src)||"null".equalsIgnoreCase(src)){
            return "";
        }
        return src;
    }
}
