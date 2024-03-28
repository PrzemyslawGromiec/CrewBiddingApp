package org.example;

import org.example.controllers.AppController;

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

/*
*
* Wczytanie lotów
* Nalozenie preferncji
* Wygenerowanie periodow
* Wyliczenie lotow dla perioda
* Nalozenie buforow
* Decyzja ktory lot bierzemy
*
*
*
*
*
*
*
*
*
*
*
*
* */