package app.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateValidator {
    private DateValidator() {

    }

    /**
     * Checks if a date is valid.
     *
     * @param dateString The date to be checked.
     * @return True if the date is valid, false otherwise.
     */
    public static boolean isValidDate(final String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
