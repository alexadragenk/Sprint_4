import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import page_objects.MainPage;
import page_objects.OrderPage;

import static org.hamcrest.CoreMatchers.startsWith;

@RunWith(Parameterized.class)
public class OrderPageTest {
    private WebDriver driver;

    private final int orderButtonNumber;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int station;
    private final String phoneNumber;
    private final String date;
    private final int duration;
    private final int color;
    private final String comment;

    public OrderPageTest(int orderButtonNumber, String firstName, String lastName, String address, int station, String phoneNumber, String date, int duration, int color, String comment) {
        this.orderButtonNumber = orderButtonNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.station = station;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.duration = duration;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {0, "Александра", "Калашникова", "Москва", 0, "89889999999", "26.01.2023", 1, 0, "комментарий1"},
                {1, "Александр", "Калашников", "Санкт-Петербург", 1, "89889999998", "26.02.2023", 2, 1, "комментарий2"},
        };
    }

    @Before
    public void setUp() {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(option);
//        driver = new FirefoxDriver();
    }

    @Test
    public void pressOrder_fillFields_orderIsMade() {
        MainPage mainPage = new MainPage(driver, 0);
        mainPage.open();
        mainPage.clickOrderButton(orderButtonNumber);
        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstOrderForm(firstName, lastName, address, station, phoneNumber);
        orderPage.fillSecondOrderForm(date, duration, color, comment);
        orderPage.orderIsCompleted();
        MatcherAssert.assertThat("Order must be completed", orderPage.orderIsCompleted(), startsWith("Заказ оформлен"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
