package com.npci.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Example3 {
    public static void main(String[] args) {

        File file = new File("f1.txt");
        try {
            FileReader fr = new FileReader(file);

            // way-1 : char by char reading
//            int ch;
//            while ((ch = fr.read()) != -1) {
//                System.out.print((char) ch);
//            }
//            fr.close();

            // way-2 : line by line reading using BufferedReader
            java.io.BufferedReader br = new java.io.BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
            fr.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
