package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

import java.util.List;

public class TechGlobalBasePage {

    public TechGlobalBasePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }
    // common elements from heater and footer and some common elements

    @FindBy(id = "logo")
    public WebElement logo;

    @FindBy(id = "dropdown-button")
    public WebElement headerDropdown;

    @FindBy(css = "#dropdown-menu a")
    public List<WebElement> headerDropdownOptions;
}
