package com.npci.io;

import java.io.*;

public class Example5 {
    public static void main(String[] args) {

        try {
            File file=new File("/Users/nag/Downloads/G6_PT3 TIMETABLE(2025-26).xlsx");
            FileInputStream fis=new FileInputStream(file);
            byte[] data=new byte[(int)file.length()];
            fis.read(data);

            // need Apache POI lib, to read sheets, rows, cells from excel file

            File backupFile=new File("/Users/nag/Downloads/G6_PT3 TIMETABLE_BACKUP(2025-26)_backup.xlsx");
            FileOutputStream fos=new FileOutputStream(backupFile);
            fos.write(data);
            fos.close();
            fis.close();
            System.out.println("File backup completed successfully.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
