package com.example.hubson.randomuserapp.utils;

public class TextUtils {

    private static final String SINGLE_SPACE = " ";

    public static String toUpperCaseFirstChar(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public static String capitalize(String text) {
        StringBuilder sb = new StringBuilder();
        String[] words = text.split(SINGLE_SPACE);
        for(String word : words)
            sb.append(toUpperCaseFirstChar(word)).append(SINGLE_SPACE);
        return sb.toString();
    }
}
