package com.example;

import java.util.regex.Pattern;

public class Exercise {
    public static void main(String[] args) {

        String ssn = "112-4a-6789";
        //System.out.println(isValidSSN(ssn));

        // Way-2
        String re = "\\d{3}-\\d{2}-\\d{4}";
        boolean b = ssn.matches(re);

        //way-3
        Pattern pattern = Pattern.compile(re);
        boolean b2 = pattern.matcher(ssn).matches();
        System.out.println(b);
        System.out.println(b2);


    }

    static boolean isValidSSN(String ssn) {
        int len = ssn.length();
        if (len == 11) {
            for (int i = 0; i < len; i++) {
                char ch = ssn.charAt(i);
                if (i == 3 || i == 6) {
                    if (ch != '-') return false;
                } else {
                    //if (!Character.isDigit(ch)) return false;
                    if ("1234567890".indexOf(ch) == -1) return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
