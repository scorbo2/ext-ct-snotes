package ca.corbett.crypttext.extensions.snotes.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.TextStyle;
import java.util.logging.Logger;

/**
 * A simple wrapper around dates in a hard-coded yyyy-MM-dd format.
 * All date values are represented in this format.
 * <p>
 * YMDDate instances are immutable, and always contain a valid date,
 * defaulting to today's date if given bad input.
 * </p>
 *
 * @author <a href="https://github.com/scorbo2">scorbo2</a>
 * @since Snotes 1.0
 */
public class YMDDate implements Comparable<YMDDate> {
    /**
     * Note: we have to use "uuuu" and ResolverStyle.STRICT here, because the default is a little too loose.
     * Without STRICT, the parser will accept dates that are technically invalid. Actual example: "2024-02-30".
     * Weirdly, using "yyyy" with ResolverStyle.STRICT rejects valid dates. Actual example: "2024-01-01".
     * Only the combination of both "uuuu" and STRICT seems to resolve dates correctly.
     */
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                                                                          .withResolverStyle(ResolverStyle.STRICT);
    private static final Logger log = Logger.getLogger(YMDDate.class.getName());
    protected LocalDate date;

    /**
     * Constructs a YMDDate representing today's date.
     */
    public YMDDate() {
        date = LocalDate.now();
    }

    /**
     * Attempts to construct a YMDDate from the given String in yyyy-MM-dd format.
     * If the String is badly formatted, a warning is logged, and today's date will be used instead.
     */
    public YMDDate(String ymdString) {
        try {
            date = ymdString == null ? LocalDate.now() : LocalDate.parse(ymdString, FORMATTER);
        }
        catch (Exception e) {
            log.warning("Invalid date string '" + ymdString + "': " + e.getMessage() + ". Using today's date instead.");
            date = LocalDate.now();
        }
    }

    /**
     * For unit testing purposes, to make it easier to create test objects.
     */
    protected YMDDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the day prior to this date.
     */
    public YMDDate getYesterday() {
        LocalDate yesterday = date.minusDays(1);
        return new YMDDate(yesterday.format(FORMATTER));
    }

    /**
     * Returns the day after this date.
     */
    public YMDDate getTomorrow() {
        LocalDate tomorrow = date.plusDays(1);
        return new YMDDate(tomorrow.format(FORMATTER));
    }

    /**
     * Returns the human-readable name of the day of the week for this date.
     * <p>
     * This value is NOT used for tagging purposes, but can be used when displaying
     * a dated Note to the user.
     * </p>
     */
    public String getDayName() {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, java.util.Locale.getDefault());
    }

    /**
     * Returns the year in string format (four digits always).
     */
    public String getYearStr() {
        return String.format("%04d", date.getYear());
    }

    /**
     * Returns the month in string format (two digits always).
     */
    public String getMonthStr() {
        return String.format("%02d", date.getMonthValue());
    }

    /**
     * Returns the day of month in string format (two digits always).
     */
    public String getDayStr() {
        return String.format("%02d", date.getDayOfMonth());
    }

    /**
     * Returns a yyyy-MM-dd formatted String representation of this date.
     */
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    /**
     * Reports whether the given String conforms to yyyy-MM-dd format.
     */
    public static boolean isValidYMD(String candidate) {
        try {
            LocalDate.parse(candidate, FORMATTER);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Compares this YMDDate to another.
     */
    @Override
    public int compareTo(YMDDate o) {
        if (o == null) {
            return 1;
        }
        return this.date.compareTo(o.date);
    }
}
