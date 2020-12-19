import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class TestPS5IsSoldOut {
    private WebDriver driver;
    private static String driverName;

    @BeforeClass
    public static void setProps() {
        String os = System.getProperty("os");
        driverName = System.getProperty("browser");
        if (os == null) {
            os = "macos";
        }
        if (driverName == null) {
            driverName = "chrome";
        }
        System.out.println("SELECTED_OS: " + os);
        System.out.println("SELECTED_DRIVER: " + driverName);
        System.setProperty(String.format("webdriver.%s.driver", driverName), String.format("src/test/resources/%sdriver_%s", driverName, os));
    }

    @Before
    public void setUp() {
        System.out.println("Configuring driver");
        if (driverName.equals("gecko")) {
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(true);
            driver = new FirefoxDriver(options);
        }
        if (driverName.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            driver = new ChromeDriver(options);
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        System.out.println("Configuring driver is finished");
    }

    @Test
    public void checkRozetka() {
        System.out.println("Started test");
        driver.navigate().to("https://rozetka.com.ua/playstation_5/p223588825");
        System.out.println("Navigated");
        WebElement productStatus = driver.findElement(By.className("product__status_color_gray"));
        System.out.println("Found element");
        Assert.assertTrue(productStatus.getText().contains("Нет в наличии") || productStatus.getText().contains("Товар закончился"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
