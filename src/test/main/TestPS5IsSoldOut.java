import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestPS5IsSoldOut {
    private WebDriver driver;

    @BeforeClass
    public static void setProps() {
        String os = System.getProperty("os");
        if (os == null) {
            os = "macos";
        }
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_" + os);
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @Test
    public void checkRozetka() {
        driver.get("https://rozetka.com.ua/playstation_5/p223588825");
        WebElement productStatus = driver.findElement(By.className("product__status_color_gray"));
        Assert.assertTrue(productStatus.getText().contains("Нет в наличии"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
