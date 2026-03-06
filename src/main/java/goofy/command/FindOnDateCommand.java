package goofy.command;

import java.time.LocalDate;
import java.util.ArrayList;

import goofy.storage.Storage;
import goofy.task.Deadline;
import goofy.task.Event;
import goofy.task.Task;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to find all tasks occurring on a specific date.
 * Matches deadlines due on the date and events whose date range includes the date.
 */
public class FindOnDateCommand extends Command {
    private final LocalDate date;

    /**
     * Creates a new FindOnDateCommand for the given date.
     *
     * @param date The date to search for tasks on.
     */
    public FindOnDateCommand(LocalDate date) {
        this.date = date;
    }

    /**
     * Executes the find-on-date command by collecting and displaying matching tasks.
     *
     * @param tasks TaskList to search.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance (not used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (isOnDate(task)) {
                matchingTasks.add(task);
            }
        }
        ui.showTasksOnDate(matchingTasks, date);
    }

    /**
     * Returns whether the given task falls on the target date.
     * For deadlines, checks if the due date matches.
     * For events, checks if the date falls within the from-to range (inclusive).
     *
     * @param task Task to check.
     * @return True if the task falls on the target date, false otherwise.
     */
    private boolean isOnDate(Task task) {
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return deadline.getByDate() != null && deadline.getByDate().equals(date);
        } else if (task instanceof Event) {
            Event event = (Event) task;
            if (event.getFromDate() == null || event.getToDate() == null) {
                return false;
            }
            return !date.isBefore(event.getFromDate()) && !date.isAfter(event.getToDate());
        }
        return false;
    }
}