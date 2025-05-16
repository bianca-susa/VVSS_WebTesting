package vvss.features.login.valid;

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

import static org.junit.Assert.assertEquals;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("C:/Users/bibis/IdeaProjects/VVSS_WebUITesting/src/test/java/vvss/features/login/valid/ValidData.csv")
public class ValidLoginTest {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public LoginPageSteps loginPage;

    @Steps
    public AccountPageSteps accountPage;

    @Steps
    public LogoutPageSteps logoutPage;

    String server,user,pass,message;

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
            throw new AssertionError("Elementul cu id 'nameofuser' nu a aparut ");
        }

    }

}