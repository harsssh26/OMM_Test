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
    private final By typeDropdown = By.xpath("//div[@class='payment-arrangement-details']//div[1]//div[1]//select[1]");
    private final By frequencyDropdown = By.xpath("//select[@name='frequency']");
    private final By termField = By.xpath("//input[@name='term']");
    private final By repaymentAmountField = By.xpath("//input[@name='amountPerFrequency']");
    private final By startDateField = By.xpath("//input[@name='startDate']");
    private final By submitButton = By.xpath("//button[@title='Primary action' and text()='Submit']");
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
    private double getDelinquentAmount() {
        String delinquentAmountText = utility.getText(delinquentAmountField).replace("$", "").replace(",", "").trim();
        return Double.parseDouble(delinquentAmountText);
    }

    public void fillPaymentArrangementDetails() {
        double delinquentAmount = getDelinquentAmount(); // Fetch the delinquent amount dynamically

        // Select Type -> Randomly choose between "Inbound PTP" and "Outbound PTP"
        String[] types = {"Inbound PTP", "Outbound PTP"};
        String type = types[faker.number().numberBetween(0, types.length)];
        utility.selectByVisibleText(typeDropdown, type);

        // Determine Frequency and Terms Based on Delinquent Amount
        String frequency;
        int terms;

        if (delinquentAmount <= 500) {
            frequency = "Weekly";
            terms = faker.number().numberBetween(2, 5); // Randomly choose between 2 and 5 terms
        } else if (delinquentAmount <= 1000) {
            frequency = faker.options().option("Fortnightly", "Monthly");
            terms = faker.number().numberBetween(2, 6); // Randomly choose between 2 and 6 terms
        } else {
            frequency = "Monthly";
            terms = faker.number().numberBetween(3, 12); // Randomly choose between 3 and 12 terms
        }
        utility.selectByVisibleText(frequencyDropdown, frequency);

        // Calculate Repayment Amount
        double repaymentAmount = delinquentAmount / terms;
        utility.enterText(termField, String.valueOf(terms));
        utility.enterText(repaymentAmountField, String.format("%.2f", repaymentAmount));

        // Calculate Total Amount (repayment amount * terms)
        double totalAmount = repaymentAmount * terms;
        utility.enterText(By.xpath("//input[@name='totalAmount']"), String.format("%.2f", totalAmount)); // Update Total Amount field if editable

        // Set Start Date -> Tomorrow's Date
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String startDate = String.format("%02d/%02d/%d", tomorrow.getDayOfMonth(), tomorrow.getMonthValue(), tomorrow.getYear());
        utility.enterText(startDateField, startDate);

        // Log the generated data
        System.out.println("Generated Data for Payment Arrangement:");
        System.out.println("Delinquent Amount: " + delinquentAmount);
        System.out.println("Type: " + type);
        System.out.println("Frequency: " + frequency);
        System.out.println("Terms: " + terms);
        System.out.println("Repayment Amount: " + repaymentAmount);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Start Date: " + startDate);
    }





    public void clickSubmitButton() {
        try {
            // Wait for the Submit button to be clickable
            WebElement submitButtonElement = wait.until(ExpectedConditions.elementToBeClickable(submitButton));

            // Check if the button is enabled
            if (submitButtonElement.isEnabled()) {
                submitButtonElement.click(); // Click the Submit button
                System.out.println("Submit button clicked successfully.");
            } else {
                System.err.println("Submit button is disabled. Please check the form for errors.");
            }
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
