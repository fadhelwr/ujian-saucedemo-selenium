import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SauceDemoTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Fadhel\\Documents\\SQA\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testNegativeLogin() {
        driver.get("https://www.saucedemo.com/");

        // Login dengan kredensial yang salah
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        username.sendKeys("wrong_user");
        password.sendKeys("wrong_password");
        loginButton.click();

        // Assert bahwa error message muncul
        WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));
        Assert.assertTrue(errorMessage.isDisplayed());
    }

    @Test(priority = 2)
    public void testLogin() {
        driver.get("https://www.saucedemo.com/");

        // Login
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();

        // Assert login sukses
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test (priority = 3)
    public void testAddProductToCart() {
        testLogin(); // Pastikan login berhasil terlebih dahulu

        // Tambah produk ke keranjang
        WebElement product = driver.findElement(By.xpath("//div[@class='inventory_item'][1]//button"));
        product.click();

        // Verifikasi produk ditambahkan
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals("1", cartBadge.getText());
    }

    @AfterClass
    public void tearDown() {
        try {
            // Tunggu 3 detik sebelum menutup browser
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
