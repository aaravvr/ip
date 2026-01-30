import java.util.Scanner;

public class Goofy {
    private static final int MAX_TASKS = 100;
    private static final String LINE = "____________________________________________________________";
    private static final String INDENT = "    ";
    private static final String LOGO = "  ____              __       \n"
            + " / ___| ___   ___  / _|_   _ \n"
            + "| |  _ / _ \\ / _ \\| |_| | | |\n"
            + "| |_| | (_) | (_) |  _| |_| |\n"
            + " \\____|\\___/ \\___/|_|  \\__, |\n"
            + "                       |___/ \n";

    public static void main(String[] args) {
        showWelcome();

        Task[] tasks = new Task[MAX_TASKS];
        int taskCounter = 0;
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println();
            String input = in.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                showTaskList(tasks, taskCounter);
            } else if (input.startsWith("mark ")) {
                taskCounter = markTaskAsDone(tasks, taskCounter, input);
            } else if (input.startsWith("unmark ")) {
                taskCounter = unmarkTask(tasks, taskCounter, input);
            } else {
                taskCounter = addTask(tasks, taskCounter, input);
            }
        }

        showGoodbye();
        in.close();
    }

    /**
     * Displays the welcome message.
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
     * Displays the list of tasks.
     *
     * @param tasks Array of tasks.
     * @param taskCounter Number of tasks in the list.
     */
    private static void showTaskList(Task[] tasks, int taskCounter) {
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < taskCounter; i++) {
            System.out.println(INDENT + (i + 1) + ". " + tasks[i]);
        }
        System.out.println(INDENT + LINE);
    }

    /**
     * Adds a new task to the task list.
     *
     * @param tasks Array of tasks.
     * @param taskCounter Number of tasks in the list.
     * @param description Description of the task.
     * @return Updated task counter.
     */
    private static int addTask(Task[] tasks, int taskCounter, String description) {
        tasks[taskCounter] = new Task(description);
        taskCounter++;
        System.out.println(INDENT + LINE);
        System.out.println(INDENT + "added: " + description);
        System.out.println(INDENT + LINE);
        return taskCounter;
    }

    /**
     * Marks a task as done.
     *
     * @param tasks Array of tasks.
     * @param taskCounter Number of tasks in the list.
     * @param input User input containing the task number.
     * @return Task counter (unchanged).
     */
    private static int markTaskAsDone(Task[] tasks, int taskCounter, String input) {
        try {
            int taskNumber = Integer.parseInt(input.substring(5).trim());
            if (taskNumber > 0 && taskNumber <= taskCounter) {
                tasks[taskNumber - 1].markAsDone();
                System.out.println(INDENT + LINE);
                System.out.println(INDENT + "Nice! I've marked this task as done:");
                System.out.println(INDENT + "  " + tasks[taskNumber - 1]);
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
        return taskCounter;
    }

    /**
     * Marks a task as not done.
     *
     * @param tasks Array of tasks.
     * @param taskCounter Number of tasks in the list.
     * @param input User input containing the task number.
     * @return Task counter (unchanged).
     */
    private static int unmarkTask(Task[] tasks, int taskCounter, String input) {
        try {
            int taskNumber = Integer.parseInt(input.substring(7).trim());
            if (taskNumber > 0 && taskNumber <= taskCounter) {
                tasks[taskNumber - 1].markAsNotDone();
                System.out.println(INDENT + LINE);
                System.out.println(INDENT + "OK, I've marked this task as not done yet:");
                System.out.println(INDENT + "  " + tasks[taskNumber - 1]);
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
        return taskCounter;
    }
}