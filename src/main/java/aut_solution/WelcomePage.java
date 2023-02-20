package aut_solution;

import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.List;

public class WelcomePage {
    WebDriver webDriver;
    public WelcomePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "//*[@id=\"add-to-cart-sauce-labs-backpack\"]")
    WebElement firstItem;

    @FindBy(xpath = "//*[@id=\"inventory_container\"]/div/div[1]/div[2]/div[2]/div")
    WebElement firstItemPrice;

    @FindBy(xpath = "//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")
    WebElement secondItem;
    @FindBy(xpath = "//*[@id=\"inventory_container\"]/div/div[2]/div[2]/div[2]/div")
    WebElement secondItemPrice;
    @FindBy(id = "shopping_cart_container")
    WebElement cart;

    @FindBy(id = "checkout")
    WebElement checkout;

    public List<String> addToCart(){
        List<String> prices = new ArrayList<>();
        if(firstItem.isDisplayed())
        {
            prices.add(firstItemPrice.getText());
            prices.add(secondItemPrice.getText());
            firstItem.click();
            secondItem.click();
            cart.click();
            Assert.assertTrue(webDriver.getPageSource().contains("Sauce Labs Backpack"));
            Assert.assertTrue(webDriver.getPageSource().contains("Sauce Labs Bike Light"));
            checkout.click();
        }
        return prices;
    }
}
