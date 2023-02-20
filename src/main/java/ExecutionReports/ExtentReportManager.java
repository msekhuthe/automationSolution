package ExecutionReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExtentReportManager {
  private static ExtentReports extent;

  public static ExtentReports createInstance(String fileName) {
    ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
    htmlReporter.config().setDocumentTitle(fileName);
    htmlReporter.config().setEncoding("utf-8");
    htmlReporter.config().setReportName(fileName);
    htmlReporter.config().setTheme(Theme.DARK);

    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    extent.setSystemInfo("Automation Tester", System.getProperty("user.name"));
    return extent;
  }

  public static String screenshotPath;
  public static String screenshotName;
  public static String myRequest;
  public static String myResponse;

  public static String captureBase64(WebDriver driver) throws IOException {
    String encodedBase64 = null;
    FileInputStream fileInputStream = null;
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    File Dest = new File("src/../BStackImages/" + System.currentTimeMillis() + ".png");
    screenshotPath = Dest.getAbsolutePath();
    FileUtils.copyFile(scrFile, Dest);

    try {

      fileInputStream = new FileInputStream(Dest);
      byte[] bytes = new byte[(int) Dest.length()];
      fileInputStream.read(bytes);
      encodedBase64 = new String(Base64.encodeBase64(bytes));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return "data:image/png;base64," + encodedBase64;
  }

  public static void writePayloads(String request, String response){
    myRequest = request.replace("\n", "<br>");
    myResponse = response;
  }
}
