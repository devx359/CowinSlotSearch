package scripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC1 {
	
	WebDriver driver;
	@BeforeTest
	public void startup()
	{
		System.out.println("Welcome back player");
		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver_90.exe");//Set env property
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("incognito");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
	}
	
	@Test
	public void login_to_cowin() throws InterruptedException
	{
		driver.get("https://www.cowin.gov.in/");
		Thread.sleep(3000);

		WebElement ele = driver.findElement(By.xpath("//div[@class='status-switch']"));
		Rectangle rect = ele.getRect();
		ele.click();
		
		new Actions(driver).moveToElement(ele, (rect.getWidth()-50), (rect.getHeight()/2)).click().build().perform();
		Thread.sleep(2000);
		
		//new Actions(driver).click(driver.findElement(By.xpath("//*[contains(text(),'Select State')]")));
		
		
	}
	
	@AfterTest
	public void teardown()
	{
		driver.quit();
	}

}
