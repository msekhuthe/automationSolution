package ExecutionReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ExtentReportLister implements ITestListener {

  static Date d = new Date();
  static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
  private static ExtentReports extent =
      ExtentReportManager.createInstance("reports/" + fileName);
  public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

  public void onTestStart(ITestResult result) {
    ExtentTest test = extent.createTest(result.getTestClass().getName()
                + "     @TestCase : " + result.getMethod().getMethodName());
    testReport.set(test);
  }

  public void onTestSuccess(ITestResult result) {
    String methodName = result.getMethod().getMethodName();
    String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
    Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
    testReport.get().pass(m);
  }

  public void onTestFailure(ITestResult result) {
    String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
    testReport.get().fail("<details>" + "<summary>"
                + "<b>"
                + "<font color="
                + "red>"
                + "Exception Occurred:Click to see"
                + "</font>"
                + "</b >"
                + "</summary>"
                + exceptionMessage.replaceAll(",", "<br>")
                + "</details>"
                + " \n");

//    String base64 = "";
//    Object testClass = result.getInstance();
//    WebDriver webDriver = (() testClass).getDriver();
//    if(webDriver instanceof WebDriver){
//      try {
//        base64 = ExtentReportManager.captureBase64(webDriver);
//      } catch (IOException e) {
//        throw new RuntimeException(e);
//      }
//      ExtentReportManager.getB64(webDriver);
//    }
//
//    Media med = new ScreenCapture("", "", ExtentReportManager.screenshotPath, base64);
    testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>");

    String failureLogg = "TEST CASE FAILED";
    Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
    testReport.get().log(Status.FAIL, m);
  }

  public void onTestSkipped(ITestResult result) {
    String methodName = result.getMethod().getMethodName();
    String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
    Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
    testReport.get().skip(m);
  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  public void onStart(ITestContext context) {}

  public void onFinish(ITestContext context) {

    if (extent != null) {
      extent.flush();
    }
  }
}