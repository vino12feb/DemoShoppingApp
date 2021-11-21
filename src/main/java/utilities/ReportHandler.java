package utilities;

import com.relevantcodes.extentreports.LogStatus;

import runner.RunDemoTest;

import java.io.File;

public class ReportHandler {

    RunDemoTest run = new RunDemoTest();

    public void logReport(LogStatus logStatus, String logDescription){
        run.getScenario().log(logStatus, logDescription);
    }

    public void updateScenarioDescription(String scenarioName){
        run.getScenario().getTest().setName(scenarioName);
        run.getScenario().setDescription(scenarioName);
    }

    public void logReportWithScreenshot(String attachFilePath){
        File f1 = new File(attachFilePath);
        File f2 = new File(f1.getParent());
        run.getScenario().log(LogStatus.INFO,run.getScenario().addScreenCapture(".\\" + f2.getName() + "\\" + f1.getName()));
    }

    public void logTestExtentReportWithAttachment(String logDescription, String attachFilePath){
        run.getScenario().log(LogStatus.INFO, logDescription + " : " + run.getScenario().addScreenCapture(attachFilePath));
    }
    
    public void logPass(String logDescription) {
    	run.getScenario().log(LogStatus.PASS, logDescription);
    }
    
    public void logFail(String logDescription) {
    	run.getScenario().log(LogStatus.FAIL, logDescription);
    }

    public void logInfo(String logDescription){
        run.getScenario().log(LogStatus.INFO, logDescription);
    }

    public void logError(String errorDescription){
        run.getScenario().log(LogStatus.ERROR, errorDescription);
    }

}
