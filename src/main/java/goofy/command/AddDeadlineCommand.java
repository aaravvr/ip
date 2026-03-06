package goofy.command;

import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.task.Deadline;
import goofy.task.Task;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to add a deadline task to the task list.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    /**
     * Creates a new AddDeadlineCommand with the given description and deadline.
     *
     * @param description Description of the deadline task.
     * @param by Deadline date or time.
     */
    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Executes the add deadline command by adding a new deadline task.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     * @throws GoofyException If there is an error saving the task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GoofyException {
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        storage.saveTasks(tasks.getTasks());
    }
}