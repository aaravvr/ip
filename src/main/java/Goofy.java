import java.util.Scanner;
public class Goofy {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String indent = "    ";
        String logo = "  ____              __       \n"
                + " / ___| ___   ___  / _|_   _ \n"
                + "| |  _ / _ \\ / _ \\| |_| | | |\n"
                + "| |_| | (_) | (_) |  _| |_| |\n"
                + " \\____|\\___/ \\___/|_|  \\__, |\n"
                + "                       |___/ \n";
        System.out.println(line);
        System.out.print(" Hello! I'm \n" + logo + " What can I do for you?\n");
        System.out.println(line);

        String[] tasks = new String[100];
        int taskCounter = 0;
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println();
            String input = in.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(indent + line);
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println(indent + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(indent + line);
            } else {
                tasks[taskCounter] = input;
                taskCounter++;
                System.out.println(indent + line);
                System.out.println(indent + "added: " + input);
                System.out.println(indent + line);
            }
        }
        System.out.println(indent + line);
        System.out.println(indent + "Bye. Hope to see you again soon!");
        System.out.print(indent + line);
    }
}