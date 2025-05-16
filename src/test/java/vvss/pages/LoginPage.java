package vvss.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

import java.util.List;

@DefaultUrl("https://demoblaze.com")
public class LoginPage extends PageObject {

    @FindBy(name="ftpserver")
    private WebElementFacade ftpServer;

    @FindBy(id="loginusername")
    private WebElementFacade username;

    @FindBy(id="loginpassword")
    private WebElementFacade password;

    @FindBy(xpath="//*[@id=\"logInModal\"]/div/div/div[3]/button[2]")
    private WebElementFacade loginButton;


    @FindBy(id="login2")
    private WebElementFacade openLoginButton;



    @FindBy(id="LoginButton1")
    private WebElementFacade saveCookies;


    public void select_server(String serverName) {
        ftpServer.clear();
        ftpServer.type(serverName);
        //ftpServer.waitUntilClickable();
        //ftpServer.selectByVisibleText(serverName); //diferenta dintre metode???
    }
    public void open_login_window() {

        openLoginButton.click();
    }

    public void enter_username(String userName) {

        username.type(userName);
    }

    public void enter_password(String password) {

        this.password.type(password);
    }

    public void click_Login() {
//        waitForCondition().until(driver -> loginButton.isVisible());

        loginButton.click();
    }


}
