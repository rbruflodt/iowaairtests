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
//        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//        capabilities.setJavascriptEnabled(true);
//        capabilities.setCapability("takesScreenshot", true);
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());

        System.setProperty("phantomjs.binary.path",phantomjs.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        //WebDriver driver = new HtmlUnitDriver();
        ChromeDriver driver = new ChromeDriver();
        //driver.manage().window().maximize();
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
            }}
        }

        driver.findElement(By.name("name"+aircraftname)).clear();
        driver.findElement(By.name("name"+aircraftname)).sendKeys("ABC123");
        driver.findElement(By.name(aircraftname)).click();
        Assert.assertEquals("Aircraft name change",0,driver.findElements(By.name("name"+aircraftname)).size());
        driver.close();
    }

    @Test
    public void EditAircraft(){

    }

    @Test
    public void DeleteAircraft(){

    }
}
