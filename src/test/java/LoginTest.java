
import net.anthavio.phanbedder.Phanbedder;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

/**
 * Created by Rachel on 4/1/2017.
 */
public class LoginTest {

    @Test
    public void loginTests(){
        //System.setProperty("phantomjs.binary.path","phantomjs.exe");
        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
        PhantomJSDriver driver = new PhantomJSDriver(capabilities);
        driver.get("http://iowaair.us-east-1.elasticbeanstalk.com/");
        driver.manage().window().setSize( new Dimension( 1124, 850 ) );
        Assert.assertEquals("http://iowaair.us-east-1.elasticbeanstalk.com/",driver.getCurrentUrl());
        System.out.println(driver.getCurrentUrl());
        Assert.assertEquals("Iowa Airs",driver.getTitle());
    }
}
