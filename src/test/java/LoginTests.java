
import net.anthavio.phanbedder.Phanbedder;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.List;

/**
 * Created by Rachel on 4/1/2017.
 */
public class LoginTests {

    @Test
    public void UserLogin(){
        //System.setProperty("phantomjs.binary.path","phantomjs.exe");
//        File phantomjs = Phanbedder.unpack();
//        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
//        PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        //ChromeDriver driver = new ChromeDriver();
        //System.setProperty("phantomjs.binary.path",phantomjs.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        //WebDriver driver = new HtmlUnitDriver();
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://iowaair.us-east-1.elasticbeanstalk.com/");
        driver.manage().window().setSize( new Dimension( 1124, 850 ) );
        Assert.assertEquals("URL match","http://iowaair.us-east-1.elasticbeanstalk.com/",driver.getCurrentUrl());
        Assert.assertEquals("Title match","Iowa Air",driver.getTitle());
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("rachel-bruflodt@uiowa.edu");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("Password");
        driver.findElement(By.name("signin")).click();
        List<WebElement> h3s=driver.findElements(By.tagName("h3"));
        for(WebElement w : h3s){
            if(w.getText().indexOf("Welcome, ")==0){
                Assert.assertEquals("Log in message","Welcome, Rachel Bruflodt!",w.getText());
            }
            System.out.println(w.getLocation());
        }
        List<WebElement> tablinks=driver.findElements(By.className("tablinks"));
        Assert.assertEquals("User tabs",2,tablinks.size());
        Assert.assertEquals("First tab","Flights",tablinks.get(0).getText());
        Assert.assertEquals("Second tab","Reservations",tablinks.get(1).getText());
        driver.findElement(By.name("signout")).click();
        Assert.assertEquals("Signed out","signin",driver.findElements(By.className("signinbutton")).get(0).getAttribute("name"));
        driver.close();
    }

    @Test
    public void InvalidLogin(){
        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
        PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        driver.get("http://iowaair.us-east-1.elasticbeanstalk.com/");
        driver.manage().window().setSize( new Dimension( 1124, 850 ) );
        Assert.assertEquals("URL match","http://iowaair.us-east-1.elasticbeanstalk.com/",driver.getCurrentUrl());
        Assert.assertEquals("Title match","Iowa Air",driver.getTitle());
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("rachel-bruflodt@uiowa.edu");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("password");
        driver.findElement(By.name("signin")).click();
        Assert.assertEquals("Password fail","Invalid email or password.",driver.findElement(By.id("loginmessage")));
        driver.close();
    }
}