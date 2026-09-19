package base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.ExtentReporter;

public class Listeners implements ITestListener {

    ExtentTest test;

    ExtentReports extent = ExtentReporter.getReportObject();

    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();


    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());

        WebDriver driver = null;

        try {
            driver = (WebDriver) result.getTestClass()
                    .getRealClass()
                    .getField("driver")
                    .get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (driver != null) {
            String filePath = null;

            try {
                filePath = getScreenshot(
                        result.getMethod().getMethodName(),
                        driver
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (filePath != null) {
                extentTest.get().addScreenCaptureFromPath(
                        filePath,
                        result.getMethod().getMethodName()
                );
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test skipped");
    }
    @Override
    public void onFinish(ITestContext context) {
      extent.flush();
    }
    
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

        TakesScreenshot screenshot = (TakesScreenshot) driver;

        File source = screenshot.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir")
                + "/reports/"
                + testCaseName
                + ".png";

        File destinationFile = new File(destination);

        destinationFile.getParentFile().mkdirs();

        FileUtils.copyFile(source, destinationFile);

        return destination;
    }
    
    
}