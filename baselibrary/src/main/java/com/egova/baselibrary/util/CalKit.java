package com.egova.baselibrary.util;

import java.math.BigDecimal;

public class CalKit {
    public static final int defaultScale = 2;

    /**
     * 两个数相加，四舍五入保留2位小数
     *
     * @param sum1
     * @param sum2
     * @return
     */
    public static BigDecimal add(String sum1, String sum2) {
        return add(sum1, sum2, defaultScale);
    }

    public static BigDecimal add(String sum1, String sum2, int scale) {
        BigDecimal b1 = new BigDecimal(sum1);
        BigDecimal b2 = new BigDecimal(sum2);
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);//四舍五入
    }

    /**
     * 两个数相减，四舍五入保留2位小数
     *
     * @param sum1
     * @param sum2
     * @return
     */
    public static BigDecimal subtract(String sum1, String sum2) {
        return subtract(sum1, sum2, defaultScale);//四舍五入
    }

    public static BigDecimal subtract(String sum1, String sum2, int scale) {
        BigDecimal b1 = new BigDecimal(sum1);
        BigDecimal b2 = new BigDecimal(sum2);
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);//四舍五入
    }

    /**
     * 两个数相乘，四舍五入保留2位小数
     *
     * @param sum1
     * @param sum2
     * @return
     */
    public static BigDecimal multiply(String sum1, String sum2) {
        return multiply(sum1, sum2, defaultScale);
    }

    public static BigDecimal multiply(String sum1, String sum2, int scale) {
        BigDecimal b1 = new BigDecimal(sum1);
        BigDecimal b2 = new BigDecimal(sum2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);//四舍五入
    }

    /**
     * 两个数相除，除数如果为0,则返回0,，四舍五入保留2位小数
     *
     * @param sum1
     * @param sum2
     * @return
     */
    public static BigDecimal divide(String sum1, String sum2) {
        return divide(sum1, sum2, defaultScale);//四舍五入
    }

    public static BigDecimal divide(String sum1, String sum2, int scale) {
        BigDecimal zero = new BigDecimal("0");
        BigDecimal b1 = new BigDecimal(sum1);
        BigDecimal b2 = new BigDecimal(sum2);

        if (b2.compareTo(zero) == 0) {
            //如果除数为0，则直接返回0
            return zero;
        }
        return b1.divide(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);//四舍五入
    }
}
