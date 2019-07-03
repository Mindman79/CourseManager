package com.tristankirkham.coursemanager.utilities;

import com.tristankirkham.coursemanager.database.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {

    //Testing constants
    private static final String SAMPLE_TEXT_1 = "Sample text 1";
    private static final String SAMPLE_TEXT_2 = "Sample text 2";
    private static final String SAMPLE_TEXT_3 = "Sample text 3";


    //Get current system time
    private static Date getDate(int diff) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();


    }

    //Sample data for terms
    public static List<TermEntity> getTerms() {
        List<TermEntity> terms = new ArrayList<>();
        terms.add(new TermEntity(1, "Spring Term", getDate(0), getDate(1) ));
        terms.add(new TermEntity(2, "Summer Term",getDate(2), getDate(3) ));
        terms.add(new TermEntity(3, "Fall Term", getDate(4), getDate(5) ));
        return terms;
    }

}
