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

    /**
     * Starts the Goofy chatbot application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        showWelcome();

        ArrayList<Task> tasks = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println();
            String input = in.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
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
                System.out.println(INDENT + LINE);
                System.out.println(INDENT + "I don't understand that command!");
                System.out.println(INDENT + LINE);
            }
        }

        showGoodbye();
        in.close();
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
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            System.out.println(INDENT + LINE);
            System.out.println(INDENT + "The description of a todo cannot be empty.");
            System.out.println(INDENT + LINE);
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
        String details = input.substring(9).trim();
        if (!details.contains("/by")) {
            System.out.println(INDENT + LINE);
            System.out.println(INDENT + "Please use the format: deadline <description> /by <date>");
            System.out.println(INDENT + LINE);
            return;
        }
        String[] parts = details.split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty() || by.isEmpty()) {
            System.out.println(INDENT + LINE);
            System.out.println(INDENT + "The description and deadline cannot be empty.");
            System.out.println(INDENT + LINE);
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
        String details = input.substring(6).trim();
        if (!details.contains("/from") || !details.contains("/to")) {
            System.out.println(INDENT + LINE);
            System.out.println(INDENT + "Please use the format: event <description> /from <start> /to <end>");
            System.out.println(INDENT + LINE);
            return;
        }
        String[] parts = details.split("/from", 2);
        String description = parts[0].trim();
        String[] timeParts = parts[1].split("/to", 2);
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            System.out.println(INDENT + LINE);
            System.out.println(INDENT + "The description, start time, and end time cannot be empty.");
            System.out.println(INDENT + LINE);
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
        String taskWord = (totalTasks == 1) ? "task"  : "tasks";
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
            int taskNumber = Integer.parseInt(input.substring(5).trim());
            if (taskNumber > 0 && taskNumber <= tasks.size()) {
                tasks.get(taskNumber - 1).markAsDone();
                System.out.println(INDENT + LINE);
                System.out.println(INDENT + "Nice! I've marked this task as done:");
                System.out.println(INDENT + "  " + tasks.get(taskNumber - 1));
                System.out.println(INDENT + LINE);
            } else {
                System.out.println(INDENT + LINE);
                System.out.println(INDENT + "Invalid task number!");
                System.out.println(INDENT + LINE);
            }
        } catch (NumberFormatException e) {
            System.out.println(INDENT + LINE);
            System.out.println(INDENT + "Please provide a valid task number!");
            System.out.println(INDENT + LINE);
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
            int taskNumber = Integer.parseInt(input.substring(7).trim());
            if (taskNumber > 0 && taskNumber <= tasks.size()) {
                tasks.get(taskNumber - 1).markAsNotDone();
                System.out.println(INDENT + LINE);
                System.out.println(INDENT + "OK, I've marked this task as not done yet:");
                System.out.println(INDENT + "  " + tasks.get(taskNumber - 1));
                System.out.println(INDENT + LINE);
            } else {
                System.out.println(INDENT + LINE);
                System.out.println(INDENT + "Invalid task number!");
                System.out.println(INDENT + LINE);
            }
        } catch (NumberFormatException e) {
            System.out.println(INDENT + LINE);
            System.out.println(INDENT + "Please provide a valid task number!");
            System.out.println(INDENT + LINE);
        }
    }
}
