package com.npci.io;

import java.io.*;

public class Exercise {
    public static void main(String[] args) {

        try {
            FileReader fr = new FileReader(new File("csv_report.csv"));
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    System.out.print(value + " | ");
                }
                System.out.println();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
