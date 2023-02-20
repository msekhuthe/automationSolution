package aut_solution;

import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    WebDriver driver;
    public CheckoutPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    @FindBy(id = "first-name")
    WebElement firstname;
    @FindBy(id = "last-name")
    WebElement lastname;
    @FindBy(id = "postal-code")
    WebElement zipCode;
    @FindBy(id = "continue")
    WebElement btnContinue;
    @FindBy(id = "finish")
    WebElement btnFinish;

    @FindBy(xpath = "//*[@id=\"checkout_summary_container\"]/div/div[2]/div[5]")
    WebElement itemTotal;

    public void checkOut(String name, String surname, String code, String firstPrice, String secondPrice) throws InterruptedException {
        firstname.sendKeys(name);
        lastname.sendKeys(surname);
        zipCode.sendKeys(code);
        btnContinue.click();

        btnFinish.click();
        Thread.sleep(1000);
        Assert.assertTrue(driver.getPageSource().contains("THANK YOU FOR YOUR ORDER"));
    }
}
