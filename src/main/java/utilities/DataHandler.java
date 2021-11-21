package utilities;

import java.util.HashMap;

import runner.RunDemoTest;

public class DataHandler {
	
	RunDemoTest run = new RunDemoTest();
	private HashMap<String, String> runTimeVariables = null;
	
	public void setRunTimeVariable(String varKey, String varData) {
		runTimeVariables = run.getRunTimeHashMap();
        runTimeVariables.put(varKey,varData);
	}
	
	public String getRunTimeVariables(String varKey){
        runTimeVariables = run.getRunTimeHashMap();
        return runTimeVariables.get(varKey);
    }

}
