import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.proHar.perfoMeasure.main.LightHouse;


public class Test1 {
	// 1. Initialize the PERFORMANCE class
	public static LightHouse ap = new LightHouse();
	public static void main(String[] args) {
		// 2. set CHROMEDRIVER path
		System.setProperty("webdriver.chrome.driver","E:\\Jars\\ChromeDriver-79\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		String baseUrl = "https://mvnrepository.com/";
		driver.get(baseUrl);

		ap.Performer(driver,"TCs18", "Zomato");
		System.out.println(ap.getListedResourceElementsNow());
		driver.get("https://github.com/");
		ap.Performer(driver,"TCs19", "Swiggy");
		driver.quit();
		
		try{
			ap.SSMSAgent();
		} catch (InterruptedException e) {      }
	}
}
