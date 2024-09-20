
import org.example.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



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
    }
}