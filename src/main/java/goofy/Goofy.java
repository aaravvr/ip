package goofy;

import java.util.ArrayList;
import java.util.Scanner;

import goofy.task.Deadline;
import goofy.task.Event;
import goofy.task.Task;
import goofy.task.Todo;

/**
 * Represents the main chatbot application that manages tasks.
 * Goofy can add tasks, list tasks, mark tasks as done, and unmark tasks.
 */
public class Goofy {
    private static final String LINE = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String LOGO = "  ____              __       \n"
            + " / ___| ___   ___  / _|_   _ \n"
            + "| |  _ / _ \\ / _ \\| |_| | | |\n"
            + "| |_| | (_) | (_) |  _| |_| |\n"
            + " \\____|\\___/ \\___/|_|  \\__, |\n"
            + "                       |___/ \n";

    private static final int TODO_COMMAND_LENGTH = 5;
    private static final int DEADLINE_COMMAND_LENGTH = 9;
    private static final int EVENT_COMMAND_LENGTH = 6;
    private static final int MARK_COMMAND_LENGTH = 5;
    private static final int UNMARK_COMMAND_LENGTH = 7;

    /**
     * Starts the Goofy chatbot application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        showWelcome();
        runCommandLoop();
        showGoodbye();
    }

    /**
     * Runs the main command loop that processes user input.
     */
    private static void runCommandLoop() {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println();
            String input = in.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            processCommand(tasks, input);
        }

