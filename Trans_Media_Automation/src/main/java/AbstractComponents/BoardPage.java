package AbstractComponents;



import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class BoardPage {

    WebDriver driver;
    WebDriverWait wait;

    public BoardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    By createBoardHeader = By.xpath("//h1[normalize-space()='Create new board']");
    By boardTitleInput = By.xpath("//input[@placeholder='Add board title']");
    By createBoardBtn = By.xpath("//button[normalize-space()='Create board']");
    By addListBtn = By.xpath("//div[contains(@class,'w-list')]");
    By listTitleInput = By.xpath("//input[@placeholder='Enter list title...']");
    By confirmAddListBtn = By.xpath("//button[normalize-space()='Add list']");
   // By listTitles = By.cssSelector("#app > div > div > div > div:nth-child(2)");
  By listTitles= By.xpath("//input[@data-cy=\"list-name\"]");
  

    By deleteIcon = By.xpath("//div[@class='inline-block']//div[2]//div[1]//div[1]//button[1]//*[name()='svg']");
    By confirmDelete = By.xpath("//div[contains(text(),'Delete')]");

    public void createNewBoard(String boardName) {
        wait.until(ExpectedConditions.elementToBeClickable(createBoardHeader)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(boardTitleInput)).sendKeys(boardName);

        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createBoardBtn));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                button.click();
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    public void addList(String title) {
        wait.until(ExpectedConditions.elementToBeClickable(addListBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(listTitleInput)).sendKeys(title);
        wait.until(ExpectedConditions.elementToBeClickable(confirmAddListBtn)).click();
        
        //try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }
    
    public int getListCount() {
        // No waiting here, just return count, or add a short wait if needed
        return driver.findElements(listTitles).size();
    }


    
    public boolean waitForListCountToBe(int expectedCount) {
        try {
            return wait.until(driver -> {
                List<WebElement> lists = driver.findElements(listTitles);
                return lists.size() == expectedCount;
            });
        } catch (TimeoutException e) {
            return false;
        }
    }
    


    public void deleteFirstList() {
       wait.until(ExpectedConditions.elementToBeClickable(deleteIcon)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDelete)).click();
    	
       
        
    }
}
