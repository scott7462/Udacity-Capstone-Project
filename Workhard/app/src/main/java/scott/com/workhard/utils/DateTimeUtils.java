package scott.com.workhard.utils;

import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Julian Cardona on 4/14/15.
 */
public class DateTimeUtils {

    public static DateTimeFormatter presenter;
    public static DateTimeFormatter formatter;

    public static String convertToPatternFromPattern(String fromPattern, String date, String toPattern) {
        return getStringPatternFromDateTime(toPattern, getDateTimeFromPattern(fromPattern, date));
    }

    public static MutableDateTime getDateTimeFromPattern(String inputPattern, String date) {
        formatter = DateTimeFormat.forPattern(inputPattern);
        return formatter.parseMutableDateTime(date);
    }

    public static String getStringPatternFromDateTime(String pattern, MutableDateTime date) {
        presenter = DateTimeFormat.forPattern(pattern);
        return presenter.print(date);
    }

}
