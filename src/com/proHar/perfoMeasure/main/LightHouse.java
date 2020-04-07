package com.proHar.perfoMeasure.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.proHar.perfoMeasure.main.ssmsModules.SSMSDataMigrationCredentials;
import com.proHar.perfoMeasure.main.ssmsModules.SSMSDataMigrationUtils;
import com.proHar.perfoMeasure.main.ssmsModules.SSMSUtils;

/*
 * Performance Entry
 * */
public class LightHouse {

	public void Performer(WebDriver driver, String Test_Scenario_Name, String ProjectName) {
		// DirecTory Allocations
		String workingPath = System.getProperty("user.dir");
		String getUserName = System.getProperty("user.name");

		// Create Folder
		String workingFOLDERpath = workingPath + "\\Output";
		File targetPath = new File(workingFOLDERpath);
		if (!targetPath.exists()) {
			targetPath.mkdir();
		}


		JavascriptExecutor js = (JavascriptExecutor)driver;  
		String base = js.executeScript("return document.domain;").toString();

		/*
		 * Set TestCase Name and UserName
		 * on the TestCases table, with an unique ID
		 * */

		int SSMStestCaseID = SSMSUtils.setTestCaseName(Test_Scenario_Name,ProjectName);

		/*

		 * Set ApplicationName with ID into Application_Name
		 *  table to check if that exists..!
		 *  If YES then return ID else INSERT Domain Name and return Current ID
		 * */
		int SSMSappname_id = SSMSUtils.getApplicationID(base, SSMStestCaseID);

		/*
		 * Set the Page Name with new ID,
		 * Fetch the Current PAGE NAME :: Collect it from VALUE PARSER class
		 * PASS the appname_id :: insert it as (Page_ID|Page_NAME|Application_ID)
		 * */
		String getCurrentURL = driver.getCurrentUrl();
		int SSMSpager_id = SSMSUtils.getPagerId(getCurrentURL, SSMSappname_id, SSMStestCaseID);

		// Performance Methods
		try {
				ValueParser.ResourceAnalyser(driver, SSMSappname_id, SSMSpager_id, SSMStestCaseID);
				ValueParser.NavigationAnalyser(driver, SSMSappname_id, SSMSpager_id, SSMStestCaseID);					
		} catch (InterruptedException e) {   
			e.printStackTrace();
		}

	}

	// Access Database generation class
	public void SSMSAgent() throws InterruptedException {
		Connection con = SSMSDataMigrationCredentials.getSSMSConnection();
		SSMSDataMigrationUtils sdm = new SSMSDataMigrationUtils();
		try{
			sdm.SSMSDatabaseManagerAgent(con);
		} catch (Exception e) {   
			e.printStackTrace();

		}
	}

	// get values at any time
	public List<String> getListedNavigationElementsNow(){
		List<String> storedNavValue = ValueParser.navHolder;
		return storedNavValue;
	}

	public List<String> getListedResourceElementsNow(){
		List<String> storedResValue = ValueParser.navHolder;
		return storedResValue;
	}

	private static String getNAVlocation(String baseFolderPath) {
		String navigationPath = baseFolderPath + "\\navTemp.txt";
		File navFile = new File(navigationPath);
		createFile(navFile);
		return navigationPath;
	}

	private static String getRESlocation(String baseFolderPath) {

		String resourcesPath = baseFolderPath + "\\resTemp.txt";
		File resFile = new File(resourcesPath);
		createFile(resFile);
		return resourcesPath;
	}

	private static void createFile(File navFile) {
		try {
			navFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}