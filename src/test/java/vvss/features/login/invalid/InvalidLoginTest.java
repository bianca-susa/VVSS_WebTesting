package vvss.features.login.invalid;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vvss.steps.serenity.LoginPageSteps;
import vvss.steps.serenity.LogoutPageSteps;

import static org.junit.Assert.assertEquals;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/java/vvss/features/login/invalid/InvalidData.csv")
public class InvalidLoginTest {

    private static final int TIMEOUT_SECONDS = 5;

    @Managed(uniqueSession = true)
    public WebDriver driver;

    @Steps
    public LoginPageSteps loginPage;

    @Steps
    public LogoutPageSteps logoutPage;

    // coloanele din CSV (Serenity le populează automat)
    String server;
    String user;
    String pass;
    String message;

    @Test
    public void invalidLogin_showsExpectedAlert() {
        /* Arrange */
        loginPage.go_to_Login_page();
        driver.get("https://demoblaze.com");
        loginPage.login_steps(user, pass);

        /* Act – click „Log in” din modal folosind JS */
        WebElement loginBtn = driver.findElement(
                By.cssSelector("#logInModal button.btn-primary")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);

        /* Assert – alerta apare și conține mesajul din fișierul CSV */
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_SECONDS);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        assertEquals("Textul alertei nu este cel așteptat!",
                message,
                alert.getText());

        alert.accept();               // închide alerta
    }
}
