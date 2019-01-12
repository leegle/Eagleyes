package com.egova.eagleyes.util;

import com.egova.eagleyes.model.respose.PersonInfo;

import java.util.Comparator;

public class PersonLetterComparator implements Comparator<PersonInfo> {

    @Override
    public int compare(PersonInfo o1, PersonInfo o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        String lhsSortLetters = o1.getFirstLetter().substring(0, 1).toUpperCase();
        String rhsSortLetters = o2.getFirstLetter().substring(0, 1).toUpperCase();
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}
