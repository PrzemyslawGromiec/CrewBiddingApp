import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        String fl;
        int num;

        String[] values = userInput.split("/");
        if (values.length == 2) {
            fl = values[0];
            num = Integer.parseInt(values[1]);
            System.out.println("Flight nr: " + fl);
            System.out.println("Num of stars: " + num);
        } else {
            System.out.println("Incorrect input. Enter values in format of 'flight number / priority'");
        }




    }

    private static void processInput(String userInput) {
        String[] values = userInput.split("/");
        if (values.length == 2 && isNumeric(values[1])) {
            String flightNumber = values[0].trim();
            int priority = Integer.parseInt(values[1].trim());
            System.out.println("Flight number: " + flightNumber);
            System.out.println("Priority: " + priority);
        } else {
            System.out.println();
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
