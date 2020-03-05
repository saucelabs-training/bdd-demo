package hellocucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {
    private WebDriver driver;

    @After
    public void cleanup()
    {
        if(driver != null)
        {
            driver.quit();
        }
    }

    @Given("a user opens a browser")
    public void a_user_opens_a_browser() throws MalformedURLException {
        driver = createDriver();
    }

    @When("the user goes to the login page")
    public void the_user_goes_to_the_login_page() {
        driver.get("https://www.saucedemo.com/");
    }

    @Then("the user sees the page loaded")
    public void the_user_sees_the_page_loaded() {
        assertEquals("https://www.saucedemo.com/",driver.getCurrentUrl() );
    }

    private WebDriver createDriver() throws MalformedURLException {
        String sauceUsername = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", sauceUsername);
        sauceOpts.setCapability("accessKey", sauceAccessKey);
        //sauceOpts.setCapability("name", testName.getMethodName());
        //sauceOpts.setCapability("build", buildName);
        sauceOpts.setCapability("tags", "'no-cucumber'");

        MutableCapabilities browserOptions = new MutableCapabilities();
        browserOptions.setCapability(CapabilityType.PLATFORM_NAME, "Windows 10");
        browserOptions.setCapability(CapabilityType.BROWSER_VERSION, "latest");
        browserOptions.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        browserOptions.setCapability("sauce:options", sauceOpts);

        String sauceUrl = "https://ondemand.saucelabs.com/wd/hub";
        URL url = new URL(sauceUrl);
        return new RemoteWebDriver(url, browserOptions);
    }
}
