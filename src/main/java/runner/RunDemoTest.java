package runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import utilities.ConfigFileReader;
import utilities.FileHandler;

@CucumberOptions(
        features = "src/test/java/features/",
        glue = {"steps"},
        tags = "@WISHLIST",
        plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class RunDemoTest extends AbstractTestNGCucumberTests {

	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static String configFile;
	private static ExtentReports extentRep = null;
    private static String screenshotPath;
    protected static ThreadLocal<ExtentTest> testScenario = new ThreadLocal<>();
    public static String cucumbertag = "";
    protected static ThreadLocal<HashMap<String,String>> runTimeVariables = new ThreadLocal<>();
	
	ConfigFileReader config = new ConfigFileReader();
	FileHandler fileObj = new FileHandler();
    int scenario = 1;
	
	static {
        try {
            configFile = getConfigFilePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@BeforeClass
	public void beforeRun() {
		String repFoldName = fileObj.currDate();
        String reportPath = fileObj.createFolder(System.getProperty("user.dir") + config.getConfig("reportPath") + repFoldName );
        screenshotPath = fileObj.createFolder(reportPath + "/screenshots");
        extentRep = new ExtentReports(reportPath + "/report.html",true);
        extentRep.loadConfig(new File(System.getProperty("user.dir") + config.getConfig("extentReportConfigPath")));
        cucumbertag = readCucumberTag(this.getClass());
        runTimeVariables.set(new HashMap<String, String>());
	}
	
	@BeforeMethod
	public void beforeHook() {		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + config.getConfig("chromeDriverPath"));
		ChromeDriver chromeDriver = new ChromeDriver();		
		driver.set(chromeDriver);
		testScenario.set(extentRep.startTest("Test", "desc"));
        scenario = scenario + 1;
	}
	
	public WebDriver getDriver() {
		return driver.get();
	}

	@AfterMethod
	public void afterHook() {
		extentRep.endTest(testScenario.get());
		getDriver().quit();
	}
	
	@AfterClass
    public void afterRun(){
        extentRep.flush();
        extentRep.close();
    }
	
	public static String readCucumberTag(Class<?> clazz) {
        String tags = "NULL";
        CucumberOptions co = clazz.getAnnotation(CucumberOptions.class);
        if (co != null) {
            tags = Arrays.asList(co.tags()).get(0);
        }
        return tags;
    }
	
	public static String getConfigFilePath() throws IOException {
        final String[] configPath = {""};
        try (Stream<Path> walkStream = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
                if (f.toString().endsWith("configuration.properties")) {
                    configPath[0] = f.toString();
                }
            });
        }
        return configPath[0];
    }
	
	public ExtentTest getScenario(){
        return testScenario.get();
    }
	
	public String getScreenshotPath() { return screenshotPath;}
	
	public HashMap<String, String> getRunTimeHashMap() { return runTimeVariables.get(); }	

}
