package goofy.ui;

import java.util.ArrayList;

import goofy.exception.GoofyException;
import goofy.task.Task;

/**
 * Manages the list of tasks in the Goofy application.
 * Responsible for adding, retrieving, and updating tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Zero-based index of the task.
     * @return Task at the given index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the full list of tasks.
     *
     * @return ArrayList of all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Total number of tasks.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Marks the task at the given one-based task number as done.
     *
     * @param taskNumber One-based task number.
     * @throws GoofyException If the task number is out of range.
     */
    public void markTask(int taskNumber) throws GoofyException {
        if (!isValidTaskNumber(taskNumber)) {
            throw new GoofyException("Task number " + taskNumber + "?! That ain't on the list! "
                    + "I only see " + tasks.size() + " task(s) in there, a-hyuck!");
        }
        tasks.get(taskNumber - 1).markAsDone();
    }

    /**
     * Marks the task at the given one-based task number as not done.
     *
     * @param taskNumber One-based task number.
     * @throws GoofyException If the task number is out of range.
     */
    public void unmarkTask(int taskNumber) throws GoofyException {
        if (!isValidTaskNumber(taskNumber)) {
            throw new GoofyException("Task number " + taskNumber + "?! That ain't on the list! "
                    + "I only see " + tasks.size() + " task(s) in there, a-hyuck!");
        }
        tasks.get(taskNumber - 1).markAsNotDone();
    }

    /**
     * Deletes the task at the given one-based task number.
     *
     * @param taskNumber One-based task number.
     * @return The deleted task.
     * @throws GoofyException If the task number is out of range.
     */
    public Task deleteTask(int taskNumber) throws GoofyException {
        if (!isValidTaskNumber(taskNumber)) {
            throw new GoofyException("Task number " + taskNumber + "?! That ain't on the list! "
                    + "I only see " + tasks.size() + " task(s) in there, a-hyuck!");
        }
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Checks if a one-based task number is within the valid range.
     *
     * @param taskNumber One-based task number to check.
     * @return True if valid, false otherwise.
     */
    private boolean isValidTaskNumber(int taskNumber) {
        return taskNumber > 0 && taskNumber <= tasks.size();
    }
}