package com.example.studybuddy.utils;

import android.util.Patterns;

import androidx.annotation.Nullable;

/// Validator class to validate user input.
/// This class contains static methods to validate user input,
/// like email, password, phone, name etc.
public class Validator {

    /// Check if the email is valid
    /// @param email email to validate
    /// @return true if the email is valid, false otherwise
    /// @see Patterns#EMAIL_ADDRESS
    public static boolean isEmailValid(@Nullable String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /// Check if the password is valid
    /// @param password password to validate
    /// @return true if the password is valid, false otherwise
    public static boolean isPasswordValid(@Nullable String password) {
        return password != null && password.length() >= 6;
    }


    /// Check if the phone number is valid
    /// @param phone phone number to validate
    /// @return true if the phone number is valid, false otherwise
    /// @see Patterns#PHONE
    public static boolean isPhoneValid(@Nullable String phone) {
        return phone != null && phone.length() >= 10 && Patterns.PHONE.matcher(phone).matches();
    }

    /// Check if the name is valid
    /// @param name name to validate
    /// @return true if the name is valid, false otherwise
    public static boolean isNameValid(@Nullable String name) {
        return name != null && name.length() >= 3;
    }
}