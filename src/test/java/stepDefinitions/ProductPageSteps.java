package stepDefinitions;

import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.When;
import pageObjects.ProductListingPage;

public class ProductPageSteps {

    TestContext testContext;
    ProductListingPage productListingPage;

    public ProductPageSteps(TestContext context) {
        testContext = context;
        productListingPage = testContext.getPageObjectManager().getProductListingPage();
    }

    @When("^select product properties and add to cart$")
    public void choose_to_buy_the_first_item() {
        String productName = productListingPage.getProductName();
        testContext.scenarioContext.setContext(Context.PRODUCT_NAME, productName.toString());
        productListingPage.select_color_size();
        productListingPage.getColorSize();
        productListingPage.clickOn_AddToCart();
        productListingPage.click_on_cart();
    }

}