package com.epam.esm.validation;

import com.epam.esm.exception.LogicException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static void validateId(int id) {
        if (id <= 0) {
            throw new LogicException("messageCode10", "errorCode=3");
        }
    }

    public static void validateTagName(String tagName) {
        if (isContainsOnlyNumbers(tagName)) {
            throw new LogicException("messageCode25", "errorCode=3");
        }
    }

    public static void validateCertificateName(String certificateName) {
        if (isContainsOnlyNumbers(certificateName)) {
            throw new LogicException("messageCode26", "errorCode=3");
        }
    }

    public static boolean isContainsOnlyNumbers(String input) {
        boolean isContain = true;
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            isContain = false;
        }
        return isContain;
    }
}
