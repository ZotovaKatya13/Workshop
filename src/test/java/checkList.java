import org.example.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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

        // Переходим в раздел клиентов
        driver.findElement(Main.customers).click();

       // Получаем список всех клиентов из таблицы
        List<WebElement> customerRows = driver.findElements(Main.listAllCustomer);
        List<String> customerNames = Main.CustomerUtils.getCustomerNames(customerRows);

// Вычисляем среднюю длину имен
        OptionalDouble averageLength = Main.CustomerUtils.calculateAverageNameLength(customerNames);

        if (averageLength.isPresent()) {
            double avg = averageLength.getAsDouble();

            // Находим имя с длиной, ближайшей к среднему
            String closestName = Main.CustomerUtils.findClosestNameToAverage(customerNames, avg);

            // Удаляем клиента с найденным именем
            Main.CustomerUtils.deleteCustomerByName(customerRows, customerNames, closestName);
        }

    }
        }
