package goofy.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end date or time.
 * If the from/to values are in yyyy-MM-dd format, they are stored as LocalDate
 * and displayed in MMM dd yyyy format. Otherwise, they are stored as plain strings.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private LocalDate fromDate;
    private LocalDate toDate;
    private String fromString;
    private String toString;

    /**
     * Creates a new Event task with the given description, start and end values.
     * Attempts to parse from/to as dates in yyyy-MM-dd format.
     * Falls back to storing them as plain strings if parsing fails.
     *
     * @param description Description of the event task.
     * @param from Start date or time string, ideally in yyyy-MM-dd format.
     * @param to End date or time string, ideally in yyyy-MM-dd format.
     */
    public Event(String description, String from, String to) {
        super(description);
        try {
            this.fromDate = LocalDate.parse(from, INPUT_FORMAT);
            this.fromString = null;
        } catch (DateTimeParseException e) {
            this.fromDate = null;
            this.fromString = from;
        }
        try {
            this.toDate = LocalDate.parse(to, INPUT_FORMAT);
            this.toString = null;
        } catch (DateTimeParseException e) {
            this.toDate = null;
            this.toString = to;
        }
    }

    /**
     * Returns the start date as a LocalDate, or null if stored as a plain string.
     *
     * @return LocalDate of the start date, or null.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * Returns the end date as a LocalDate, or null if stored as a plain string.
     *
     * @return LocalDate of the end date, or null.
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * Returns the start value in its original storage format.
     * Returns yyyy-MM-dd if stored as a date, otherwise returns the plain string.
     *
     * @return Start string for file storage.
     */
    public String getFrom() {
        if (fromDate != null) {
            return fromDate.format(INPUT_FORMAT);
        }
        return fromString;
    }

    /**
     * Returns the end value in its original storage format.
     * Returns yyyy-MM-dd if stored as a date, otherwise returns the plain string.
     *
     * @return End string for file storage.
     */
    public String getTo() {
        if (toDate != null) {
            return toDate.format(INPUT_FORMAT);
        }
        return toString;
    }

    /**
     * Returns the string representation of the event task.
     * Format: [E][status] description (from: MMM dd yyyy to: MMM dd yyyy)
     * or with plain strings if dates could not be parsed.
     *
     * @return String representation of the event task.
     */
    @Override
    public String toString() {
        String fromDisplay = (fromDate != null) ? fromDate.format(OUTPUT_FORMAT) : fromString;
        String toDisplay = (toDate != null) ? toDate.format(OUTPUT_FORMAT) : toString;
        return "[E]" + super.toString() + " (from: " + fromDisplay + " to: " + toDisplay + ")";
    }
}