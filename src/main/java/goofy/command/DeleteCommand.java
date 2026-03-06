package goofy.command;

import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.task.Task;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskNumber;

    /**
     * Creates a new DeleteCommand for the given task number.
     *
     * @param taskNumber One-based task number to delete.
     */
    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the delete command by removing the specified task.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     * @throws GoofyException If the task number is out of range.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GoofyException {
        Task deletedTask = tasks.deleteTask(taskNumber);
        ui.showTaskDeleted(deletedTask, tasks.getSize());
        storage.saveTasks(tasks.getTasks());
    }
}