package ru.lukymiv.doc;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class LoginPage extends BasePage {

    public static SelenideElement loginInput = $("#login_field"),
            passwordInput = $("#password_field"),
            loginButton = $x("//button[@type='submit']");

    public static void login(String username, String password) {
//        loginInput.val(username);
        val(loginInput, username);
//        passwordInput.val(password).pressEnter();
        val(passwordInput, password);
    }

    public static void loginClickButton(String username, String password) {
//        loginInput.val(username);
        val(loginInput, username);
//        passwordInput.val(password);
        val(passwordInput, password);
//        sleep(500);
//        loginButton.click();
        click(loginButton);
    }


}
