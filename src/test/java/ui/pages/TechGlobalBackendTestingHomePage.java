package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

import java.util.List;

public class TechGlobalBackendTestingHomePage extends TechGlobalBasePage{

    public TechGlobalBackendTestingHomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "input[name='firstName']")
    public WebElement firstName;

    @FindBy(css = "input[name='lastName']")
    public WebElement lastName;

    @FindBy(css = "input[name='email']")
    public WebElement backendEmail;

    @FindBy(name = "dob")
    public WebElement dob;

    @FindBy(xpath = "//*[contains(text(),'ADD')]")
    public WebElement add;

    @FindBy(xpath = "//*[contains(text(),'Successfully added user!')]")
    public WebElement successMessage;

    @FindBy(css = ".common_listInfo__FnnDR")
    public List<WebElement> table;
}
