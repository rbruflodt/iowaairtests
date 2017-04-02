import net.anthavio.phanbedder.Phanbedder;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rachel on 4/1/2017.
 */
public class AircraftTests {
    @Test
    public void AddAircraft(){
        File phantomjs = Phanbedder.unpack();

        System.setProperty("phantomjs.binary.path",phantomjs.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        WebDriver driver = new PhantomJSDriver();
        //ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://iowaair.us-east-1.elasticbeanstalk.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertEquals("URL match","http://iowaair.us-east-1.elasticbeanstalk.com/",driver.getCurrentUrl());
        Assert.assertEquals("Title match","Iowa Air",driver.getTitle());
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("iowaairteam10@gmail.com");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("Team1010");
        driver.findElement(By.name("signin")).click();
        driver.findElement(By.name("Aircraft")).click();
        driver.findElement(By.name("newaircraft")).click();
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        String aircraftname="";
        boolean found = false;
        for(WebElement i : inputs){
            if(i.getAttribute("name").indexOf("name")==0){
                if(!found && i.getAttribute("value").indexOf("Aircraft")==0&&!i.getAttribute("name").equals("namefield")){
                aircraftname=i.getAttribute("value");
                    found=true;
                    i.clear();
                    i.sendKeys("ABC123");
                }}
        }

        inputs=driver.findElements(By.name(aircraftname));
        int count = 0;
        found = false;
        while(count < inputs.size() && !found){
            if(inputs.get(count).getAttribute("value").equals("Save Changes")){
                inputs.get(count).click();
                found = true;
            }
            count++;
        }

        driver.findElement(By.name("namefield")).clear();

        System.out.println(driver.findElement(By.id("namefield")).getAttribute("value"));
        driver.findElement(By.name("searchaircraft")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("nameABC123")));
        Assert.assertEquals("Aircraft name change",0,driver.findElements(By.name("name"+aircraftname)).size());
        inputs=driver.findElements(By.name("ABC123"));
        count = 0;
        found = false;
        WebElement addclass = null;
        while(count < inputs.size() && !found){
            if(inputs.get(count).getAttribute("value").equals("Add Class")){
                addclass = inputs.get(count);
                found = true;
            }
            count++;
        }
        addclass.click();
        Assert.assertEquals("New Class", "Class 1", driver.findElement(By.name("classABC123Class 1")).getAttribute("value"));
        Assert.assertEquals("New Class", "0", driver.findElement(By.name("seatsABC123Class 1")).getAttribute("value"));
        driver.findElement(By.name("classABC123Class 1")).clear();
        driver.findElement(By.name("classABC123Class 1")).sendKeys("First Class");
        driver.findElement(By.name("seatsABC123Class 1")).clear();
        driver.findElement(By.name("seatsABC123Class 1")).sendKeys("50");
        count = 0;
        found = false;
        addclass = null;
        inputs=driver.findElements(By.name("ABC123"));
        while(count < inputs.size() && !found){
            if(inputs.get(count).getAttribute("value").equals("Add Class")){
                addclass = inputs.get(count);
                found = true;
            }
            count++;
        }
        addclass.click();
        Assert.assertEquals("Edit class", "First Class", driver.findElement(By.name("classABC123First Class")).getAttribute("value"));
        Assert.assertEquals("Edit class", "50", driver.findElement(By.name("seatsABC123First Class")).getAttribute("value"));
        Assert.assertEquals("New Class", "Class 2", driver.findElement(By.name("classABC123Class 2")).getAttribute("value"));
        Assert.assertEquals("New Class", "0", driver.findElement(By.name("seatsABC123Class 2")).getAttribute("value"));
        driver.findElement(By.name("classABC123Class 2")).clear();
        driver.findElement(By.name("classABC123Class 2")).sendKeys("Economy Class");
        driver.findElement(By.name("seatsABC123Class 2")).clear();
        driver.findElement(By.name("seatsABC123Class 2")).sendKeys("200");
        driver.findElement(By.name("typeABC123")).sendKeys(Keys.DOWN,Keys.DOWN,Keys.ENTER);
        count = 0;
        found = false;
        WebElement savechanges = null;
        inputs=driver.findElements(By.name("ABC123"));
        while(count < inputs.size() && !found){
            if(inputs.get(count).getAttribute("value").equals("Save Changes")){
                savechanges = inputs.get(count);
            }

            count++;
        }
        savechanges.click();
        Assert.assertEquals("Edit class", "Economy Class", driver.findElement(By.name("classABC123Economy Class")).getAttribute("value"));
        Assert.assertEquals("Edit class", "200", driver.findElement(By.name("seatsABC123Economy Class")).getAttribute("value"));
        Assert.assertEquals("Change aircraft type","Boeing 747",driver.findElement(By.name("typeABC123")).getAttribute("value"));
        driver.findElement(By.name("deleteclassABC123First Class")).click();
        Assert.assertEquals("Delete class",0,driver.findElements(By.name("classABC123First Class")).size());
        count = 0;
        found = false;
        inputs=driver.findElements(By.name("ABC123"));
        WebElement delete = null;
        while(count < inputs.size() && !found){

            if(inputs.get(count).getAttribute("value").equals("Delete")){
                delete = inputs.get(count);
            }
            count++;
        }
        delete.click();
        Assert.assertEquals("Delete aircraft",0,driver.findElements(By.name("ABC123")).size());
        driver.close();
    }

}
