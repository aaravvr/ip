package goofy.command;

import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskNumber;

    /**
     * Creates a new UnmarkCommand for the given task number.
     *
     * @param taskNumber One-based task number to mark as not done.
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the unmark command by marking the specified task as not done.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     * @throws GoofyException If the task number is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GoofyException {
        tasks.unmarkTask(taskNumber);
        ui.showTaskMarkedAsNotDone(tasks.getTask(taskNumber - 1));
        storage.saveTasks(tasks.getTasks());
    }
}