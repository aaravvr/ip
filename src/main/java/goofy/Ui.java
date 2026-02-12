package goofy;

import java.util.ArrayList;
import java.util.Scanner;

import goofy.task.Task;

/**
 * Handles all user interface interactions for the Goofy application.
 * Responsible for reading user input and displaying messages to the user.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________"
            + "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String LOGO = "  ____              __       \n"
            + " / ___| ___   ___  / _|_   _ \n"
            + "| |  _ / _ \\ / _ \\| |_| | | |\n"
            + "| |_| | (_) | (_) |  _| |_| |\n"
            + " \\____|\\___/ \\___/|_|  \\__, |\n"
            + "                       |___/ \n";

    private final Scanner scanner;

    /**
     * Creates a new Ui instance with a scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of user input.
     *
     * @return Trimmed user input string.
     */
    public String readCommand() {
        System.out.println();
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner.
     */
    public void closeScanner() {
        scanner.close();
    }

    /**
     * Displays the welcome message with the Goofy logo.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.print(" Gawrsh! It's me, \n" + LOGO
                + " Your not-so-average task buddy! Whatcha need today?\n");
        System.out.println(LINE);
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "A-hyuck! See ya later! Don't forget your tasks now!");
        System.out.print(INDENT + LINE);
    }

    /**
     * Displays an error message.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Gawrsh, something went wrong! " + message);
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays the list of all tasks with their completion status.
     *
     * @param tasks List of tasks to display.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println(INDENT + LINE);
        if (tasks.isEmpty()) {
            System.out.println(INDENT + "A-hyuck! Your list is emptier than my brain! "
                    + "Go add some tasks!");
        } else {
            System.out.println(INDENT + "Hold on, lemme squint at your list real hard...");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(INDENT + (i + 1) + ". " + tasks.get(i));
            }
        }
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays the message when a task is added.
     *
     * @param task Task that was added.
     * @param totalTasks Total number of tasks in the list.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Okie dokie! I went ahead and scribbled that down:");
        System.out.println(INDENT + "  " + task);
        String taskWord = (totalTasks == 1) ? "task" : "tasks";
        System.out.println(INDENT + "Ya got " + totalTasks + " " + taskWord
                + " now. Don't let 'em pile up like my laundry!");
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays the message when a task is marked as done.
     *
     * @param task Task that was marked as done.
     */
    public void showTaskMarkedAsDone(Task task) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "A-hyuck! You actually did it! "
                + "I'm so proud I could trip over myself!");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays the message when a task is marked as not done.
     *
     * @param task Task that was marked as not done.
     */
    public void showTaskMarkedAsNotDone(Task task) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Oopsie daisy! Un-done it is! Back to the to-do pile it goes:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + LINE);
    }
}