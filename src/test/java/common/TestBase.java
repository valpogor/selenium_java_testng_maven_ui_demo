package common;

import java.util.*;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.remote.*;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.annotations.Optional;
import static common.Utility.*;

public class TestBase {
	public static WebDriver driver;

	public static FirefoxOptions firefoxOption() {
		FirefoxOptions option = new FirefoxOptions();
		return option;
	}
	public static ChromeOptions chromeOption()
	{
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default.content_settings.popups", 0);
		options.setExperimentalOption("prefs", chromePrefs);
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return options;

	}

	@BeforeSuite
	@Parameters({"browser"})
	public void startDriver(@Optional("chrome") String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOption());
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(firefoxOption());

		}
		else if (browserName.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
	}
	@BeforeMethod
	public void getNavigation(){
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		System.out.println("Navigation to url: "+getUrl("prod"));
		driver.navigate().to(getUrl("prod"));
	}

	@AfterMethod
	public void failure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println(result.getName()+" fail");
		}
	}

	@AfterSuite
	public void closeDriver() {
		if (driver != null) {
			driver.quit();
		}
	}
}
