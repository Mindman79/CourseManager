package com.tristankirkham.coursemanager.utilities;

import java.text.SimpleDateFormat;

public class TextFormatter {

    public static String cardPattern = "MMM d yyyy";
    private static String fullPattern = "MM/dd/yyyy";
    public static SimpleDateFormat cardDateFormat = new SimpleDateFormat(cardPattern);
    public static SimpleDateFormat fullDateFormat = new SimpleDateFormat(fullPattern);

}
