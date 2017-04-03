import net.anthavio.phanbedder.Phanbedder;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rachel on 4/3/2017.
 */
public class FlightTest {
    @Test
    public void flightTest(){
        File phantomjs = Phanbedder.unpack();

        System.setProperty("phantomjs.binary.path",phantomjs.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        //WebDriver driver = new PhantomJSDriver();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://iowaair.us-east-1.elasticbeanstalk.com/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertEquals("URL match","http://iowaair.us-east-1.elasticbeanstalk.com/",driver.getCurrentUrl());
        Assert.assertEquals("Title match","Iowa Air",driver.getTitle());
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("iowaairteam10@gmail.com");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("Team1010");
        driver.findElement(By.name("signin")).click();
        driver.findElement(By.name("Flight Schedule")).click();
        driver.findElement(By.name("newflight")).click();
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        String flightname="";
        boolean found = false;
        for(WebElement i : inputs){
            if(i.getAttribute("name").indexOf("name")==0){
                if(!found && i.getAttribute("value").indexOf("Flight")==0){
                    flightname=i.getAttribute("value");
                    found=true;
                    i.clear();
                    i.sendKeys("ABC123");
                }}
        }
        driver.findElement(By.name("save"+flightname)).click();
        Assert.assertEquals("Change flight name",0,driver.findElements(By.name("name"+flightname)).size());
        driver.findElement(By.name("flightidfield")).clear();
        driver.findElement(By.name("searchflights")).click();
        driver.findElement(By.name("arrive_cityABC123")).sendKeys(Keys.DOWN,Keys.ENTER);
        driver.findElement(By.name("arrivehoursABC123")).clear();
        driver.findElement(By.name("arrivehoursABC123")).sendKeys("1");
        driver.findElement(By.name("arriveminutesABC123")).clear();
        driver.findElement(By.name("arriveminutesABC123")).sendKeys("10");
        driver.findElement(By.name("arrive_AMPMABC123")).sendKeys(Keys.DOWN,Keys.ENTER);
        driver.findElement(By.name("aircraftABC123")).sendKeys(Keys.DOWN,Keys.DOWN,Keys.ENTER);
        driver.findElement(By.name("saveABC123")).click();
        Assert.assertEquals("Change time","1",driver.findElement(By.name("arrivehoursABC123")).getAttribute("value"));
        Assert.assertEquals("Change time","10",driver.findElement(By.name("arriveminutesABC123")).getAttribute("value"));
        Assert.assertEquals("Change time","PM",driver.findElement(By.name("arrive_AMPMABC123")).getAttribute("value"));
        Assert.assertNotEquals("Change aircraft", "ANP432",driver.findElement(By.name("aircraftABC123")).getAttribute("value") );
        driver.findElement(By.name("deleteABC123")).click();
        Assert.assertEquals("Delete flight",0,driver.findElements(By.name("nameABC123")).size());
        driver.close();
    }
}
