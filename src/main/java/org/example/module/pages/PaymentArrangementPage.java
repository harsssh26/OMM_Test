package org.example.module.pages;

import com.github.javafaker.Faker;
import org.example.framework.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

public class PaymentArrangementPage {
    private final Faker faker = new Faker();
    private final Utility utility;
    private final WebDriver driver;
    private final WebDriverWait wait;

    public PaymentArrangementPage(WebDriver driver) {
        this.utility = new Utility(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private final By typeDropdown = By.xpath("//select[@name='type']");
    private final By frequencyDropdown = By.xpath("//select[@name='frequency']");
    private final By termField = By.xpath("//input[@name='term']");
    private final By repaymentAmountField = By.xpath("//input[@name='amountPerFrequency']");
    private final By startDateField = By.xpath("//input[@name='startDate']");
    private final By submitButton = By.xpath("//button[@title='Primary action']");
    private final By cancelButton = By.xpath("//button[@id='Cancel']");
    private final By createPaymentArrangementDropdown = By.xpath("//lightning-primitive-icon[@variant='bare']");
    private final By createPaymentArrangementOption = By.xpath("//span[normalize-space()='Create Payment Arrangement']");
    private final By successToastMessage = By.xpath("//div[@class='toast-message' and contains(text(), 'Payment Arrangement created successfully')]");
    private final By delinquentAmountField = By.xpath("//label[contains(text(), 'Delinquent Amount')]/following-sibling::div//span");

    // Actions

    /**
     * Navigate to the "Create Payment Arrangement" form by clicking on the dropdown and selecting the option.
     */
    public void createNewPaymentArrangement() {
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(createPaymentArrangementDropdown));
            utility.jsClick(createPaymentArrangementDropdown);
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(createPaymentArrangementOption));
            utility.jsClick(createPaymentArrangementOption);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException occurred: " + e.getMessage());
        }
    }

    /**
     * Fetch the Delinquent Amount dynamically.
     */
    private double getDelinquentAmount() {
        String delinquentAmountText = utility.getText(delinquentAmountField).replace("$", "").replace(",", "").trim();
        return Double.parseDouble(delinquentAmountText);
    }

    /**
     * Fill in the Payment Arrangement Details.
     */
    public void fillPaymentArrangementDetails() {
        double delinquentAmount = getDelinquentAmount(); // Fetch the delinquent amount dynamically

        // Select Type -> Inbound PTP
        utility.selectByVisibleText(typeDropdown, "Inbound PTP");

        // Select Frequency -> Weekly
        utility.selectByVisibleText(frequencyDropdown, "Weekly");

        // Set Term
        int terms = 3; // Adjust this dynamically or based on test case
        utility.enterText(termField, String.valueOf(terms));

        // Calculate Repayment Amount
        double repaymentAmount = delinquentAmount / terms;
        utility.enterText(repaymentAmountField, String.format("%.2f", repaymentAmount));

        // Set Start Date
        String startDate = "17/01/" + LocalDate.now().getYear();
        utility.enterText(startDateField, startDate);

        System.out.println("Details entered: Delinquent Amount=" + delinquentAmount + ", Terms=" + terms +
                ", Repayment Amount=" + repaymentAmount + ", Start Date=" + startDate);
    }


    public void clickSubmitButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
            System.out.println("Submit button clicked successfully.");
        } catch (Exception e) {
            System.err.println("Failed to click Submit button: " + e.getMessage());
        }
    }

    public void clickCancelButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        utility.jsClick(cancelButton);
    }

    public boolean isSubmissionSuccessful() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successToastMessage));
        WebElement toastMessage = driver.findElement(successToastMessage);
        return toastMessage.isDisplayed();
    }

    public void printGeneratedData() {
        System.out.println("Generated Data for Payment Arrangement:");
        System.out.println("Type: " + utility.getText(typeDropdown));
        System.out.println("Frequency: " + utility.getText(frequencyDropdown));
        System.out.println("Term: " + utility.getText(termField));
        System.out.println("Repayment Amount: " + utility.getText(repaymentAmountField));
        System.out.println("Start Date: " + utility.getText(startDateField));
    }
}
