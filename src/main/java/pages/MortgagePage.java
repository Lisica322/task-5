package pages;

import io.cucumber.datatable.DataTable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MortgagePage extends BasePage {
    @FindBy(xpath = "//div[contains(@class,'kit-col_xs_12')]//h1")
    private WebElement title;

    @FindBy(xpath = "//div[@class='dc-input__input-container-5-3-5']//input")
    private List<WebElement> creditInfoFieldList;

    @FindBy(xpath = "//input[contains(@class,'switch-input')]")
    private List<WebElement> switchInputList;

    @FindBy(xpath = "//span[contains(text(),'Молодая семья')]/../following-sibling::span//input")
    private WebElement youngFamilyCheckBox;

    @FindBy(xpath = "//span[contains(text(),'Страхование жизни и здоровья')]/../following-sibling::span//input")
    private WebElement lifeInsuranceCheckBox;

    /**
     * Заполняем поле стоимость недвижимости
     */
    public void fill(DataTable mapFieldsAndValue) {
        mapFieldsAndValue.asMap(String.class, String.class).forEach((key, value) ->
                pageManager.getCheckAndInputPage().fillField((String) key, (String) value));
    }

    /**
     * Кликаем оп кнопке страхование жизни и здороаья
     */
    public MortgagePage checkOrUncheckBox(String operation, String nameField) {
        String booleanFlag = operation.equals("Поставить") ? "true" : "false";
        WebElement element = null;
        switch (nameField) {
            case "Молодая семья":
                element = youngFamilyCheckBox;
                break;
            case "Страхование жизни и здоровья":
                element = lifeInsuranceCheckBox;
                break;
            default:
                Assertions.fail("CheckBox с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Ипотека на готовые квартиры'");

        }
        if (!element.getAttribute("checked").equals(booleanFlag)) {
            scrollWithOffset(element, 0, 100);
            waitUtilElementToBeClickable(element.findElement(By.xpath("./.."))).click();
            wait.until(ExpectedConditions.attributeToBe(element, "checked", booleanFlag));
            Assertions.assertEquals(booleanFlag, element.getAttribute("checked"), "CheckBox '" + nameField + "' не поставился");
        }

        return this;
    }
}