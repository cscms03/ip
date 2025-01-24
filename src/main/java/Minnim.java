import java.util.Scanner;
import java.util.ArrayList;

public class Minnim {
    private Scanner s = new Scanner(System.in);
    private ArrayList<Task> tasks = new ArrayList<>();
    private final String dateDivider = "/";

    private void listTasks() {
        if (this.tasks.isEmpty()) {
            System.out.println("Your task list is empty.");
        } else {
            for (int j = 0; j < this.tasks.size(); j++) {
                System.out.print(j + 1 + ". " + this.tasks.get(j).getDescription() + "\n");
            }
        }
    }

    private void todo(String message) throws MinnimMissingTaskDetailException {
        try {
            Todo todo = new Todo(message.substring(5));
            this.tasks.add(todo);
            System.out.println("Got it. I've added this task:\n");
            System.out.println(this.tasks.size() + ". " + todo.getDescription() + "\n");
            System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
        } catch (StringIndexOutOfBoundsException e) {
            throw new MinnimMissingTaskDetailException();
        }
    }

    private void deadline(String message) throws MinnimMissingTaskDetailException, MinnimMissingDateException {
        try {
            if (message.length() == 8) {
                // case where only "deadline" was entered without task nor dates
                throw new MinnimMissingTaskDetailException();
            }
            int index = message.indexOf(dateDivider);
            String date = message.substring(index + 1).replaceFirst("by", "");
            Deadline deadline = new Deadline(message.substring(9, index - 1), date);
            this.tasks.add(deadline);
            System.out.println("Got it. I've added this task:\n");
            System.out.println(this.tasks.size() + ". " + deadline.getDescription() + "\n");
            System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
        } catch (StringIndexOutOfBoundsException e) {
            // case where deadline and task details were given but missing a date
            throw new MinnimMissingDateException();
        }
    }

    private void event(String message) throws MinnimMissingTaskDetailException, MinnimMissingDateException {
        try {
            if (message.length() == 5) {
                // case where only "event" was entered without task nor dates
                throw new MinnimMissingTaskDetailException();
            }
            int firstIndex = message.indexOf(dateDivider);
            int secondIndex = message.indexOf(dateDivider, firstIndex + 1);
            String fromDate = message.substring(firstIndex + 1, secondIndex - 1).replaceFirst("from", "");
            String toDate = message.substring(secondIndex + 1).replaceFirst("to", "");
            Events event = new Events(message.substring(6, firstIndex - 1), fromDate, toDate);
            this.tasks.add(event);
            System.out.println("Got it. I've added this task:\n");
            System.out.println(this.tasks.size() + ". " + event.getDescription() + "\n");
            System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
        } catch (StringIndexOutOfBoundsException e) {
            throw new MinnimMissingDateException();
        }
    }

    private void mark(String message) throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException{
        if (message.trim().length() == 4) {
            // if only command is given without target task number
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(5).trim());
        try {
            this.tasks.get(taskNum - 1).setMarked();
            System.out.println("Nice! I've marked this task as done: \n");
            System.out.println(taskNum + ". " + this.tasks.get(taskNum - 1).getDescription());
        } catch (IndexOutOfBoundsException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    private void unmark(String message) throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        if (message.trim().length() == 6) {
            // if only command is given without target task number
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(7).trim());
        try {
            this.tasks.get(taskNum - 1).setUnmarked();
            System.out.println("OK, I've marked this task as not done yet: \n");
            System.out.println(taskNum + ". " + this.tasks.get(taskNum - 1).getDescription());
        } catch (IndexOutOfBoundsException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    private void delete(String message) throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        if (message.trim().length() == 6) {
            // if only command is given without target task number
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(7).trim());
        try {
            Task taskToDelete = this.tasks.get(taskNum - 1);
            System.out.println("Noted. I've removed this task:");
            System.out.println(taskToDelete.getDescription());
            this.tasks.remove(taskNum - 1);
            System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
        } catch (IndexOutOfBoundsException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    private void chat() {
        try {
            while (true) {
                String message = s.nextLine().trim();
                if (message.equals("bye")) {
                    System.out.println("     Bye. Hope to see you again soon!");
                    break;
                }

                String command = message.split(" ")[0];
                TaskType taskType = TaskType.fromString(command);

                if (taskType == null) {
                    System.out.println("Please provide the type of your task: todo, deadline, or event / delete, list, mark, unmark");
                } else {
                    switch (taskType) {
                        case TODO:
                            todo(message);
                            break;
                        case DEADLINE:
                            deadline(message);
                            break;
                        case EVENT:
                            event(message);
                            break;
                        case MARK:
                            mark(message);
                            break;
                        case UNMARK:
                            unmark(message);
                            break;
                        case DELETE:
                            delete(message);
                            break;
                        case LIST:
                            listTasks();
                            break;
                        }
                    }
                }
        } catch (MinnimException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {

        Minnim bot = new Minnim();

        //greetings
        System.out.println( "Hello! I'm Minnim.");
        System.out.println( "What can I do for you?");
        System.out.println( "\n");

        //execute chat
        bot.chat();

    }
}
