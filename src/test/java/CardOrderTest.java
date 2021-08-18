import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
       // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void happyPathFormTest() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Петрова Елизавета");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+71234567890");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("paragraph")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void formTest1() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Petrova Elisaveta");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+71234567890");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void formTest2() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Петрова Елизавета");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("71234567890");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void formTest3() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("Петрова Елизавета");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+71234567890");
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getText().trim();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void formTest4() {
        driver.findElement(By.cssSelector("input[name=name]")).sendKeys("");
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+71234567890");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedText, actualText);
    }
}
