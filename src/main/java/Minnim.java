import java.util.Scanner;

public class Minnim {
    private Scanner s = new Scanner(System.in);

    private void chat(){
        String message = s.nextLine();
        if (message.equals("bye")) {
            System.out.println("     Bye. Hope to see you again soon!");
        } else {
            System.out.println("    "+ message);
            chat();
        }
    }
    public static void main(String[] args) {

        Minnim bot = new Minnim();

        //greetings
        System.out.println( "Hello! I'm Minnim.");
        System.out.println( "What can I do for you?");
        System.out.println( "\n");
        //echo
        bot.chat();

    }
}
