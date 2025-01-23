import java.util.Scanner;

public class Minnim {
    private Scanner s = new Scanner(System.in);
    private Task[] tasks = new Task[100];

    private void chat() {
        int i = 0;
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
            } else if (message.startsWith("mark")) {
                int taskNum = Character.getNumericValue(message.charAt(5));
                tasks[taskNum - 1].setMarked();
                System.out.println("Nice! I've marked this task as done: \n");
                System.out.println(tasks[taskNum - 1].getDescription());
            } else if (message.startsWith("unmark")) {
                int taskNum = Character.getNumericValue(message.charAt(7));
                tasks[taskNum - 1].setUnmarked();
                System.out.println("OK, I've marked this task as not done yet: \n");
                System.out.println(tasks[taskNum - 1].getDescription());
            } else {
                Task t = new Task(message);
                this.tasks[i] = t;
                System.out.println("added: " + message);
                System.out.print("\n");
                i++;
            }
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
