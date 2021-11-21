package automation;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.LogStatus;

import runner.RunDemoTest;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigFileReader;
import utilities.FileHandler;
import utilities.ReportHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleniumFunctions {

    WebDriver driver = null;
    ConfigFileReader config = new ConfigFileReader();
    ReportHandler report = new ReportHandler();
    RunDemoTest run = new RunDemoTest();
    FileHandler fh = new FileHandler();

    public boolean elementExists(By browserObject){
        WebElement element = null;
        Boolean elemDisp = false;
        try{
            element = getElement(browserObject);
            if(element != null){
                elemDisp = true;
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
        return elemDisp;
    }

    public WebElement getElement(By browserObject) {
        WebElement element = null;
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(browserObject)));
            if (driver.findElements(browserObject).size() != 0) {
                //wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
                //wait.until(ExpectedConditions.visibilityOf(driver.findElement(browserObject)));
                element = driver.findElement(browserObject);
                //getAndSwitchFrame(browserObject);
            }
        } catch (Exception e) {
            //report.logError(e.getMessage());
        }
        return element;
    }

    public WebElement getElement(WebElement element) {
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            if (element.isDisplayed()) {
                wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
                wait.until(ExpectedConditions.visibilityOf(element));
            }
        } catch (Exception e) {
            //report.logError(e.getMessage());
        }
        return element;
    }

    public List<WebElement> getElements(By browserObject) {
        List<WebElement> elements = null;
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            //wait.until(ExpectedConditions.visibilityOf(getElement(browserObject)));
            wait.until(ExpectedConditions.presenceOfElementLocated(browserObject));
            if (getElement(browserObject) != null) {
                elements = driver.findElements(browserObject);
            }
        } catch (Exception e) {
            //report.logError(e.getMessage());
        }
        return elements;
    }
    
    public int getElementSize(By browserObject) {
        List<WebElement> elements = null;
        int elementSize = 0;
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            //wait.until(ExpectedConditions.visibilityOf(getElement(browserObject)));
            wait.until(ExpectedConditions.presenceOfElementLocated(browserObject));
            if (getElement(browserObject) != null) {
                elements = driver.findElements(browserObject);
                elementSize = elements.size();
            }
        } catch (Exception e) {
            //report.logError(e.getMessage());
        }
        return elementSize;
    }
    
    public List<String> getElementsByValues(By browserObject) {
        List<WebElement> elements = null;
        List<String> elementValues = null;
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            //wait.until(ExpectedConditions.visibilityOf(getElement(browserObject)));
            //wait.until(ExpectedConditions.presenceOfElementLocated(browserObject));
            if (getElements(browserObject) != null) {
                elements = getElements(browserObject);
                System.out.println("Element Size - " + elements.size());
                for(WebElement element: elements) {
                	elementValues.add(element.getText());
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            report.logError(e.getLocalizedMessage());
        }
        return elementValues;
    }
    
    public List<String> getElementsByAttributes(By browserObject, String attributeName) {
        List<WebElement> elements = null;
        List<String> elementValues = null;
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            //wait.until(ExpectedConditions.visibilityOf(getElement(browserObject)));
            wait.until(ExpectedConditions.presenceOfElementLocated(browserObject));
            if (getElement(browserObject) != null) {
                elements = driver.findElements(browserObject);
                for(WebElement element: elements) {
                	elementValues.add(element.getAttribute(attributeName));
                }
            }            
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
        return elementValues;
    }
    
    public String getElementByValue(By browserObject){
        String attributeValue = "";
        try{
            attributeValue = getElement(browserObject).getText();
        }catch (Exception e){
        	e.printStackTrace();
            report.logError(e.getMessage());
        }
        return attributeValue;
    }

    public String getAttribute(By browserObject, String attributeName){
        String attributeValue = "";
        try{
            attributeValue = getElement(browserObject).getAttribute(attributeName);
        }catch (Exception e){
            report.logError(e.getMessage());
        }
        return attributeValue;
    }
    
    public String getAttribute(WebElement element, String attributeName){
        String attributeValue = "";
        try{
            attributeValue = getElement(element).getAttribute(attributeName);
        }catch (Exception e){
            report.logError(e.getMessage());
        }
        return attributeValue;
    }

    public String getScreenshot() {
        String destination = null;
        try {
            driver = run.getDriver();
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            //after execution, you could see a folder "FailedTestsScreenshots" under src folder
            destination = run.getScreenshotPath() + "\\" +dateName + ".png";
            File finalDestination = new File(destination);
            Files.copy(source, finalDestination);
            report.logReportWithScreenshot(finalDestination.getAbsolutePath());
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
        return destination;
    }

    public void launchBrowser(String url) {
        try {       	        	
            driver = run.getDriver();
            driver.get(url);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Long.parseLong(config.getConfig("waitSeconds")),TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(Long.parseLong(config.getConfig("pageloadWait")),TimeUnit.SECONDS);
            report.logReport(LogStatus.PASS, "Browser launched successfully and navigated to url - " + url);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void quitBrowser() {
        try {
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            driver = run.getDriver();
            Set<String> s = driver.getWindowHandles();
            String subWindowHandler = "", mainWindowHandler = "";
            Iterator<String> iterator = s.iterator();
            if (iterator.hasNext()) {
                mainWindowHandler = iterator.next();
            }
            while (iterator.hasNext()){
                subWindowHandler = iterator.next();
                driver.switchTo().window(subWindowHandler);
                driver.close();
            }
            driver.switchTo().window(mainWindowHandler);
            //closeDriver();
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public String getText(By browserObject) {
        String txt = null;
        try {
            driver = run.getDriver();
            txt = getElement(browserObject).getText();
        } catch (Exception e) {
            txt = "";
        }
        return txt;
    }

    public String getText(WebElement element) {
        String txt = null;
        try {
            driver = run.getDriver();
            txt = getElement(element).getAttribute("textContent");
        } catch (Exception e) {
            txt = "";
        }
        return txt;
    }

    public void click(By browserObject, String logDesc) {
        try {
            driver = run.getDriver();
            getElement(browserObject).click();
            report.logInfo("Clicked on - " + logDesc);
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
        }
        catch (Exception e) {
            report.logError(e.getMessage());
        }
    }
    
    public void clickNoLog(By browserObject) {
        try {
            driver = run.getDriver();
            driver.findElement(browserObject).click();
        }
        catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void click(WebElement element, String logDesc) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            element.click();
            report.logInfo("Clicked on - " + logDesc);
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
        }
        catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void clickifavailable(By browserObject, String logDesc) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            if (driver.findElements(browserObject).size() != 0) {
                getElement(browserObject).click();
                report.logInfo("Clicked on - " + logDesc);
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void clickifavailable(WebElement browserObject, String logDesc) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            if (browserObject.isDisplayed()) {
                getElement(browserObject).click();
                report.logInfo("Clicked on - " + logDesc);
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }
    
    public void mouseHover(By browserObject) {
    	try {
    		driver = run.getDriver();
    		Actions action = new Actions(driver);
    		action.moveToElement(driver.findElement(browserObject)).perform();
    	}catch(Exception e) {
    		e.printStackTrace();
    		report.logError(e.getMessage());
    	}
    }
    
    public void scrollToElement(By browserObject) {
    	try {
    		driver = run.getDriver();
    		WebElement element = driver.findElement(browserObject);
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    	}catch(Exception e) {
    		e.printStackTrace();
    		report.logError(e.getMessage());
    	}
    }
    
    public int getObjectCounts(By browserObject) {
    	List<WebElement> elements = null;
    	int elementsSize = 0;
    	try {
    		driver = run.getDriver();
    		elements = driver.findElements(browserObject);   
    		elementsSize = elements.size();
    	}catch(Exception e) {
    		e.printStackTrace();
    		report.logError(e.getMessage());
    	}
    	return elementsSize;
    }

    public void closeWindow() {
        try {
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            driver = run.getDriver();
            driver.close();
            Set<String> s = driver.getWindowHandles();
            String subWindowHandler = "";
            Iterator<String> iterator = s.iterator();
            //System.out.println("Windows Opened - " + s.size());
            while (iterator.hasNext()){
                subWindowHandler = iterator.next();
            }
            driver.switchTo().window(subWindowHandler);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void validateText(By browserObject, String expectedText, String logDescription) {
        try {
            driver = run.getDriver();
            String txt = getElement(browserObject).getText();
            if (txt.equalsIgnoreCase(expectedText)) {
                report.logReport(LogStatus.PASS, logDescription + "is successful");
            } else {
                report.logReport(LogStatus.FAIL, logDescription + "is failed");
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void sleepsometime(long slp){
        try {
            Thread.sleep(slp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

