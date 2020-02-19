import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

import exceptions.DukeException;
import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.TaskType;

// TODO: Extract existing error handling to exceptions.DukeException where appropriate
// TODO: Assess if there is a better way to implement task adding i.e. with splitString() or otherwise
// TODO: Check for further modularisation
// TODO: A general add to arrayList method? reduce LOC

public class Duke {
    /** Helper Functions Start **/
    // TODO: Check if this method will help, may be unhelpful
    private static String[] splitString(String[] strArr, String userCmd, String splitCase){
        strArr = userCmd.split(splitCase);
        return strArr;
    }

    public static void printHelp() {
        String helpMsg = "Here is a list of things you can do: \n"
                + "\ttodo:      tasks without a date/time (syntax: todo buy food)\n"
                + "\tlist:      list your current tasks (syntax: list)\n"
                + "\tdeadline:  tasks that need to be done by a date/time (syntax: deadline buy food /by Sunday)\n"
                + "\tevent:     tasks that start/end by a specific time (syntax: event food fair /at Mon 2-4pm)\n"
                + "\tdone x:    mark the xth task as done (syntax: done 3)\n"
                + "\thelp:      launch the help screen (syntax: help)";
        System.out.println(helpMsg);
    }

    /** Duke Functions Start**/
    private static void listTasks(ArrayList<Task> taskArrList) {
        if (taskArrList.size() == 0) {
            System.out.println("List is empty!");
        }
        else {
            System.out.println("Current task list: ");
            int index = 1;
            for (Task t : taskArrList) {
                System.out.println("\t" +index +". " +t.toString());
                index++;
            }
        }
    }

    // java.lang.NumberFormatException
    private static void markTaskDone(String userCmd, ArrayList<Task> taskArrList) throws DukeException {
        // syntax: done 2
        String[] splitCmd = userCmd.split(" ");
        int completedTask = 0;

        // error check syntax
        try {
            completedTask = Integer.parseInt(splitCmd[1]);
        }
        catch (NumberFormatException e){
            throw new DukeException("The number must be in number form. Syntax: done 3");
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new DukeException("There must be a task number provided to mark as done. Syntax: done 3");
        }

        if (completedTask > taskArrList.size() ) {
            System.out.println("Task not found!");
        }
        else {
            int completedIndex = completedTask-1;
            Task t = taskArrList.get(completedIndex);
            // todo: need to check if already completed?
            t.setDone();

            System.out.println("I've marked this task as done:");
            System.out.println("\t" +t.toString());
        }
    }

    // input: 2do<enter> error: java.lang.ArrayIndexOutOfBoundsException
    private static void addTask(String userCmd, ArrayList<Task> taskArrList) throws DukeException {
        String todoStr;
        try{
            String[] splitCmd = userCmd.split("todo ");
            todoStr = splitCmd[1].trim();
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new DukeException("There must be a description for a todo task. Syntax: todo buy food");
        }

        Task newTask = new Task(TaskType.TODO, todoStr);
        taskArrList.add(newTask);
        System.out.println("Got it. I've added this task: ");
        System.out.println("\t" +newTask.toString() );
    }

    // java.lang.ArrayIndexOutOfBoundsException
    private static void addDeadline(String userCmd, ArrayList<Task> taskArrList) throws DukeException {
        String taskStr, deadlineStr;
        // Syntax: deadline return book /by Sunday
        try {
            String[] splitCmd = userCmd.split("/by ");
            deadlineStr = splitCmd[1].trim();
            String[] splitName = splitCmd[0].split("deadline ");
            taskStr = splitName[1].trim();
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new DukeException("Wrong syntax for deadline tasks. Syntax: deadline buy food /by Sunday");
        }

        Deadline newDeadline = new Deadline(TaskType.DEADLINE, taskStr, deadlineStr);
        taskArrList.add(newDeadline);

        System.out.println("Got it. I've added this task: ");
        System.out.println("\t" +newDeadline.toString() );
    }

    // java.lang.ArrayIndexOutOfBoundsException
    private static void addEvent(String userCmd, ArrayList<Task> taskArrList) throws DukeException {
        // Syntax: event project meeting /at Mon 2-4pm
        String dateStr, eventStr;

        try {
            String[] splitCmd = userCmd.split("/at ");
            dateStr = splitCmd[1].trim();

            String[] splitName = splitCmd[0].split("event ");
            eventStr = splitName[1].trim();
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new DukeException("Wrong syntax for event tasks. Syntax: event food fair /at Mon 2-4pm");
        }

        Event newEvent = new Event(TaskType.EVENT, eventStr, dateStr);
        taskArrList.add(newEvent);
        System.out.println("Got it. I've added this event: ");
        System.out.println("\t" +newEvent.toString() );
    }

