package com.egova.eagleyes.util;

import android.text.TextUtils;

import com.egova.baselibrary.util.DateUtil;
import com.egova.eagleyes.model.request.SearchCondition;

public class VehicleUtil {

    public static String formatPlateNo(String plateNo) {
        if (plateNo == null || TextUtils.isEmpty(plateNo)) {
            return "无牌车";
        }
        if (plateNo.contains("----")) {
            return "无牌车";
        }
        return plateNo;
    }

    public static String getShowTime(String dateStr) {
        return DateUtil.dateFormat(DateUtil.dateFormatMis(dateStr), "yyyy-MM-dd HH:mm");
    }

    //处理关键字
    public static SearchCondition buildSearchKeyWord(SearchCondition condition, String keyWord) {
        if (TextUtils.isEmpty(keyWord)) {
            return condition;
        }
        if (keyWord.trim().length() == 0) {
            return condition;
        }
        keyWord = keyWord.replaceAll("？", "?");//替换中文？
        condition.setIword(keyWord);
        return condition;
    }

    public static String dealAlarmStatus(boolean isHandler) {
        return isHandler ? "已处理" : "未处理";
    }

    public static String formatNull(String src) {
        if (src == null || TextUtils.isEmpty(src) || src.equalsIgnoreCase("null")) {
            return "";
        }
        return src;
    }

    public static String contactDate(String startDate, String endDate) {
        return startDate + "至" + endDate;
    }

    public static String contactTime(String startTime, String endTime) {
        return startTime + "~" + endTime;
    }

    public static String dealDispositionStatus(boolean isDisabled) {
        return isDisabled ? "已撤控" : "已布控";
    }
}
