package org.example;

import org.openqa.selenium.By;

import java.util.concurrent.ThreadLocalRandom;


public class Main {

    public static By buttonAddCustomer = (By.cssSelector(".btn.btn-lg.tab"));
    public static By postCodee = (By.cssSelector("div.form-group:nth-of-type(3)>input.ng-invalid-required"));
    public static By firstName = (By.cssSelector(".ng-untouched.ng-invalid.ng-invalid-required"));
    public static By customers = (By.cssSelector(".btn.btn-lg.tab:nth-of-type(3)"));
    public static By listAllCustomer = (By.cssSelector(".table tbody tr"));
    public static By columnsNameCustomer = (By.cssSelector("td:nth-child(1)"));
    public static By buttonDelete = (By.cssSelector("button.btn-danger"));

    public static String generateRandomPostCode() {
        long randomNum = ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);
        return String.valueOf(randomNum);
    }

    public static String generateNameFromPostCode(String postCode) {
        return convertNumbersToLetters(postCode);
    }

    private static String convertNumbersToLetters(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i += 2) {
            String twoDigit = input.substring(i, Math.min(i + 2, input.length()));
            int number = Integer.parseInt(twoDigit);
            char letter = convertNumberToLetter(number);
            result.append(letter);
        }

        return result.toString();
    }

    private static char convertNumberToLetter(int number) {
        int adjustedNumber = number % 26;
        return (char) ('a' + adjustedNumber);
    }
}