        in.close();
    }

    /**
     * Processes a single command from the user.
     *
     * @param tasks List of tasks.
     * @param input User input command.
     */
    private static void processCommand(ArrayList<Task> tasks, String input) {
        if (input.equalsIgnoreCase("list")) {
            showTaskList(tasks);
        } else if (input.startsWith("mark ")) {
            markTaskAsDone(tasks, input);
        } else if (input.startsWith("unmark ")) {
            unmarkTask(tasks, input);
        } else if (input.startsWith("todo ")) {
            addTodoTask(tasks, input);
        } else if (input.startsWith("deadline ")) {
            addDeadlineTask(tasks, input);
        } else if (input.startsWith("event ")) {
            addEventTask(tasks, input);
        } else {
            showUnknownCommandMessage();
        }
    }

    /**
     * Displays the welcome message with the Goofy logo.
     */
    private static void showWelcome() {
        System.out.println(LINE);
        System.out.print(" Hello! I'm \n" + LOGO + " What can I do for you?\n");
        System.out.println(LINE);
    }

    /**
     * Displays the goodbye message.
     */
    private static void showGoodbye() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        System.out.print(INDENT + LINE);
    }

    /**
     * Displays an unknown command error message.
     */
    private static void showUnknownCommandMessage() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "I don't understand that command!");
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays the list of all tasks with their completion status.
     *
     * @param tasks List of tasks.
     */
    private static void showTaskList(ArrayList<Task> tasks) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(INDENT + (i + 1) + ". " + tasks.get(i));
        }
        System.out.println(INDENT + LINE);
    }

    /**
     * Adds a new todo task to the task list.
     *
     * @param tasks List of tasks.
     * @param input User input containing the todo description.
     */
    private static void addTodoTask(ArrayList<Task> tasks, String input) {
        String description = input.substring(TODO_COMMAND_LENGTH).trim();
        if (description.isEmpty()) {
            showEmptyDescriptionError("todo");
            return;
        }
        Task task = new Todo(description);
        tasks.add(task);
        printTaskAdded(task, tasks.size());
    }

    /**
     * Adds a new deadline task to the task list.
     *
     * @param tasks List of tasks.
     * @param input User input containing the deadline description and due date.
     */
    private static void addDeadlineTask(ArrayList<Task> tasks, String input) {
        String details = input.substring(DEADLINE_COMMAND_LENGTH).trim();
        if (!details.contains("/by")) {
            showFormatError("deadline <description> /by <date>");
            return;
        }

        String[] parts = details.split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            showEmptyFieldsError("description and deadline");
            return;
        }

        Task task = new Deadline(description, by);
        tasks.add(task);
        printTaskAdded(task, tasks.size());
    }

    /**
     * Adds a new event task to the task list.
     *
     * @param tasks List of tasks.
     * @param input User input containing the event description, start and end time.
     */
    private static void addEventTask(ArrayList<Task> tasks, String input) {
        String details = input.substring(EVENT_COMMAND_LENGTH).trim();
        if (!details.contains("/from") || !details.contains("/to")) {
            showFormatError("event <description> /from <start> /to <end>");
            return;
        }

        String[] parts = details.split("/from", 2);
        String description = parts[0].trim();
        String[] timeParts = parts[1].split("/to", 2);
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            showEmptyFieldsError("description, start time, and end time");
            return;
        }

        Task task = new Event(description, from, to);
        tasks.add(task);
        printTaskAdded(task, tasks.size());
    }

    /**
     * Prints the message when a task is added.
     *
     * @param task Task that was added.
     * @param totalTasks Total number of tasks in the list.
     */
    private static void printTaskAdded(Task task, int totalTasks) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task);
        String taskWord = (totalTasks == 1) ? "task" : "tasks";
        System.out.println(INDENT + "Now you have " + totalTasks + " " + taskWord + " in the list.");
        System.out.println(INDENT + LINE);
    }

    /**
     * Marks a task as done based on the user input.
     *
     * @param tasks List of tasks.
     * @param input User input containing the task number to mark.
     */
    private static void markTaskAsDone(ArrayList<Task> tasks, String input) {
        try {
            int taskNumber = parseTaskNumber(input, MARK_COMMAND_LENGTH);
            if (!isValidTaskNumber(taskNumber, tasks.size())) {
                showInvalidTaskNumberError();
                return;
            }

            Task task = tasks.get(taskNumber - 1);
            task.markAsDone();
            showTaskMarkedAsDone(task);
        } catch (NumberFormatException e) {
            showInvalidTaskNumberFormatError();
        }
    }

    /**
     * Marks a task as not done based on the user input.
     *
     * @param tasks List of tasks.
     * @param input User input containing the task number to unmark.
     */
    private static void unmarkTask(ArrayList<Task> tasks, String input) {
        try {
            int taskNumber = parseTaskNumber(input, UNMARK_COMMAND_LENGTH);
            if (!isValidTaskNumber(taskNumber, tasks.size())) {
                showInvalidTaskNumberError();
                return;
            }

            Task task = tasks.get(taskNumber - 1);
            task.markAsNotDone();
            showTaskMarkedAsNotDone(task);
        } catch (NumberFormatException e) {
            showInvalidTaskNumberFormatError();
        }
    }

    /**
     * Parses the task number from user input.
     *
     * @param input User input string.
     * @param commandLength Length of the command prefix.
     * @return Task number.
     * @throws NumberFormatException If the input is not a valid number.
     */
    private static int parseTaskNumber(String input, int commandLength) throws NumberFormatException {
        return Integer.parseInt(input.substring(commandLength).trim());
    }

    /**
     * Checks if a task number is valid.
     *
     * @param taskNumber Task number to check.
     * @param totalTasks Total number of tasks.
     * @return True if valid, false otherwise.
     */
    private static boolean isValidTaskNumber(int taskNumber, int totalTasks) {
        return taskNumber > 0 && taskNumber <= totalTasks;
    }

    /**
     * Displays the message when a task is marked as done.
     *
     * @param task Task that was marked as done.
     */
    private static void showTaskMarkedAsDone(Task task) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Nice! I've marked this task as done:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays the message when a task is marked as not done.
     *
     * @param task Task that was marked as not done.
     */
    private static void showTaskMarkedAsNotDone(Task task) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "OK, I've marked this task as not done yet:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays an empty description error message.
     *
     * @param taskType Type of task (todo, deadline, event).
     */
    private static void showEmptyDescriptionError(String taskType) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "The description of a " + taskType + " cannot be empty.");
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays a format error message.
     *
     * @param correctFormat The correct format string.
     */
    private static void showFormatError(String correctFormat) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Please use the format: " + correctFormat);
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays an empty fields error message.
     *
     * @param fields Fields that cannot be empty.
     */
    private static void showEmptyFieldsError(String fields) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "The " + fields + " cannot be empty.");
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays an invalid task number error message.
     */
    private static void showInvalidTaskNumberError() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Invalid task number!");
        System.out.println(INDENT + LINE);
    }

    /**
     * Displays an invalid task number format error message.
     */
    private static void showInvalidTaskNumberFormatError() {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Please provide a valid task number!");
        System.out.println(INDENT + LINE);
    }
}