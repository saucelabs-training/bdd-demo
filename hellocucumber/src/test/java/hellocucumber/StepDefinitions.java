package hellocucumber;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class StepDefinitions {
    private RemoteWebDriver driver;
    private String sauceUsername = System.getenv("SAUCE_USERNAME");
    private String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");

    @After()
    public void tearDown(Scenario scenario){
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (scenario.getStatus().equals("testSuccessful") ? "passed" : "failed"));
        driver.quit();
    }

    @Given("a user opens a browser")
    public void a_user_opens_a_browser() throws MalformedURLException {
        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setCapability(CapabilityType.PLATFORM_NAME, "windows 10");
        chromeOpts.setCapability(CapabilityType.BROWSER_VERSION, "latest");

        MutableCapabilities sauceOpts = setSauceOptions();

        MutableCapabilities capabilities = setBrowserOptions(chromeOpts, sauceOpts);

        driver = setDriver(capabilities);
    }

    @Given("a user opens the latest chrome on mac")
    public void aUserOpensTheLatestChromeOnMac() throws MalformedURLException {
        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setCapability(CapabilityType.PLATFORM_NAME, "macOS 10.13");
        chromeOpts.setCapability(CapabilityType.BROWSER_VERSION, "latest");

        MutableCapabilities sauceOpts = setSauceOptions();

        MutableCapabilities capabilities = setBrowserOptions(chromeOpts, sauceOpts);

        driver = setDriver(capabilities);
    }

    @Given("a user opens chrome one version behind on mac")
    public void aUserOpensChromeOneVersionBehindOnMac() throws MalformedURLException {
        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setCapability(CapabilityType.PLATFORM_NAME, "macOS 10.13");
        chromeOpts.setCapability(CapabilityType.BROWSER_VERSION, "latest-1");

        MutableCapabilities sauceOpts = setSauceOptions();

        MutableCapabilities capabilities = setBrowserOptions(chromeOpts, sauceOpts);

        driver = setDriver(capabilities);
    }

    private RemoteWebDriver setDriver(MutableCapabilities capabilities) throws MalformedURLException {
        String sauceUrl = "https://ondemand.saucelabs.com/wd/hub";
        URL url = new URL(sauceUrl);
        return new RemoteWebDriver(url, capabilities);
    }

    private MutableCapabilities setBrowserOptions(ChromeOptions chromeOpts, MutableCapabilities sauceOpts) {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOpts);
        capabilities.setCapability("sauce:options", sauceOpts);
        return capabilities;
    }

    private MutableCapabilities setSauceOptions() {
        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", sauceUsername);
        sauceOpts.setCapability("accessKey", sauceAccessKey);
        sauceOpts.setCapability("build", "best-practices");
        sauceOpts.setCapability("tags", "['best-practices', 'best-practices']");
        return sauceOpts;
    }

    @When("they navigate to the Sauce Demo home page")
    public void they_navigate_to_the_Sauce_Demo_home_page() {
        new LoginPage(driver).open();
    }

    @Then("it's visible")
    public void it_s_visible() {
        assertTrue("login page should be visible", new LoginPage(driver).isVisible());
    }




}
