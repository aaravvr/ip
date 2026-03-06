package goofy.command;

import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int taskNumber;

    /**
     * Creates a new MarkCommand for the given task number.
     *
     * @param taskNumber One-based task number to mark as done.
     */
    public MarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the mark command by marking the specified task as done.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     * @throws GoofyException If the task number is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GoofyException {
        tasks.markTask(taskNumber);
        ui.showTaskMarkedAsDone(tasks.getTask(taskNumber - 1));
        storage.saveTasks(tasks.getTasks());
    }
}