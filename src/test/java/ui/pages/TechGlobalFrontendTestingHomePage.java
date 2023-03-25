package ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

import java.util.List;

public class TechGlobalFrontendTestingHomePage extends TechGlobalBasePage {
    public TechGlobalFrontendTestingHomePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // locate all the unique elements for this page
    @FindBy(css = "div[id^='card']")
    public List<WebElement> cards;

    public void getFrontendTestingPage(){
        headerDropdown.click();
        headerDropdownOptions.get(0).click();
    }

    public void clickOnCard(String cardText){
        for (WebElement card : cards) {
            if(card.getText().equals(cardText)){
                card.click();
                break;
            }
        }
    }
    public void clickOnCard(int index){
            cards.get(index).click();
    }

}
