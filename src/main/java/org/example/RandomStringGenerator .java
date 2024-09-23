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
     public class CustomerUtils {

        // Метод для получения списка имен клиентов
        public static List<String> getCustomerNames(List<WebElement> customerRows) {
            return customerRows.stream()
                    .map(row -> row.findElement(Main.columnsNameCustomer).getText())
                    .collect(Collectors.toList());
        }

        // Метод для вычисления средней длины имен
        public static OptionalDouble calculateAverageNameLength(List<String> customerNames) {
            return customerNames.stream()
                    .map(String::length)
                    .mapToInt(Integer::intValue)
                    .average();
        }

        // Метод для нахождения имени с длиной, ближайшей к среднему
        public static String findClosestNameToAverage(List<String> customerNames, double averageLength) {
            return customerNames.stream()
                    .min((name1, name2) -> {
                        double diff1 = Math.abs(name1.length() - averageLength);
                        double diff2 = Math.abs(name2.length() - averageLength);
                        return Double.compare(diff1, diff2);
                    }).orElse(null);
        }

        // Метод для удаления клиента по имени
        public static void deleteCustomerByName(List<WebElement> customerRows, List<String> customerNames, String closestName) {
            if (closestName != null) {
                int indexToRemove = customerNames.indexOf(closestName);
                if (indexToRemove != -1) { // Проверка на существование индекса
                    customerRows.get(indexToRemove).findElement(Main.buttonDelete).click();
                }
            }
        }
        }
}



