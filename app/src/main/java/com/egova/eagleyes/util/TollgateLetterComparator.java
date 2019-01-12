package com.egova.eagleyes.util;

import com.egova.eagleyes.model.respose.Tollgate;

import java.util.Comparator;

/**
 * 卡口--专用于按首字母排序
 */
public class TollgateLetterComparator implements Comparator<Tollgate> {

    @Override
    public int compare(Tollgate o1, Tollgate o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        String lhsSortLetters = o1.getFirstLetter().substring(0, 1).toUpperCase();
        String rhsSortLetters = o2.getFirstLetter().substring(0, 1).toUpperCase();
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}
