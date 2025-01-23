import java.util.Scanner;

public class Minnim {
    private Scanner s = new Scanner(System.in);
    private String[] tasks = new String[100];

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
                    System.out.print(j + 1 + ". " + this.tasks[j]);
                    System.out.print("\n");
                }
            } else {
                this.tasks[i] = message;
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
