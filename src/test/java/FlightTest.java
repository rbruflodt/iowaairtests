import net.anthavio.phanbedder.Phanbedder;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.LocalDate;
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
        driver.findElement(By.name("depart_AMPMABC123")).sendKeys(Keys.DOWN,Keys.ENTER);
        driver.findElement(By.name("departminutesABC123")).clear();
        driver.findElement(By.name("departminutesABC123")).sendKeys("15");
        driver.findElement(By.name("aircraftABC123")).sendKeys(Keys.DOWN,Keys.DOWN,Keys.ENTER);
        driver.findElement(By.name("saveABC123")).click();
        Assert.assertEquals("Change time","1",driver.findElement(By.name("arrivehoursABC123")).getAttribute("value"));
        Assert.assertEquals("Change time","10",driver.findElement(By.name("arriveminutesABC123")).getAttribute("value"));
        Assert.assertEquals("Change time","PM",driver.findElement(By.name("arrive_AMPMABC123")).getAttribute("value"));
        Assert.assertNotEquals("Change aircraft", "ANP432",driver.findElement(By.name("aircraftABC123")).getAttribute("value") );
        driver.findElement(By.name("editABC123")).click();
        List<WebElement> frequency = driver.findElements(By.name("frequency"));
        for(WebElement r : frequency){
            if(r.getAttribute("value").equals("onceradio")){
                r.click();
            }
        }
        LocalDate date = LocalDate.now().plusDays(1);

        //String tomorrow = String.format("%02d",date.getMonthValue())+"/"+String.format("%02d",date.getDayOfMonth())+"/"+date.getYear();
        //((JavascriptExecutor) driver).executeScript("document.getElementById('once').value="+tomorrow);
        List<WebElement> flightinputs = driver.findElements(By.tagName("input"));
        for(int i = 0; i < flightinputs.size(); i++){
            if(flightinputs.get(i).isDisplayed()&&flightinputs.get(i).getAttribute("type").equals("number")){
                flightinputs.get(i).clear();
                flightinputs.get(i).sendKeys(""+200*(i+1));
            }
        }
        driver.findElement(By.id("once")).sendKeys(String.format("%02d",date.getMonthValue())+String.format("%02d",date.getDayOfMonth())+date.getYear());
        driver.findElement(By.name("editflight")).click();
        driver.findElement(By.name("signout")).click();
        driver.findElement(By.name("flighttofield")).sendKeys(Keys.DOWN,Keys.ENTER);
        driver.findElement(By.name("departfield")).sendKeys(String.format("%02d",date.getMonthValue())+String.format("%02d",date.getDayOfMonth())+date.getYear());
        driver.findElement(By.name("searchflights")).click();
       email = driver.findElement(By.name("email"));
        email.sendKeys("rachel-bruflodt@uiowa.edu");
        password = driver.findElement(By.name("password"));
        password.sendKeys("Password");
        driver.findElement(By.name("signin")).click();
        driver.findElement(By.name("bookABC123")).click();
        driver.findElement(By.id("firstname0")).sendKeys("Bruce");
        driver.findElement(By.id("lastname0")).sendKeys("Dickinson");
        driver.findElement(By.id("gender0")).sendKeys("M");
        driver.findElement(By.id("dob0")).sendKeys("12251983");
        driver.findElement(By.id("id0")).sendKeys("A123B");
        driver.findElement(By.id("creditcardtype")).sendKeys("Visa");
        driver.findElement(By.id("creditcardnumber")).sendKeys("A123456789");
        driver.findElement(By.id("creditcardexpiration")).sendKeys("12/20");
        driver.findElement(By.id("creditcardsecurity")).sendKeys("123");
        List<WebElement> h4s = driver.findElements(By.tagName("h4"));
        for(WebElement e : h4s){
            if(e.getText().contains("Your total is")){
                driver.findElement(By.id("priceconfirmation")).sendKeys(e.getText().substring(15));
            }
        }
        driver.findElement(By.name("bookflights")).click();
        driver.findElement(By.name("Reservations")).click();
        List<WebElement> entries = driver.findElements(By.tagName("td"));
        int ticketnum = 0;
        for(WebElement e : entries){
            try {
                int a = Integer.valueOf(e.getText());
                if(a>ticketnum){
                    ticketnum=a;
                }
            }catch(NumberFormatException ex){

            }
        }
        driver.findElement(By.name("signout")).click();
        email = driver.findElement(By.name("email"));
        email.sendKeys("rachelbruflodt@gmail.com");
        password = driver.findElement(By.name("password"));
        password.sendKeys("Password");
        driver.findElement(By.name("signin")).click();
        driver.findElement(By.name("Passenger Tickets")).click();
        driver.findElement(By.id("tnum")).sendKeys(""+ticketnum);
        driver.findElement(By.id("firstname")).sendKeys("Bruce");
        driver.findElement(By.id("lastname")).sendKeys("Dickinson");
        driver.findElement(By.name("searchtickets")).click();
        driver.findElement(By.name("checkinticket")).click();
        driver.findElement(By.name("numbags")).sendKeys("1");
        driver.findElement(By.name("seatnumber")).sendKeys("13A");
        driver.findElement(By.name("boardingpass")).click();
        driver.findElement(By.name("finishcheckin")).click();
        driver.findElement(By.name("signout")).click();
        email = driver.findElement(By.name("email"));
        email.sendKeys("iowaairteam10@gmail.com");
        password = driver.findElement(By.name("password"));
        password.sendKeys("Team1010");
        driver.findElement(By.name("signin")).click();
        driver.findElement(By.name("Flight Schedule")).click();
        driver.findElement(By.name("deleteABC123")).click();
        Assert.assertEquals("Delete flight",0,driver.findElements(By.name("nameABC123")).size());
        driver.close();
    }
}
