package org.example.framework;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.file.Files;
import java.io.File;
import java.time.Duration;
import java.util.List;

public class Utility {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;
    private final Actions action;

    public Utility(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
        this.action = new Actions(driver);
    }

    public void clickElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (Exception e) {
            System.out.println("Standard click failed. Attempting JavaScript click.");
            jsClick(locator);
        }
    }

    public void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    // Scroll to element using a By locator
    public void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void jsClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        js.executeScript("arguments[0].click();", element);
    }

    public void jsClick(WebElement element)
    {
        js.executeScript("arguments[0].click();", element);
    }

//    public void switchToLightningExperience() {
//        By switchToLightning = By.cssSelector("a.switch-to-lightning");
//
//        try {
//            // Check if the "Switch to Lightning Experience" button is visible
//            if (verifyElementPresence(switchToLightning)) {
//                System.out.println("Switching to Lightning Experience...");
//                jsClick(switchToLightning); // Use JavaScript click to avoid issues
//                waitForPageToLoad();       // Wait for the page to load
//            } else {
//                System.out.println("Already in Lightning Experience or button not visible.");
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to switch to Lightning Experience: " + e.getMessage());
//        }
//    }


    public void performAction(By locator, String actionName) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            if ("click".equalsIgnoreCase(actionName)) {
                action.moveToElement(element).click().perform();
            } else if ("contextClick".equalsIgnoreCase(actionName)) {
                action.moveToElement(element).contextClick().perform();
            }
        } catch (Exception e) {
            System.out.println("Standard action failed, attempting JavaScript click.");
            jsClick(locator);
        }
    }

    public String getElementValue(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getAttribute("value");
    }
    public void selectByVisibleText(By locator, String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }


    public boolean verifyElementPresence(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Unable to locate element with locator "+locator);
            return false;
        }
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    public boolean isElementEnabled(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.isEnabled();
    }

    public void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }

    public void takeScreenshot(String filePath) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            Files.copy(srcFile.toPath(), destFile.toPath());
            System.out.println("Screenshot saved at: " + filePath);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    public List<WebElement> getElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }
}