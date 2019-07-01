package com.tristankirkham.coursemanager.utilities;

import com.tristankirkham.coursemanager.model.TermEntity;

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
        terms.add(new TermEntity(1, "Spring Term","1/1/2000", "1/2/2000" ));
        terms.add(new TermEntity(2, "Summer Term","2/1/2000", "2/2/2000" ));
        terms.add(new TermEntity(3, "Fall Term","3/1/2000", "3/2/2000" ));
        return terms;
    }

}
