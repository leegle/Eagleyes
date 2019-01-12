package com.egova.eagleyes.util;

import com.egova.baselibrary.model.NameTextValue;

import java.util.Comparator;

public class NameTextLetterComparator implements Comparator<NameTextValue<String>> {

    @Override
    public int compare(NameTextValue<String> o1, NameTextValue<String> o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        String lhsSortLetters = o1.getFirstLetter().substring(0, 1).toUpperCase();
        String rhsSortLetters = o2.getFirstLetter().substring(0, 1).toUpperCase();
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}
