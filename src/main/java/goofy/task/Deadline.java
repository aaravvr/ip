package goofy.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 * If the deadline is in yyyy-MM-dd format, it is stored as a LocalDate
 * and displayed in MMM dd yyyy format. Otherwise, it is stored as a plain string.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private LocalDate byDate;
    private String byString;

    /**
     * Creates a new Deadline task with the given description and deadline.
     * Attempts to parse the deadline as a date in yyyy-MM-dd format.
     * Falls back to storing it as a plain string if parsing fails.
     *
     * @param description Description of the deadline task.
     * @param by Deadline string, ideally in yyyy-MM-dd format.
     */
    public Deadline(String description, String by) {
        super(description);
        try {
            this.byDate = LocalDate.parse(by, INPUT_FORMAT);
            this.byString = null;
        } catch (DateTimeParseException e) {
            this.byDate = null;
            this.byString = by;
        }
    }

    /**
     * Returns the deadline as a LocalDate, or null if stored as a plain string.
     *
     * @return LocalDate of the deadline, or null.
     */
    public LocalDate getByDate() {
        return byDate;
    }

    /**
     * Returns the deadline in its original storage format.
     * Returns yyyy-MM-dd if stored as a date, otherwise returns the plain string.
     *
     * @return Deadline string for file storage.
     */
    public String getBy() {
        if (byDate != null) {
            return byDate.format(INPUT_FORMAT);
        }
        return byString;
    }

    /**
     * Returns the string representation of the deadline task.
     * Format: [D][status] description (by: MMM dd yyyy) or (by: plain string)
     *
     * @return String representation of the deadline task.
     */
    @Override
    public String toString() {
        String byDisplay = (byDate != null) ? byDate.format(OUTPUT_FORMAT) : byString;
        return "[D]" + super.toString() + " (by: " + byDisplay + ")";
    }
}