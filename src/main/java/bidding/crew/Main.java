package bidding.crew;

import bidding.crew.controller.AppController;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            AppController controller = new AppController();
            controller.runMenu();
        } catch (FileNotFoundException e) {
            System.out.println("App couldn't run due to missing file.");
        }
    }
}
