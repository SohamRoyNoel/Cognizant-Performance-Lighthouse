# PerformanceAccessJar-V1 : Demo Testing Code

import org.openqa.selenium.WebDriver; <br />
import org.openqa.selenium.chrome.ChromeDriver;<br />
<br />
import com.proHar.perfoMeasure.main.App;<br />

public class Test1 {<br />
	// 1. Initialize the PERFORMANCE class<br />
	public static App ap = new App();<br />
	public static void main(String[] args) {<br />
		// 2. set CHROMEDRIVER path<br />
		System.setProperty("webdriver.chrome.driver","E:\\Jars\\ChromeDriver-79\\chromedriver.exe");<br />
		WebDriver driver = new ChromeDriver();<br />
		driver.manage().window().maximize();<br />
		String baseUrl = "https://www.johnhancock.com/index.html";<br />
		driver.get(baseUrl);<br />
		/* 3. Call PERFORMER method to record performance at any point of time<br />
                           Performer method takes 3 parameters : (<driver instance>, <Testcase Name>, <Application Name>) where Test case name SHOULD BE UNIQUE for different flows but same for SAME FLOW<br />
	                           Example : for flow like [a-> b-> c->] = TC1<br />
	                                            [a-> b-> c-> d] = TC2<br />
		 */<br />
		ap.Performer(driver,"TCs8", "XYZ");<br />
		// 4. OPTIONAL : call the method getListedResourceElementsNow() to get all the listed elements till now<br />
		System.out.println(ap.getListedResourceElementsNow());<br />
		driver.get("https://www.johnhancock.com/investing.html");<br />
		ap.Performer(driver,"TCs9", "XYZ");<br />
		driver.quit();<br />
		// AZURE Migration<br />
		//	            ap.AzureAgent("perfo", "Performances", "Candy@perfo", "Noelle1234");<br />
		// Excel Report<br />
		//	            try {<br />
		//	                  ap.ExcelAgent();<br />
		//	                  System.out.println("Excel Migrated");<br />
		//	            } catch (InterruptedException e) {      }<br />
		// 5. Call AccessAgenet() to push all the values into access database : (PATH) : \\ENTSSERVER85\Cognizant\Soham\My Projects\Performence Access Databases\FinalDatabaseEntry\PerformanceLightHouse-0.1-Beta.accdb<br />
		try{<br />
			ap.AccessAgent();<br />
		} catch (InterruptedException e) {      }<br />
	}<br />
}<br />
<br />
