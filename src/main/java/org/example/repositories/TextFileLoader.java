package org.example.repositories;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileLoader {
    private String filePath;

    public TextFileLoader(String filePath) {
        this.filePath = filePath;
    }

    public List<String> readFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }


}
