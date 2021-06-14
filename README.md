# CucumberAutomationDemoFramework
An End to End Selenium Cucumber Framework put together from https://www.toolsqa.com/

Project structure
```
├───configs
│       Configuration.properties
│       extent-config.xml
│
├───src
│   ├───main
│   │   ├───java
│   │   └───resources
│   └───test
│       ├───java
│       │   ├───cucumber
│       │   │       ScenarioContext.java
│       │   │       TestContext.java
│       │   │
│       │   ├───dataProvider
│       │   │       ConfigFileReader.java
│       │   │       JsonDataReader.java
│       │   │
│       │   ├───enums
│       │   │       Context.java
│       │   │       DriverType.java
│       │   │       EnvironmentType.java
│       │   │
│       │   ├───managers
│       │   │       FileReaderManager.java
│       │   │       PageObjectManager.java
│       │   │       WebDriverManager.java
│       │   │
│       │   ├───pageObjects
│       │   │       CartPage.java
│       │   │       CheckoutPage.java
│       │   │       ConfirmationPage.java
│       │   │       HomePage.java
│       │   │       ProductListingPage.java
│       │   │
│       │   ├───runners
│       │   │       RunnerTest.java
│       │   │
│       │   ├───stepDefinitions
│       │   │       CartPageSteps.java
│       │   │       CheckoutPageSteps.java
│       │   │       ConfirmationPageSteps.java
│       │   │       HomePageSteps.java
│       │   │       ProductPageSteps.java
│       │   │
│       │   ├───testDataTypes
│       │   │       Customer.java
│       │   │
│       │   └───Wait
│       │           Wait.java
│       │
│       └───resources
│           ├───features
│           │       End2End_Test.feature
│           │
│           ├───geckodriver
│           │       geckodriver.exe
│           │
│           └───testDataResources
│                   Customer.json
```


# __Test to Automate__

_"Ritika visits ToolsQA Demo Website for the first time and SEARCH for Dress. She selects the first product and goes to product page. 
She successfully adds it to the bag. She continues to Cart Page from mini cart icon at the top right corner. Then  she moves forward
to Checkout page. She choose to be an ANONYMOUS USER (Not Registering) completes email and address details. She selects FREE delivery,
and places the order using CHECK payment method with Billing & Delivery address as same."_

## Feature File ##
Translating the test into Cucumber BDD steps, expressed in Gherkin language:
```
│       └───resources
│           ├───features
│           │       End2End_Test.feature
```
```
  @tag
    Feature: Automated End2End Tests
    Description: test end 2 end integration
  
	@tag1
	Scenario: Customer place an order by purchasing an item from scratch
		Given user is on Home Page
		When he search for "dress"
		And choose to buy the first item
		And select product properties and add to cart
		And moves to checkout from mini cart
		And enter "<customer>" personal details on checkout page
		And place the order
		Then verify the order details
```

## Steps Definitions ##
All BDD steps must translate to test code which will be implemented under stepDefinition package
```
│       │   ├───stepDefinitions
│       │   │       CartPageSteps.java
│       │   │       CheckoutPageSteps.java
│       │   │       ConfirmationPageSteps.java
│       │   │       HomePageSteps.java
│       │   │       ProductPageSteps.java
```

 ## Page Object Models ##