    private static void loadDuke() {
//        try{
//            File f = new File(FILEPATH);
//            Scanner sc = new Scanner(f);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }
    private static void saveDuke(ArrayList<Task> taskArrList) throws DukeException {
        String fileWriteData = "";

        for (Task t : taskArrList){
            int status;
            if (t.isDone()){
                status = 1;
            }
            else{
                status = 0;
            }

            switch (t.getCategory()){
            case 'T': //todos
                fileWriteData += t.getCategory() +"," +status +"," +t.getTaskName() +"\n";
            case 'D':
                fileWriteData += t.getCategory() +"," +status +"," +t.getTaskName() +"," +"\n";
            case 'E': //events

            }


        }

        try {
            Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
            String filePath = path.toString() +"/data/taskList.txt";

            FileWriter fw = new FileWriter("./src/main/java/duke/data/taskList.txt");
            fw.write("helloworld");
            fw.close();
        }
        catch (IOException e){
            throw new DukeException("File save error occurred.");
        }
    }

    private static void saveByStream(String filepath) throws DukeException {
        File file = new File(filepath);
        //write to file
//        try{
//            //Creates a file output stream to write to the file
//            FileOutputStream writeData = new FileOutputStream(file);
//            //handle the object to be written into the file that FileOutputStream created.
//            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
//
//            // write the people ArrayList into the file
//            writeStream.writeObject(people);
//            //flush the data in stream, writes to file
//            writeStream.flush();
//            // close stream, close resource
//            writeStream.close();
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void loadByStream(File file) throws DukeException {
        if(!file.exists()) {
            try {
                // creates all sub dir if not exist
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            catch (IOException e){
                throw new DukeException("File creation error");
            }
        }
//        else {
//            try{
//                //change to input, to read from file
//                FileInputStream readData = new FileInputStream(file);
//                ObjectInputStream readStream = new ObjectInputStream(readData);
//
//                //create a new arraylist for checking
//                //cast the value to arraylist person
//                ArrayList<Person> people2 = (ArrayList<Person>) readStream.readObject();
//                //close reading stream
//                readStream.close();
//                System.out.println(people2.toString());
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    /** Main method Start **/
    public static void main(String[] args) throws DukeException {
        Scanner sc = new Scanner(System.in);
        boolean continueRun = true;
        String userCmd = "";
        ArrayList<Task> taskArrList = new ArrayList<>();

        String dir = System.getProperty("user.dir");
        Path filepath = Paths.get(dir, "src","main", "java","data", "taskList.txt");
        String filepathStr = String.valueOf(filepath);
        File dukeFile = new File(filepathStr);
        loadByStream(dukeFile);
//        loadDuke(); TODO

        String logo = " ____        _        \n"
                    + "|  _ \\ _   _| | _____ \n"
                    + "| | | | | | | |/ / _ \\\n"
                    + "| |_| | |_| |   <  __/\n"
                    + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        while (continueRun) {
            System.out.println("==========================");
            System.out.println("How can I help you?");
            userCmd = sc.nextLine();

            try {
                // end program
                if (userCmd.toLowerCase().equals("bye")) {
                    System.out.println("Bye. Hope to see you again!");
                    continueRun = false;
                    break;
                }
                // List commands
                else if (userCmd.toLowerCase().equals("list")) {
                    listTasks(taskArrList);
                }
                // Mark task as done
                else if (userCmd.contains("done")) {
                    markTaskDone(userCmd, taskArrList);
                }
                // Add task type todos (normal tasks)
                else if (userCmd.contains("todo")) {
                    addTask(userCmd, taskArrList);
                }
                // Add task type deadline
                else if (userCmd.contains("deadline")) {
                    addDeadline(userCmd, taskArrList);
                }
                // Add task type events
                else if (userCmd.contains("event")) {
                    addEvent(userCmd, taskArrList);
                }
                else {
                    System.out.println("Wrong syntax!");
                    printHelp();
                }
//                saveDuke(taskArrList); TODO
            }
            catch (DukeException e){
                System.out.println(e +"\nPlease try again");
            }
        }
    }
}

// saving & reading to file reference:
// https://samderlust.com/dev-blog/java/write-read-arraylist-object-file-java