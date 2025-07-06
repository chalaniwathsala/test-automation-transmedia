//package WebAutomation;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//import java.time.Duration;
//import java.util.List;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//public class Standaloan {
//
//	public static void main(String[] args) {
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver= new ChromeDriver();
//		driver.get("https://rahulshettyacademy.com/client");
//		driver.manage().window().maximize();
//		driver.findElement(By.id("userEmail")).sendKeys("practice788@gmail.com");
//		driver.findElement(By.id("userPassword")).sendKeys("ABCd@12345");
//		driver.findElement(By.id("login")).click();
//		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
//		WebElement prod = products.stream().filter(product-> product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
//		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
//		
//
//	}
//
//}


package WebAutomation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Standalone {
    public static void main(String[] args) {
    	//String productName="ADIDAS ORIGINAL";
    	
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:3000/");
        driver.manage().window().maximize();
        WebDriverWait waits = new WebDriverWait(driver, java.time.Duration.ofSeconds(50));
        waits.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[normalize-space()='Create new board']"))).click();
        waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Add board title']"))).sendKeys("Test1");
        //waits.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Create board']"))).click();
        
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement element = waits.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Create board']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }

       // waits.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Create board']"))).click();
       
        
        // Wait for "Add list" button (update this selector if incorrect)
        List<String> listTitles = Arrays.asList("List1", "List2");
        for (String title : listTitles) {
            waits.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'w-list')]"))).click(); 
            waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter list title...']"))).sendKeys(title);
            waits.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add list']"))).click();
        }

        System.out.println("✅ All lists added successfully.");

        //Delete
     driver.findElement(By.xpath("//div[@class='inline-block']//div[2]//div[1]//div[1]//button[1]//*[name()='svg']")).click();
     driver.findElement(By.xpath("//div[@class='block py-1 px-2 pt-2 text-sm text-gray-700 hover:bg-gray1 active:bg-gray2 cursor-pointer text-red-600']")).click();


     driver.quit();
        
       
    

    }
    
    }

