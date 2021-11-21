Web Automation - Demo Shopping Application

Develop a web test solution that automates below simple test scenario, composed as BDD scenarios. The target for the test is the dummy web site:
https://testscriptdemo.com

Acceptance Criteria:

GIVEN I add four different products to my wishlist
WHEN I view my wishlist table
THEN I find total four selected items in my wishlist
WHEN I search for lowest price product
AND I am able to add the lowest price item to my cart
THEN I am able to verify the item in my cart


Pre-Requisite

* Eclipse/Intellij should be installed on user's system
* Java JDK 1.8 should be available on user's system
* JAVA_HOME environment variables should have been added on user's system
* Chrome driver should be downloaded from https://chromedriver.chromium.org/downloads and kept it under /resouces directory (Ensure chrome driver version matches the Chrome installed on user's system)
* Ensure all the dependencies are downloaded from maven repo


Tech Stacks

* Java
* Maven
* Selenium
* Cucumber
* TestNG
* Extent Report


Framework Structure
.
|---src
|   |---main
|       |---java
|		|---automation
|			|---SeleniumFunctions.java  - Java functions for selenium related operations
|		|---runner
|			|---RunDemoTest.java	  - Java runner class for execution
|		|---utilities
|			|---ConfigFileReader.java	  - Java functions to read the configuration file
|			|---DataHandler.java	  - Java functions to handle test data in runtime
|			|---FileHandler.java	  - Java functions to perform file operations
|			|---ReportHandler.java	  - Java functions for handling reports
|			|---Run.xml		  - Test NG xml for triggering the runner class
|
|---src
|   |---test
|	|---java
|		|---application
|			|---WishlistFunctions.java  - Java business functions specific to wishlist
|		|---data
|			|---Testdata.java		  - Test data enum variables
|		|---features
|			|---Wishlist.features	  - Cucumber feature file for wishlist feature
|		|---objects
|			|---Cart.java		- Cart Page Objects
|			|---Header.java		- Header Objects
|			|---Home.java		- Home Page Objects
|			|---Wishlist.java		- Wishlist Page Objects
|		|---steps
|			|---WishlistSteps.java	- Step definitions for wishlist features
|
|---configs
|	|---configuration.properties		- Property file to refer global configurations
|	|---extent-config.xml			- Property file for Extent reports
|
|---reports					- Report directory for custom reports
|
|---resouces
|	|---chromedriver.exe			- Chrome driver for execution
|
|---target					- Default output directory for Maven
|
|---pom.xml		- XML file that contains information about the project and configuration details


Steps To Execute

* Import project on Eclipse (File -> Import -> Existing Maven Project)
* Create a run configuration (Run -> Run Configuration)
	a) Right click on Maven Build -> New Configuration
	b) Update below details,
		Name 			: ShoppingDemo
		Base directory	: ${workspace_loc:/DemoProject}
		Goals			: clean test
	c) Click Run to begin execution
* After execution, navigate to /reports directory and navigate further into latest timestamp folder. Check report.html file for the detailed report.


Key Customization

* Report output path can be configured in configuration.properties file
* Timeout seconds can be configured in configuration.properties file
* Extent report theme can be customized in extent-config.xml file
* App url can be configured in configuration.properties file
