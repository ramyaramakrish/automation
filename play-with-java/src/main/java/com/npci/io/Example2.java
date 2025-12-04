package com.npci.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Example2 {
    public static void main(String[] args) {

        try {
            File file = new File("f1.txt");
            FileWriter fw = new FileWriter(file, true); // append mode
            fw.write("\nline-1");
            fw.write("\nline-2");
            fw.flush();
            fw.write("\nline-3");
            fw.close(); // flush() is called implicitly
            //fw.write("\nline-4"); // IOException
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
