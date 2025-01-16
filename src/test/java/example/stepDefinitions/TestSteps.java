package example.stepDefinitions;

import io.cucumber.java.en.*;
import org.example.framework.TestAutomationFramework;
import org.example.module.pages.DashboardPage;
import org.example.module.pages.LoginPage;
import org.example.module.pages.PaymentArrangementPage;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class TestSteps {
    private final WebDriver driver = TestAutomationFramework.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);
    private final DashboardPage dashboardPage = new DashboardPage(driver);
    private final PaymentArrangementPage paymentArrangementPage = new PaymentArrangementPage(driver);

    @Given("user logs in with valid credentials")
    public void userIsOnLoginPage() {
        TestAutomationFramework.openUrl("test.salesforce.com");
        loginPage.enterValidCredentials();
    }

    @When("user selects contracts from dropdown and further selects collection")
    public void userSelectContractsFromDropdownAndFurtherSelectCollection() throws InterruptedException {
        dashboardPage.navigateToContracts();// navigating to contracts
    }

    @And("user selects creating new payment Arrangement and enters valid data")
    public void userSelectCreatingNewPaymentArrangementAndEntersValidData() throws InterruptedException {
       paymentArrangementPage.createNewPaymentArrangement();
       paymentArrangementPage.fillPaymentArrangementDetails();
       paymentArrangementPage.clickSubmitButton();

    }

    @When("user tries to create payment collection for contract which already has a payment collection")
    public void paymentArrangemenRecreation() throws InterruptedException
    {
        paymentArrangementPage.createNewPaymentArrangement();

    }

    @Then("user encounters an error")
    public void userEncountersAnError() throws InterruptedException {
        Thread.sleep(2000);
        // Verify the error message
        boolean isErrorDisplayed = paymentArrangementPage.isExistingArrangementErrorDisplayed();
        assertTrue("Expected error message is not displayed.", isErrorDisplayed);

    }
}
