package aut_solution;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage {

    WebDriver driver;
    public SignInPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(name = "user-name")
    WebElement username;
    @FindBy(name = "password")
    WebElement password;
    @FindBy(name = "login-button")
    WebElement login;

    public boolean login(String username, String password){
        boolean flag = false;
        if(this.username.isDisplayed()){
            this.username.sendKeys(username);
            this.password.sendKeys(password);
            this.login.click();
            if(driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html"))
                flag = true;
            else
                flag = false;
        }else
            System.out.print("You're not on Login page");
        return flag;
    }

}
