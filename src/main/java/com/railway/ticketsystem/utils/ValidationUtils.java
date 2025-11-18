package com.railway.ticketsystem.utils;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^[0-9]{10}$";

    public boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public boolean isValidPhone(String phone) {
        return Pattern.compile(PHONE_PATTERN).matcher(phone).matches();
    }

    public boolean isValidPassword(String password) {
        // at least 5 chars (you can adjust later)
        return password != null && password.length() >= 5;
    }
}
