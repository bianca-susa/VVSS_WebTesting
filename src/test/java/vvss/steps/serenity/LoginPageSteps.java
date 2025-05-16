package vvss.steps.serenity;

import vvss.pages.LoginPage;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class LoginPageSteps {

    LoginPage loginPage;

    @Step
    public void select_server_name(String server) {

        loginPage.select_server(server);
    }

    @Step
    public void enter_username(String username) {

        loginPage.enter_username(username);
    }
    @Step
    public void open_login_window() {

        loginPage.open_login_window();
    }
    @Step
    public void enter_password(String password) {

        loginPage.enter_password(password);
    }

    @Step
    public void click_Login() {

        loginPage.click_Login();
    }

    @Step
    public void go_to_Login_page() {

        loginPage.open();
    }



    @Step
    public void login_steps(String username, String password) {
        open_login_window();
        enter_username(username);
        enter_password(password);
    }


    @Step
    public void should_to_see_alert(String alert, String message) {
        assert message.equals(alert);
    }
}
