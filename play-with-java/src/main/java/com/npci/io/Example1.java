package com.npci.io;

import java.io.File;
import java.io.IOException;

public class Example1 {

    public static void main(String[] args) {


        File file = new File("/Users/nag/automation/play-with-java/src/main/java/com/npci/io/Notes.txt");
        System.out.println("File Name: " + file.getName());
        System.out.println("Absolute Path: " + file.getAbsolutePath());
        System.out.println("Writeable: " + file.canWrite());
        System.out.println("Readable: " + file.canRead());
        System.out.println("File Size in bytes: " + file.length());

        //--------------

        File f1 = new File("foo.txt");
        System.out.println(f1.exists());
        try {
            f1.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //--------------

        File dir = new File("mydir");
        //dir.mkdir();
        //System.out.println(dir.isDirectory());

        String[] files=dir.list();
        for(String f:files){
            System.out.println(f);
        }


    }
}
