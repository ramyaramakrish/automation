package com.example;

public class Play_With_String {

    public static void main(String[] args) {


        //way-1:
        String s1=new String("npci");
        String s2=new String("npci");

        System.out.println(s1==s2); // false, different references
        System.out.println(s1.equals(s2)); // true, same content

        //way-2:
        String s3="npci";
        String s4="npci";
        System.out.println(s3==s4); // true, same reference from string pool
        System.out.println(s3.equals(s4)); // true, same content

        //-----

        // by default strings are immutable in java
        String str="hello";
        String str2=str.concat(" world"); // creates a new string, original 'str' remains unchanged
        System.out.println(str); // prints "hello"
        System.out.println(str2); // prints "hello world"

        //------------------------------
        // string methods
        //------------------------------


        String sample="  Hello NPCI Welcome to Java Programming  ";
        System.out.println("Original String: '" + sample + "'");
        System.out.println("Length: " + sample.length());
        System.out.println("Uppercase: " + sample.toUpperCase());
        System.out.println("Lowercase: " + sample.toLowerCase());
        System.out.println("Trimmed: '" + sample.trim() + "'");
        System.out.println("Substring (7, 11): " + sample.substring(7, 11));
        System.out.println("Replace 'Java' with 'Python': " + sample.replace("Java", "Python"));
        System.out.println("Index of 'NPCI': " + sample.indexOf("NPCI"));
        System.out.println("Character at index 10: " + sample.charAt(10));
        System.out.println("Split by space: ");
        String[] parts = sample.trim().split(" ");
        for (String part : parts) {
            System.out.println(part);
        }
        System.out.println("Contains 'Welcome': " + sample.contains("Welcome"));
        System.out.println("Starts with '  Hello': " + sample.startsWith("  Hello"));
        System.out.println("Ends with 'Programming  ': " + sample.endsWith("Programming  "));
        System.out.println("Is empty: " + sample.isEmpty());
        System.out.println("Trimmed Length: " + sample.trim().length());
        String s="  ";
        System.out.println("Is s empty: " + s.isEmpty());
        System.out.println("Is s blank: " + s.isBlank());


        //------------------------------

        StringBuffer sb=new StringBuffer("Hello");
        sb.append(" World");
        System.out.println("StringBuffer: " + sb.toString());


    }

}
