import ExecutionReports.ExtentReportLister;
import Loggerr.Log;
import aut_solution.CheckoutPage;
import aut_solution.WelcomePage;
import aut_solution.SignInPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Listeners(ExtentReportLister.class)
public class CheckoutTestcases {

    WebDriver webDriver;
    SignInPage SignInPage;
    WelcomePage WelcomePage;
    CheckoutPage CheckoutPage;
    List<String> prices = new ArrayList<>();
    public WebDriver getWebDriver(){
        return webDriver;
    }
    @BeforeClass
    @Parameters({"browser"})
    public void browserLauncher(@Optional("Chrome") String browser){

        switch (browser) {
            case "Chrome":

                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                Log.info("Chrome started successfully");
                break;
            case "Firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                Log.info("Firefox started successfully");
                break;
            case "Edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                Log.info("Edge started successfully");
                break;
            case "IE":
                WebDriverManager.iedriver().setup();
                webDriver = new InternetExplorerDriver();
                break;
            default:
                System.out.println("Invalid browser");
                break;
        }
        Log.info("Launching saucedemo website");
        webDriver.get("https://www.saucedemo.com/");
        SignInPage = new SignInPage(webDriver);
        WelcomePage = new WelcomePage(webDriver);
        CheckoutPage = new CheckoutPage(webDriver);
    }
    @Test(priority = 1)
    public void login(){
        Log.info("Login testcase is starting");
        boolean pass = SignInPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(pass);
        Log.info("Login testcase concluded");
    }

    @Test(priority = 2)
    public void addToCart(){
        Log.info("Adding Items to cart");
        prices = WelcomePage.addToCart();
        Log.info("Done adding items to cart");
    }

    @Test(priority = 3)
    private void checkout() throws InterruptedException {
        Log.info("Checkout process starting");
        CheckoutPage.checkOut("Mogopodi", "Sekhuthe", "1724", prices.get(0), prices.get(1));
        Log.info("Checkout completed");
    }

    @AfterClass
    private void teardown(){
        Log.info("Shutting Down driver");
        webDriver.close();
    }
}
