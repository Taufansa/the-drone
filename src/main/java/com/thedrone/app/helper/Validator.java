package com.thedrone.app.helper;

import java.util.ArrayList;
import java.util.Arrays;

public class Validator {

    public static boolean isOnlyLettersNumbersDashAndUnderscore(String text) {

        var allowedChars =  new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "_", "-", " "));

        for (int i = 0; i < text.length(); i++) {
            if (!allowedChars.contains(String.valueOf(text.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isOnlyUpperCaseLettersNumbersAndUnderscore(String text) {

        var allowedChars =  new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "_"));

        for (int i = 0; i < text.length(); i++) {
            if (!allowedChars.contains(String.valueOf(text.charAt(i)))) {
                return false;
            }
        }

        return true;
    }
}
