package pages;

import io.qameta.allure.Attachment;
import managers.DriverManager;
import managers.PageManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Базовый класс всех страничек
 */
public class BasePage {

    /**
     * Менеджер WebDriver
     */
    protected final DriverManager driverManager = DriverManager.getDriverManager();

    /**
     * Менеджер страничек
     */
    protected PageManager pageManager = PageManager.getPageManager();

    /**
     * Объект для имитации реального поведения мыши или клавиатуры
     */
    protected Actions action = new Actions(driverManager.getDriver());

    /**
     * Объект для выполнения любого js кода
     */
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    /**
     * Объект явного ожидания
     * При применении будет ожидать заданного состояния 10 секунд с интервалом в 1 секунду
     */
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);

    /**
     * Конструктор позволяющий инициализировать все странички и их элементы помеченные аннотацией {@link FindBy}
     * Подробнее можно просмотреть в класс {@link PageFactory}

     */
    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js
     *
     * @param element - веб-элемент странички
     * @see JavascriptExecutor
     */
    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    /**
     * Функция позволяющая производить scroll до любого элемента с помощью js со смещение
     * Смещение задается количеством пикселей по вертикали и горизонтали, т.е. смещение до точки (x, y)
     *
     */
    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driverManager.getDriver()).executeScript(code, element, x, y);
        return element;
    }

    /**
     * Явное ожидание состояния clickable элемента
     *

     */
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Общий метод по заполнения полей ввода
     */
    protected void fillInputField(WebElement field, String value) {
        scrollToElementJs(field);
        waitUtilElementToBeClickable(field).click();
        field.sendKeys(value);
    }

    /**
     * Общий метод по заполнению полей с датой
     */
    protected void fillDateField(WebElement field, String value) {
        scrollToElementJs(field);
        field.sendKeys(value);
    }

    @Attachment(value = "Error screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
