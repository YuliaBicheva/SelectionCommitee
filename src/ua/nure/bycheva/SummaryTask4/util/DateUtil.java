package ua.nure.bycheva.SummaryTask4.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yulia on 30.08.16.
 */
public abstract class DateUtil {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    public static String toString(Date date){
        return date == null ? "" : DATE_FORMATTER.format(date);
    }
}
