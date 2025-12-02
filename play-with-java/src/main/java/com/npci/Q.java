package com.npci;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Q {
    public static void main(String[] args) {
//        FileReader fr = null;
//        try {
//            fr = new FileReader("accounts.csv");
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } finally {
//            if (fr != null) {
//                try {
//                    fr.close();
//                } catch (Exception e) {
//                    System.out.println("Error closing the file");
//                }
//            }
//        }

        // JDK 1.7 and above - try with resources

        try(FileReader fr = new FileReader("accounts.csv")) {
            // read data from file
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("Some other error occurred");
        }

    }
}
