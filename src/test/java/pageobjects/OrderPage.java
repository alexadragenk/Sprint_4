package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    private final WebDriver driver;
    // Поля формы заказа
    private static final By orderFields = By.xpath(".//div[@class=\"Order_Form__17u6u\"]//input");
    // Кнопка "Заказать"
    private static final By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    // Кнопка "Да"
    private static final By yesButton = By.xpath(".//button[text()='Да']");
    // Выпадающий список срока аренды
    private static final By dropdownPeriod = By.xpath(".//div[@class='Dropdown-placeholder']");
    // Окно с подтверждением успешного заказа
    private static final By completeModalWindow = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstOrderForm(String firstName, String lastName, String address, int station, String phoneNumber) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(orderFields));
        List<WebElement> elements = driver.findElements(orderFields);
        elements.get(0).sendKeys(firstName);
        elements.get(1).sendKeys(lastName);
        elements.get(2).sendKeys(address);
        elements.get(3).click();
        WebElement stationRow = driver.findElement(By.xpath(String.format(".//li[@data-index=%d]", station)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", stationRow);
        stationRow.click();
        elements.get(4).sendKeys(phoneNumber);
        driver.findElement(orderButton).click();
    }

    public void fillSecondOrderForm(String date, int duration, int color, String comment) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(orderFields));
        List<WebElement> elements = driver.findElements(orderFields);
        elements.get(0).sendKeys(date);
        elements.get(0).sendKeys(Keys.ENTER);

        driver.findElement(dropdownPeriod).click();
        WebElement durationRow = driver.findElement(By.xpath(String.format(".//div[@class='Dropdown-menu']/div[position()=%d]", duration)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", durationRow);
        durationRow.click();

        elements.get(1 + color).click();
        elements.get(3).sendKeys(comment);
        driver.findElement(orderButton).click();
        driver.findElement(yesButton).click();
    }

    public String orderIsCompleted() {
        return driver.findElement(completeModalWindow).getText();
    }
}
