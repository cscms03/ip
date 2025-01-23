import java.util.Scanner;

public class Minnim {
    private Scanner s = new Scanner(System.in);
    private Task[] tasks = new Task[100];
    private final String dateDivider = "/";

    private void todo(String message, int i) throws MinnimMissingTaskDetailException {
        try {
            Todo todo = new Todo(message.substring(5));
            this.tasks[i] = todo;
            i++;
            System.out.println("Got it. I've added this task:\n");
            System.out.println(i + ". " + todo.getDescription() + "\n");
            System.out.println("Now you have " + i + " tasks in the list.");
        } catch (StringIndexOutOfBoundsException e) {
            throw new MinnimMissingTaskDetailException();
        }
    }

    private void deadline(String message, int i) throws MinnimMissingTaskDetailException, MinnimMissingDateException {
        try {
            if (message.length() == 8) {
                // case where only "deadline" was entered without task nor dates
                throw new MinnimMissingTaskDetailException();
            }
            int index = message.indexOf(dateDivider);
            String date = message.substring(index + 1).replaceFirst("by", "");
            Deadline deadline = new Deadline(message.substring(9, index - 1), date);
            this.tasks[i] = deadline;
            i++;
            System.out.println("Got it. I've added this task:\n");
            System.out.println(i + ". " + deadline.getDescription() + "\n");
            System.out.println("Now you have " + i + " tasks in the list.");
        } catch (StringIndexOutOfBoundsException e) {
            // case where deadline and task details were given but missing a date
            throw new MinnimMissingDateException();
        }
    }

    private void event(String message, int i) throws MinnimMissingTaskDetailException, MinnimMissingDateException {
        try {
            if (message.length() == 5) {
                // case where only "evemt" was entered without task nor dates
                throw new MinnimMissingTaskDetailException();
            }
            int firstIndex = message.indexOf(dateDivider);
            int secondIndex = message.indexOf(dateDivider, firstIndex + 1);
            String fromDate = message.substring(firstIndex + 1, secondIndex - 1).replaceFirst("from", "");
            String toDate = message.substring(secondIndex + 1).replaceFirst("to", "");
            Events event = new Events(message.substring(6, firstIndex - 1), fromDate, toDate);
            this.tasks[i] = event;
            i++;
            System.out.println("Got it. I've added this task:\n");
            System.out.println(i + ". " + event.getDescription() + "\n");
            System.out.println("Now you have " + i + " tasks in the list.");
        } catch (StringIndexOutOfBoundsException e) {
            throw new MinnimMissingDateException();
        }
    }

    private void mark(String message) throws MinnimNoTaskFoundException {
        int taskNum = Integer.parseInt(message.substring(5).trim());
        try {
            tasks[taskNum - 1].setMarked();
            System.out.println("Nice! I've marked this task as done: \n");
            System.out.println(taskNum + ". " + tasks[taskNum - 1].getDescription());
        } catch (NullPointerException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    private void unmark(String message) throws MinnimNoTaskFoundException {
        int taskNum = Character.getNumericValue(message.charAt(7));
        try {
            tasks[taskNum - 1].setUnmarked();
            System.out.println("OK, I've marked this task as not done yet: \n");
            System.out.println(taskNum + ". " + tasks[taskNum - 1].getDescription());
        } catch (NullPointerException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    private void chat() {
        int i = 0;
        try {
            while (true) {
                String message = s.nextLine();
                if (message.equals("bye")) {
                    System.out.println("     Bye. Hope to see you again soon!");
                    break;
                }

                if (message.equals("list")) {
                    for (int j = 0; j < i; j++) {
                        System.out.print(j + 1 + ". " + this.tasks[j].getDescription());
                        System.out.print("\n");
                    }
                } else if (message.startsWith("todo")) {
                    todo(message, i);
                    i++;
                } else if (message.startsWith("deadline")) {
                    deadline(message, i);
                    i++;
                } else if (message.startsWith("event")) {
                    event(message, i);
                    i++;
                } else if (message.startsWith("mark")) {
                    mark(message);
                } else if (message.startsWith("unmark")) {
                    unmark(message);
                } else {
                    System.out.println("Please provide the type of your task: todo, deadline, or event");
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