Page Object Design Pattern using [Selenium Page Factory](https://www.toolsqa.com/selenium-cucumber-framework/page-object-design-pattern-with-selenium-pagefactory-in-cucumber/)
is used to to prevent code duplication and improve code maintenability by creating a Model of each page. This Model is represented 
by single classes containing all the required page elements for your tests. 

To execute the test case in question, the following Page models will be required:

* Home Page
* Product Listing Page
* Cart Page
* Checkout Page
* Confirmation Page


```
│       │   ├───pageObjects
│       │   │       CartPage.java
│       │   │       CheckoutPage.java
│       │   │       ConfirmationPage.java
│       │   │       HomePage.java
│       │   │       ProductListingPage.java
```
PageFactory is used to Initializes Elements inside Page class without having to use 'FindElement' or 'FindElements', commonly used in Selenium, 
but instead @FindBy Annotation is used from TestNG. @FindBy can accept TagName, PartialLinkText, Name, LinkText, Id, Css, ClassName, XPath as attributes. 

```
    @FindAll(@FindBy(how = How.CSS, using = ".noo-product-inner"))
    private List<WebElement> prd_List;
```

## Managers ##

Manager classes are using Singleton Design Pattern 

__What is Singleton Design Pattern?__
The Singleton’s purpose is to control object creation, limiting the number of objects to only one. Since there is only one 
Singleton instance, any instance fields of a Singleton will occur only once per class, just like static fields.

__How to implement Singleton Pattern?__
To implement Singleton pattern, we have different approaches but all of them have the following common concepts.

* Private constructor to restrict instantiation of the class from other classes.
* Private static variable of the same class that is the only instance of the class.
* Public static method that returns the instance of the class, this is the global access point for outer world to get the 
instance of the singleton class.

```
│       │   ├───managers
│       │   │       FileReaderManager.java
│       │   │       PageObjectManager.java
│       │   │       WebDriverManager.java
```
Page Object Manager class is used to create a single instance of a Page Model class to be used by the Cucumber Steps Definion classes. 
It uses the Singleton Design Pattern and has two objectives:

* To create an Object of Page Class only if the object is null.
* To supply the already created object if it is not null 

### PageObjectManager.java ###
```
import org.openqa.selenium.WebDriver; 
import pageObjects.CartPage; 
import pageObjects.CheckoutPage; 
import pageObjects.ConfirmationPage; 
import pageObjects.HomePage; 
import pageObjects.ProductListingPage;  
 
public class PageObjectManager { 
 private WebDriver driver; 
 private ProductListingPage productListingPage; 
 private CartPage cartPage; 
 private HomePage homePage; 
 private CheckoutPage checkoutPage; 
 private ConfirmationPage confirmationPage; 
  
 public PageObjectManager(WebDriver driver) { 
 this.driver = driver; 
 }
 
  
 public HomePage getHomePage(){
  return (homePage == null) ? homePage = new HomePage(driver) : homePage; 
 }
 
  
 public ProductListingPage getProductListingPage() { 
 return (productListingPage == null) ? productListingPage = new ProductListingPage(driver) : productListingPage;
 }
 
 
 public CartPage getCartPage() { 
 return (cartPage == null) ? cartPage = new CartPage(driver) : cartPage; 
 } 
 
 public CheckoutPage getCheckoutPage() { 
 return (checkoutPage == null) ? checkoutPage = new CheckoutPage(driver) : checkoutPage; 
 }
}
```


### FileReaderManager.java ###
```
import dataProviders.ConfigFileReader;
 
public class FileReaderManager {
 
 private static FileReaderManager fileReaderManager = new FileReaderManager();
 private static ConfigFileReader configFileReader;
 
 private FileReaderManager() {
 }
 
 public static FileReaderManager getInstance( ) {
       return fileReaderManager;
 }
 
 public ConfigFileReader getConfigReader() {
 return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
 }
}
```

### Web Driver Manager ###

The only responsibility of the WebDriver Manager Factory is to get us the WebDriver, when required. To accomplish this we will be doing the below activities:

* Specify new WebDriver Properties in the Configuration File
* Create Enums for DriverType and EnvironmentType
```
package enums;
 
public enum DriverType {
 FIREFOX,
 CHROME,
 INTERNETEXPLORER
}
```
```
ckage enums;
 
public enum EnvironmentType {
 LOCAL,
 REMOTE,
}
```
* Write new Method to read the above properties
* Design a WebDriver Manager
* Configuration File
```
environment=local
browser=firefox
windowMaximize=true
driverPath=src\\test\\resources\\geckodriver\\geckodriver.exe
implicitlyWait=20
url=http://shop.demoqa.com
```
### WebDriverManager.java ###
```
import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverManager {
    private WebDriver driver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";

    public WebDriverManager() {
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
    }

    public WebDriver getDriver() {
        if(driver == null) driver = createDriver();
        return driver;
    }

    private WebDriver createDriver() {
        switch (environmentType) {
            case LOCAL : driver = createLocalDriver();
                break;
            case REMOTE : driver = createRemoteDriver();
                break;
        }
        return driver;
    }

    private WebDriver createRemoteDriver() {
        throw new RuntimeException("RemoteWebDriver is not yet implemented");
    }

    private WebDriver createLocalDriver() {
        switch (driverType) {
            case FIREFOX :
                System.setProperty(FIREFOX_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getDriverPath());
                driver = new FirefoxDriver();
                break;
            case CHROME :
                System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getDriverPath());
                driver = new ChromeDriver();
                break;
            case INTERNETEXPLORER : driver = new InternetExplorerDriver();
                break;
        }

        if(FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
            driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
        return driver;
    }

    public void closeDriver() {
        driver.close();
        driver.quit();
    }

}
```

## Configuration Files ##

```
├───configs
│       Configuration.properties
│       extent-config.xml
```

### Configuration.properties ###
```
environment=local
browser=firefox
windowMaximize=true
driverPath=src\\test\\resources\\geckodriver\\geckodriver.exe
implicitlyWait=20
url=http://shop.demoqa.com
testDataResourcePath=src/test/resources/testDataResources/
reportConfigPath=/configs/extent-config.xml
```

### ConfigFileReader.class ###
This class is used to read the configuration file. This will later be used in the StepDefinition classes to avoid code duplication.
Maintaining a single configuration file has the advantages of avoiding recompiling the project  
```
package dataProvider;

import enums.DriverType;
import enums.EnvironmentType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final String propertyFilePath= "configs//Configuration.properties";


    public ConfigFileReader(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getDriverPath(){
        String driverPath = properties.getProperty("driverPath");
        if(driverPath!= null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if(url != null) return url;
        else throw new RuntimeException("url not specified in the Configuration.properties file.");
    }
    public DriverType getBrowser() {
        String browserName = properties.getProperty("browser");
        if(browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
        else if(browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
        else if(browserName.equals("iexplorer")) return DriverType.INTERNETEXPLORER;
        else throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    public EnvironmentType getEnvironment() {
        String environmentName = properties.getProperty("environment");
        if(environmentName == null || environmentName.equalsIgnoreCase("local")) return EnvironmentType.LOCAL;
        else if(environmentName.equals("remote")) return EnvironmentType.REMOTE;
        else throw new RuntimeException("Environment Type Key value in Configuration.properties is not matched : " + environmentName);
    }

    public Boolean getBrowserWindowSize() {
        String windowSize = properties.getProperty("windowMaximize");
        if(windowSize != null) return Boolean.valueOf(windowSize);
        return true;
    }

    public String getTestDataResourcePath(){
        String testDataResourcePath = properties.getProperty("testDataResourcePath");
        if(testDataResourcePath!= null) return testDataResourcePath;
        else throw new RuntimeException("Test Data Resource Path not specified in the Configuration.properties file for the Key:testDataResourcePath");
    }
    public String getReportConfigPath(){
        String reportConfigPath = properties.getProperty("reportConfigPath");
        if(reportConfigPath!= null) return reportConfigPath;
        else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }

```
## Test context sharing between Cucumber step definitions with Dependency Injection (DI) Containers using PicoContainer ##

When steps become interconnected and interdependent, context sharing is required to execute scenario steps one after another. Keeeping all steps in a single file will become impractical, sharing objects between classes will be addresesed by a Test Context Manager.

__What is PicoContainer?__
It’s a tiny library written by Paul Hammant in 2003-2004. It is pretty awesome because it’s so little and simple:

* PicoContainer doesn’t require any configuration
* PicoContainer doesn’t require your classes to use any APIs such as the horrible @Inject – just use constructors
* PicoContainer really only has a single feature – it instantiates objects

Simply hand it some classes and it will instantiate each one, correctly wired together via their constructors. That’s it. Cucumber scans your classes with step definitions in them, passes them to PicoContainer, then asks it to create new instances for every scenario.

### TestContext.java ###

```
package cucumber;
 
import managers.PageObjectManager;
import managers.WebDriverManager;
 
public class TestContext {
 private WebDriverManager webDriverManager;
 private PageObjectManager pageObjectManager;
 
 public TestContext(){
 webDriverManager = new WebDriverManager();
 pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
 }
 
 public WebDriverManager getWebDriverManager() {
 return webDriverManager;
 }
 
 public PageObjectManager getPageObjectManager() {
 return pageObjectManager;
 }
 
}
```


PicoContainer can create and inject an object in the step classes constructor. For example, the Home Page Steps class will instantiate the TestContext object in the constructor, so the code to instantiate a WebDriverManager and PageObjectManager will no longer be required. 


### HomePageSteps.java ###
```
package stepDefinitions;
 
import cucumber.TestContext;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import pageObjects.HomePage;
 
public class HomePageSteps {
 
 TestContext testContext;
 HomePage homePage;
 
 public HomePageSteps(TestContext context) {
 testContext = context;
 homePage = testContext.getPageObjectManager().getHomePage();
 }
 
 @Given("^user is on Home Page$")
 public void user_is_on_Home_Page(){
 homePage.navigateTo_HomePage(); 
 }
 
 @When("^he search for \"([^\"]*)\"$")
 public void he_search_for(String product)  {
 homePage.perform_Search(product);
 }
 
}
```

## Data Driven Testing using Json with Cucumber ##

[Data-Driven Testing](https://www.toolsqa.com/selenium-cucumber-framework/data-driven-testing-using-json-with-cucumber/) is a technique which allows test scripts to run using multiple values from external sources. For example a customer order, has many fields which can be difficult to test with many types of input. In this case, a data source provider will pass multiple set of values, even altered input variables to check for errors in the form.

JSON has the advantage of not requiring additional dependencies like Excel which require MS Ofiice to be installed on the machine where the tests are ruuning. 
Cucumber Examples and test cases are a good source of data for extracting Customer Details. 
The new feature file will include variables that will use the new JSON Data Source. 

Scenario Outline must be specified instead of Scenario in order to use placeholder which will use the new Data Source.
```
Feature: Automated End2End Tests
Description: The purpose of this feature is to test End 2 End integration.
 
Scenario Outline: Customer place an order by purchasing an item from search 
 Given user is on Home Page
 When he search for "dress"
 And choose to buy the first item
 And moves to checkout from mini cart
 And enter "<customer>" personal details on checkout page
 And select same delivery address
 And select payment method as "check" payment
 And place the order
Examples:
 |customer|
 |Lakshay|
 ```

The JSON file which will be used with our End to End test will contain the following data:
```
[ 
 {
 "firstName": "Lakshay",
 "lastName": "Sharma",
 "age": 35,
 "emailAddress": "Lakshay@Gmail.com",
 "address": {
 "streetAddress": "Shalimar Bagh",
 "city": "Delhi",
 "postCode": "110088",
 "state": "Delhi",
 "country": "India",
 "county": "Delhi"
 },
 "phoneNumber": {
 "home": "012345678",
 "mob": "0987654321"
 }
 },
 {
 "firstName": "Virender",
 "lastName": "Singh",
 "age": 35,
 "emailAddress": "Virender@Gmail.com",
 "address": {
 "streetAddress": "Palam Vihar",
 "city": "Gurgaon",
 "postCode": "122345",
 "state": "Haryana",
 "country": "India",
 "county": "Delhi"
 },
 "phoneNumber": {
 "home": "012345678",
 "mob": "0987654321"
 }
 }
]
```
This JSON file will be deserialized using a Java class object which will contain the arguments required to store the values in the JSON file.
A POJO class will be constructed using the website http://www.jsonschema2pojo.org/

### Customer.java ###
```
package testDataTypes;
 
public class Customer {
   public String firstName;
   public String lastName;
   public int age;
   public String emailAddress;
   public Address address;
   public PhoneNumber phoneNumber; 
   
   public class Address {
   public String streetAddress;
   public String city;
   public String postCode;
   public String state;
   public String country;
   public String county;
   }
 
   public class PhoneNumber {
   public String home;
   public String mob;
   }
}
```
A new line (testDataResourcePath) containing the JSON location will be added in Configuration.properties and a new method testDataResourcePath() will be creatd in the Config Reader class to read the new entry from the Configuation. properties file.
Next, a JSON Reader class will be created using GSON, an opern source library which uses Java Reflection to provide simple methods to convert JSON to java.

### JsonDataReader.java ###
```
package dataProviders;
	import java.io.BufferedReader;
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.Arrays;
	import java.util.List;
	import com.google.gson.Gson;
	import managers.FileReaderManager;
	import testDataTypes.Customer;
	
public class JsonDataReader {
	private final String customerFilePath = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath() + "Customer.json";
	private List<Customer> customerList;
	
	public JsonDataReader(){
		customerList = getCustomerData();
	}
	
	private List<Customer> getCustomerData() {
		Gson gson = new Gson();
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(customerFilePath));
			Customer[] customers = gson.fromJson(bufferReader, Customer[].class);
			return Arrays.asList(customers);
		}catch(FileNotFoundException e) {
			throw new RuntimeException("Json file not found at path : " + customerFilePath);
		}finally {
			try { if(bufferReader != null) bufferReader.close();}
			catch (IOException ignore) {}
		}
	}
		
	public final Customer getCustomerByName(String customerName){
			 return customerList.stream().filter(x -> x.firstName.equalsIgnoreCase(customerName)).findAny().get();
	}
	

}

```
Our new data source will be mostly used in the CheckoutPage Steps file to pass Test Data to checkout Page Objects.
```
@When("^enter \\\"(.*)\\\" personal details on checkout page$")
public void enter_personal_details_on_checkout_page(String customerName){
 Customer customer = FileReaderManager.getInstance().getJsonReader().getCustomerByName(customerName);
 checkoutPage.fill_PersonalDetails(customer); 
}
```
The CheckoutPage.java class will have a method to pass data from our JSON file
```
 public void fill_PersonalDetails(Customer customer) {
 enter_Name(customer.firstName);
 enter_LastName(customer.lastName);
 enter_Phone(customer.phoneNumber.mob);
 enter_Email(customer.emailAddress);
 enter_City(customer.address.city);
 enter_Address(customer.address.streetAddress);
 enter_PostCode(customer.address.postCode);
 select_Country(customer.address.country);
 select_County(customer.address.county); 
 }
 ```


## Test Runner ##

A [JUnit Test Runner](https://www.toolsqa.com/cucumber/junit-test-runner-class/) will be the starting point to executing all tests
```
│       │   ├───runners
│       │   │       RunnerTest.java
```

