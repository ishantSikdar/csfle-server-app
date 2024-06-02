package com.ishant.csfle.util;

import java.util.regex.Pattern;

public class RegexUtil {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._]{3,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");

    public static String identify(String input) {
        if (MOBILE_PATTERN.matcher(input).matches()) {
            return "mobile";
        } else if (EMAIL_PATTERN.matcher(input).matches()) {
            return "email";
        } else if (USERNAME_PATTERN.matcher(input).matches()) {
            return "username";
        } else {
            return "unknown";
        }
    }
}
