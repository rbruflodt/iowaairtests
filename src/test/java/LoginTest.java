
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Rachel on 4/1/2017.
 */
public class LoginTest {

    @Test
    public void loginTests(){
        
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        driver.get("http://iowaair.us-east-1.elasticbeanstalk.com/");
        driver.manage().window().setSize( new Dimension( 1124, 850 ) );
        Assert.assertEquals(driver.getCurrentUrl(),"http://iowaair.us-east-1.elasticbeanstalk.com/");
        Assert.assertEquals(driver.getTitle(),"Iowa Air");
    }
}
