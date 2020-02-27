import tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

/*
Deals with interactions with the user
 */
public class UI {
    private static Scanner sc = new Scanner(System.in);

    public UI() {
        printGreeting();
    }

    protected static void printGreeting() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
    }

    protected static void printHelp() {
        String helpMsg = "Here is a list of things you can do: \n"
                + "\ttodo:      tasks without a date/time (syntax: todo buy food)\n"
                + "\tlist:      list your current tasks (syntax: list)\n"
                + "\tdeadline:  tasks that need to be done by a date/time (syntax: deadline buy food /by Sunday)\n"
                + "\tevent:     tasks that start/end by a specific time (syntax: event food fair /at Mon 2-4pm)\n"
                + "\tdone x:    mark the xth task as done (syntax: done 3)\n"
                + "\tdelete x:  remove the xth task (syntax: delete 3)\n"
                + "\thelp:      launch the help screen (syntax: help)";
        System.out.println(helpMsg);
    }

    protected static String getUserCommand() {
        System.out.println("==========================");
        System.out.println("How can I help you?");
        return sc.nextLine();
    }



}
