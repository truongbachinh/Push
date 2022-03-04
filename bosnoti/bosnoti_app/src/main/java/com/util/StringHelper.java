package com.util;

public class StringHelper {
    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.length() == 0)
            return true;

        return false;
    }

    public static int stringToInt(String value, int defValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long stringToLong(String value, long defValue) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return defValue;
        }
    }

    // Function to perform left padding
    public static String leftPadding(String input, char ch, int L) {
        String result = String.format("%" + L + "s", input).replace(' ', ch);

        return result;
    }

    // Function to perform right padding
    public static String rightPadding(String input, char ch, int L) {
        String result = String.format("%" + (-L) + "s", input).replace(' ', ch);

        return result;
    }
}
