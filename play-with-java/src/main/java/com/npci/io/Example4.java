package com.npci.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Example4 {
    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(new File("csv_report.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                for (String value : values) {
                    System.out.print(value + " | ");
                }
                System.out.println();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
