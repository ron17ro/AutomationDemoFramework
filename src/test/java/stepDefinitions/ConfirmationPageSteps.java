package stepDefinitions;

import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pageObjects.ConfirmationPage;

public class ConfirmationPageSteps {
    TestContext testContext;
    ConfirmationPage confirmationPage;

    public ConfirmationPageSteps(TestContext context) {
        testContext = context;
        confirmationPage = testContext.getPageObjectManager().getConfirmationPage();
    }

    @Then("^verify the order details$")
    public void verify_the_order_details() {
        String productName = (String) testContext.scenarioContext.getContext(Context.PRODUCT_NAME);
        Assert.assertTrue(confirmationPage.getProductNames().stream().filter(x -> x.toLowerCase().contains(productName.toLowerCase())).findFirst().get().length() > 0);
    }
}