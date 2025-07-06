package WebAutomation;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;



public class BoardTest {

    WebDriver driver;
    AbstractComponents.BoardPage boardPage;

    @BeforeClass
    public void setup() {
        driver = DriverFactory.getDriver();
        driver.get("http://localhost:3000/");
        boardPage = new AbstractComponents.BoardPage(driver);
    }

    @Test(priority = 1)
    public void createBoardAndAddLists() {
        boardPage.createNewBoard("Test1");

        boardPage.addList("List1");
        boardPage.addList("List2");

        boolean listsAdded = boardPage.waitForListCountToBe(2);
        Assert.assertTrue(listsAdded, "Expected 2 lists after creation but found: " + boardPage.getListCount());

        System.out.println("Board created and 2 lists added.");
    }

    @Test(priority = 2)
    public void deleteListAndValidate() {
        boardPage.deleteFirstList();

//       boolean listDeleted = boardPage.waitForListCountToBe(1);
//       Assert.assertTrue(listDeleted, "Expected 1 list after deletion but found: " + boardPage.getListCount());
//
//        System.out.println("One list deleted successfully.");
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
