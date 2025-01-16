package org.example.module.pages;
import com.github.javafaker.Faker;
import org.example.framework.TestAutomationFramework;
import org.example.framework.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class DashboardPage {

    private final Utility utility;
    private final WebDriver driver;
    private final WebDriverWait wait;

    public DashboardPage(WebDriver driver) {

        this.utility = new Utility(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Wait for up to 20 seconds
    }

    public void navigateToContracts() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Show Navigation Menu']//lightning-primitive-icon[@variant='bare']")));
        utility.jsClick(By.xpath("//button[@title='Show Navigation Menu']//lightning-primitive-icon[@variant='bare']"));
        // clicking on the dropdown button on dashboard page

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='menuLabel slds-listbox__option-text slds-listbox__option-text_entity'][normalize-space()='Contracts']")));
        utility.jsClick(By.xpath("//span[@class='menuLabel slds-listbox__option-text slds-listbox__option-text_entity'][normalize-space()='Contracts']"));
        //clicking on the contratcs from the dropdown menu

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='LAI-00102207']")));
        utility.jsClick(By.xpath("//a[@title='LAI-00102207']"));
        //clicking on the specific contract[hardcoded]

    }


}
