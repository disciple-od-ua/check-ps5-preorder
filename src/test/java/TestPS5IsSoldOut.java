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
    }

    @Test
    public void checkRozetka() {
        driver.navigate().to("https://rozetka.com.ua/playstation_5/p223588825/");
        WebElement productStatus = findByCss(".product__status_color_gray");
        Assert.assertNotNull("Статус продукта не найден", productStatus);

        Assert.assertTrue("Текст неверный", productStatus.getText().contains("Нет в наличии")
                || productStatus.getText().contains("Товар закончился")
                || productStatus.getText().contains("Немає в наявності"));
    }

    @Test
    public void checkRozetka_digital() {
        driver.navigate().to("https://rozetka.com.ua/playstation_5_digital_edition_2/p223596301/");
        WebElement productStatus = findByCss(".product__status_color_gray");
        Assert.assertNotNull("Статус продукта не найден", productStatus);

        Assert.assertTrue("Текст неверный", productStatus.getText().contains("Нет в наличии")
                || productStatus.getText().contains("Товар закончился")
                || productStatus.getText().contains("Немає в наявності"));
    }

//    @Test
//    public void checkAllo() {
//        driver.navigate().to("https://allo.ua/ua/igrovye-pristavki/igrovaya-konsol-playstation-5.html");
//        WebElement productStatus = findByCss(".main-button-section__stock");
//        Assert.assertNotNull(productStatus);
//        Assert.assertTrue(productStatus.getText().contains("Немає в наявності"));
//    }
//
//    @Test
//    public void checkAllo_digital() {
//        driver.navigate().to("https://allo.ua/ua/igrovye-pristavki/konsol-playstation-5-digital-edition.html");
//        WebElement productStatus = findByCss(".main-button-section__stock");
//        Assert.assertNotNull(productStatus);
//        Assert.assertTrue(productStatus.getText().contains("Немає в наявності"));
//    }
//
//    @Test
//    public void checkCitrus() {
//        driver.navigate().to("https://www.citrus.ua/igrovye-pristavki/igrovaya-konsol-sony-playstation-5-663700.html");
//        WebElement productStatus = driver.findElement(By.cssSelector("button[title='Уведомить о наличии']"));
//        Assert.assertTrue(productStatus.getText().contains("ХОЧУ"));
//    }
//
//    @Test
//    public void checkCitrus_digital() {
//        driver.navigate().to("https://www.citrus.ua/igrovye-pristavki/igrovaya-konsol-sony-playstation-5-digital-edition-668783.html");
//        WebElement productStatus = driver.findElement(By.cssSelector("button[title='Уведомить о наличии']"));
//        Assert.assertTrue(productStatus.getText().contains("ХОЧУ"));
//    }
//
//    @Test
//    public void checkFoxtrot() {
//        driver.navigate().to("https://www.foxtrot.com.ua/ru/shop/igrovye_pristavki_sony_ps5-bluray.html");
//        WebElement productStatus = driver.findElement(By.className("product-preorder-button"));
//        Assert.assertTrue(productStatus.getText().contains("Уведомить о наличии"));
//    }
//
//    @Test
//    public void checkFoxtrot_digital() {
//        driver.navigate().to("https://www.foxtrot.com.ua/ru/shop/igrovye_pristavki_sony_sony-ps5.html");
//        WebElement productStatus = driver.findElement(By.className("product-preorder-button"));
//        Assert.assertTrue(productStatus.getText().contains("Уведомить о наличии"));
//    }

    @After
    public void tearDown() {
        driver.quit();
        sleep(2);
    }

    private WebElement findByCss(String cssSelector) {
        int attempts = 0;
        while (attempts < 5) {
            try {
                WebElement productStatus = driver.findElement(By.cssSelector(cssSelector));
                return productStatus;
            } catch (Exception ex) {
                if (attempts < 5) {
                    attempts++;
                    sleep(4);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted sleep");
            e.printStackTrace();
        }

    }
}
