package org.example;

import org.example.controllers.AppController;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            AppController controller = new AppController();
            controller.run();
        } catch (FileNotFoundException e) {
            System.out.println("App couldn't run due to missing file.");
        }

    }
}