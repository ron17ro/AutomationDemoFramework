package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProductListingPage {
    WebDriver driver;

    public ProductListingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    private Select colorSelection;
    private Select sizeSelection;
    private WebElement cart;
    @FindBy(how = How.CSS, using = "button.single_add_to_cart_button")
    private WebElement btn_AddToCart;
//    @FindBy(how = How.CSS, using = ".cart-button")
    private WebElement btn_Cart;
    WebDriverWait wait;
    Actions action;

    public void clickOn_AddToCart() {
        btn_AddToCart.click();
    }

    public void select_color_size(){
        colorSelection = new Select(driver.findElement(By.id("pa_color")));

        sizeSelection = new Select(driver.findElement(By.id("pa_size")));

        if(colorSelection != null){
            colorSelection.selectByIndex(1);
        }
        if(sizeSelection != null){
            sizeSelection.selectByIndex(1);
        }
    }
    public void getColorSize(){
        List<WebElement> e = colorSelection.getOptions();
        for (int i = 0; i < e.size(); i++) {
            System.out.println(e.get(i).getText());
        }
        e = sizeSelection.getOptions();
        for (int i = 0; i < e.size(); i++) {
            System.out.println(e.get(i).getText());
        }

    }

    public void click_on_cart(){
        btn_Cart = driver.findElement(By.linkText("View cart"));
//        wait = new WebDriverWait(driver, 5);
//        action = new Actions(driver);
//        action.moveToElement(btn_Cart);
//        wait.until(ExpectedConditions.visibilityOf(btn_Cart));
//        wait.until(ExpectedConditions.elementToBeClickable(btn_Cart));
        btn_Cart.click();
    }
    public String getProductName() {
        return driver.findElement(By.cssSelector(".product_title")).getText();
    }
}