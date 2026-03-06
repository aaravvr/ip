package goofy.command;

import goofy.exception.GoofyException;
import goofy.storage.Storage;
import goofy.task.Task;
import goofy.task.Todo;
import goofy.ui.TaskList;
import goofy.ui.Ui;

/**
 * Represents a command to add a todo task to the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Creates a new AddTodoCommand with the given description.
     *
     * @param description Description of the todo task.
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the add todo command by adding a new todo task.
     *
     * @param tasks TaskList to operate on.
     * @param ui Ui instance for displaying results.
     * @param storage Storage instance for saving tasks.
     * @throws GoofyException If there is an error saving the task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GoofyException {
        Task task = new Todo(description);
        tasks.addTask(task);
        ui.showTaskAdded(task, tasks.getSize());
        storage.saveTasks(tasks.getTasks());
    }
}