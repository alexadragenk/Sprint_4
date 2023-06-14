package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    // Адрес сайта
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    // Кнопка-стрелка
    private final By arrow;
    // Текст ответа на вопрос
    private final By answerText;
    // Обе кнопки "Заказать"
    private static final By orderButtons = By.xpath(".//button[text()='Заказать']");

    public MainPage(WebDriver driver, Integer questionNumber) {
        this.driver = driver;
        // одна из элементов кнопок-стрелок, в id проставляется индекс конкретного элемента
        arrow = By.xpath(String.format(".//div[@id='accordion__heading-%d']", questionNumber));
        // один из элементов с текстом, в id проставляется индекс конкретного элемента
        answerText = By.xpath(String.format(".//div[@aria-labelledby='accordion__heading-%d']", questionNumber));
    }

    public void open() {
        driver.get(PAGE_URL);
    }

    public void clickArrow() {
        WebElement element = driver.findElement(arrow);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void clickOrderButton(int orderButtonNumber) {
        List<WebElement> elements = driver.findElements(orderButtons);
        WebElement theElement = elements.get(orderButtonNumber);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", theElement);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(theElement));
        theElement.click();
    }

    public boolean isTextVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> driver.findElement(answerText).isDisplayed());
        return driver.findElement(answerText).isDisplayed();
    }

    public String getAnswer() {
        return driver.findElement(answerText).getText();
    }
}
