package vvss.features.scenariu;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vvss.steps.serenity.AccountPageSteps;
import vvss.steps.serenity.LoginPageSteps;
import vvss.steps.serenity.LogoutPageSteps;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("C:/Users/bibis/IdeaProjects/VVSS_WebUITesting/src/test/java/vvss/features/login/valid/ValidData.csv")
public class ScenariuTest {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public LoginPageSteps loginPage;

    @Steps
    public AccountPageSteps accountPage;

    @Steps
    public LogoutPageSteps logoutPage;

    String server, user, pass, message;

    @Test
    public void login() {
        loginPage.go_to_Login_page();
        webdriver.get("https://demoblaze.com");
        loginPage.login_steps(user, pass);

        loginPage.click_Login();

        try {
            WebDriverWait wait = new WebDriverWait(webdriver, 5);
            WebElement welcomeElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))
            );

            String expectedText = "Welcome " + user;
            String actualText = welcomeElement.getText();

            assertEquals("Mesajul de bun venit nu corespunde", expectedText, actualText);
        } catch (TimeoutException e) {
            throw new AssertionError("Elementul cu id 'nameofuser' nu a apărut – login eșuat?");
        }

        // Navigare către Laptops și click pe produs
        webdriver.findElement(By.linkText("Laptops")).click();
        webdriver.findElement(By.linkText("Sony vaio i5")).click();

        // Click pe "Add to cart" folosind JavaScriptExecutor
        WebElement addToCart = webdriver.findElement(By.linkText("Add to cart"));
        JavascriptExecutor js = (JavascriptExecutor) webdriver;
        js.executeScript("arguments[0].click();", addToCart);

        // Așteaptă alerta
        try {
            WebDriverWait waitAlert = new WebDriverWait(webdriver,5);
            Alert alert = waitAlert.until(ExpectedConditions.alertIsPresent());

            String actualAlertText = alert.getText();
            alert.accept();

            loginPage.should_to_see_alert(actualAlertText, "Product added.");
        } catch (TimeoutException e) {
            throw new AssertionError("Nu a apărut alerta 'Product added.' în timp util.");

        }

        // în „Cart”
        webdriver.findElement(By.id("cartur"))      // pe Demoblaze link-ul Cart are id-ul „cartur”
                .click();

//   un produs in tabel
        WebDriverWait waitCart = new WebDriverWait(webdriver, 5);
        waitCart.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tbodyid")));

        List<WebElement> cartRows = webdriver.findElements(By.cssSelector("#tbodyid > tr"));
        assertFalse("Cosul ar trebui să contina un produs!", cartRows.isEmpty());

// ștergem primul produs din tabel
        WebElement deleteLink = cartRows.get(0).findElement(By.linkText("Delete"));
        deleteLink.click();

//verificam că nu mai exista produse in tabel
        waitCart.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#tbodyid > tr"), 0));

        List<WebElement> cartRowsAfterDelete = webdriver.findElements(By.cssSelector("#tbodyid > tr"));
        assertTrue("Cosul ar trebui să fie gol după stergere!", cartRowsAfterDelete.isEmpty());

    }

}