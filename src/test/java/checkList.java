
import org.example.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class checkList {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    // @After
    //public void tearDown() {
    // driver.quit();
    // }

    @Test
    public void addCustomer() {
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager");
        wait.until(ExpectedConditions.presenceOfElementLocated(Main.buttonAddCustomer));
        driver.findElement(Main.buttonAddCustomer).click();

        String postCode = Main.generateRandomPostCode();
        wait.until(ExpectedConditions.presenceOfElementLocated(Main.postCodee));
        driver.findElement(Main.postCodee).sendKeys(postCode);

        String generatedName = Main.generateNameFromPostCode(postCode);
        driver.findElement(Main.firstName).sendKeys(generatedName);

        String lastNameGenerated = Main.generatedString();
        driver.findElement(Main.lastName).sendKeys(lastNameGenerated);

        driver.findElement(Main.button).click();


        try {

            Alert alert = driver.switchTo().alert();
            // Принять алерт
            alert.accept();
        } catch (
                NoAlertPresentException e) {
            System.out.println("Алерт не найден.");
        }

        // Переходим в раздел клиентов
        driver.findElement(Main.customer).click();

        // Получаем список всех клиентов из таблицы
        List<WebElement> customerRows = driver.findElements(By.cssSelector("table tbody tr"));
        List<String> customerNames = customerRows.stream()
                .map(row -> row.findElement(By.cssSelector("td:nth-child(1)")).getText())
                .collect(Collectors.toList());

        // Вычисляем длины имен и среднее арифметическое
        List<Integer> nameLengths = customerNames.stream()
                .map(String::length)
                .collect(Collectors.toList());

        OptionalDouble averageLength = nameLengths.stream()
                .mapToInt(Integer::intValue)
                .average();

        if (averageLength.isPresent()) {
            double avg = averageLength.getAsDouble();

            // Находим имя с длиной, ближайшей к среднему
            String closestName = customerNames.stream()
                    .min((name1, name2) -> {
                        double diff1 = Math.abs(name1.length() - avg);
                        double diff2 = Math.abs(name2.length() - avg);
                        return Double.compare(diff1, diff2);
                    }).orElse(null);

            // Удаляем клиента с найденным именем
            if (closestName != null) {
                // Нажимаем на кнопку удаления для соответствующей строки
                int indexToRemove = customerNames.indexOf(closestName);
                customerRows.get(indexToRemove).findElement(By.cssSelector("button.btn-danger")).click();
            }
        }
    }
}


