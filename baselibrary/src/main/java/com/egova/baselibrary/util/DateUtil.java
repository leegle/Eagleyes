package com.egova.baselibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_START = "yyyy-MM-dd 00:00:00";
    public static final String DATE_END = "yyyy-MM-dd 23:59:59";


    public static int getTodayYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }

    public static int getTodayMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        return month;
    }

    public static int getTodayDay() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static Date getTodayStart(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_START);
        String dateStr = formatter.format(date);
        return dateFormatMis(dateStr);
    }

    public static Date getTodayEnd(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_END);
        String dateStr = formatter.format(date);
        return dateFormatMis(dateStr);
    }


    public static String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String dateFormatMis(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date dateFormat(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //将字符串时间 yyyy-MM-dd HH:mm:ss转为 date类型
    public static Date dateFormatMis(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    //将字符串时间 yyyy-MM-dd HH:mm:ss转为 yyyy-MM-dd
    public static String convertMisToDay(String dateStr) {
        Date date = dateFormatMis(dateStr);
        return dateFormat(date);
    }

    //将字符串yyyyMMddHHmmss 转为 yyyy-MM-dd HH:mm:ss
    public static String convertMisToStandard(String timeStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = formatter.parse(timeStr);
            return dateFormatMis(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }


    //判断是否是同一天
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 判断是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(String date1, String date2) {
        return isSameDate(dateFormatMis(date1), dateFormatMis(date2));
    }

    /**
     * 判断是否同一年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        return isSameYear;
    }

    public static boolean isSameYear(String date1, String date2) {
        return isSameYear(dateFormat(date1), dateFormat(date2));
    }

    /**
     * 判断是否同一年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        return isSameMonth;
    }

    public static boolean isSameMonth(String date1, String date2) {
        return isSameMonth(dateFormat(date1), dateFormat(date2));
    }


    public static Date getDateAddDays(int days) {
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, days);
        return cal1.getTime();
    }

    public static Date getDateAddDays(Date startDate,int days) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        cal1.add(Calendar.DAY_OF_YEAR, days);
        return cal1.getTime();
    }

    /**
     * 判断是否同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameWeek = isSameYear && cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        return isSameWeek;
    }

    public static boolean isSameWeek(String date1, String date2) {
        return isSameWeek(dateFormat(date1), dateFormat(date2));
    }


    /**
     * 获得当月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(cal.getTime());
    }

    /**
     * 获得当月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(cal.getTime());
    }

    /**
     * 获得日期属于当年的第几周
     *
     * @param dateStr
     * @return
     */
    public static int getWeekOfYear(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = null;
        try {
            Date date = format.parse(dateStr);
            calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 通过时间秒毫秒数判断两个时间的间隔天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    /**
     * 比较两个日期
     *
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int compareDate(String dateStr1, String dateStr2) {
        return compareDate(dateStr1, dateStr2, DATE_FORMAT);
    }

    public static int compareDate(String dateStr1, String dateStr2, String formatPatter) {
        SimpleDateFormat format = new SimpleDateFormat(formatPatter);
        try {
            Date date1 = format.parse(dateStr1);
            Date date2 = format.parse(dateStr2);
            if (date1.getTime() < date2.getTime()) {
                return -1;
            } else if (date1.getTime() > date2.getTime()) {
                return 1;
            } else {
                return 0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
