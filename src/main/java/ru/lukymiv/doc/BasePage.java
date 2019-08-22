package ru.lukymiv.doc;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class BasePage {

    //TABS
    public SelenideElement queueManagerPage = $x("//li[a[i[@class='fa fa-inbox']]]");
    public SelenideElement queuelist = $x("//li[a[i[@class='fa fa-inbox']]] //a[contains(text(),'Список очередей')]");
    public SelenideElement topicsList = $x("//li[a[i[@class='fa fa-inbox']]] //a[contains(text(),'Список разделов')]");
    public SelenideElement currentUser = $x("//a[@id=\"current-user\"]");
    public SelenideElement indexPage = $x("//li[@id='main']");
    public SelenideElement messagePage = $x("//li[@id='send']");
    public static SelenideElement sopsPage = $x("//span[contains(text(),'Домены Брокера')]");
    public SelenideElement topicsPage = $x("//i[@class='fa fa-power-off']");
    public SelenideElement configurationPage = $x("//li[@class='dropdown']//a[@href='#']");
    public SelenideElement logsPage = $x("//li[@id='log']");
    public SelenideElement wrenchPage = $x("//i[@class='fa fa-wrench']");
    public SelenideElement settingsPage = $x("//li[a[span[contains(text(),'Настройки')]]]");

    static ElementsCollection spinners = $$x("//div[@class='spinner']");


    public static SelenideElement logoutButton = $x("//li[text()='Выйти']"),
            userProfile = $x("//span[@class=\"fa fa-fw fa-user\"]/following-sibling::span[@class=\"widget-title\"]");

    static String CurrentURL = "";

    @Step("Logout")
    public static void logout() {
//        if ($x("//aside[@class='main_sidebar ']").getAttribute("class").equals("main_sidebar sidebar_close"))
//        $x("//span[@class=\"fa fa-fw fa-user\"]/following-sibling::span[@class=\"widget-title\"]").click();

//        userProfile.click();
        click(userProfile);
//        logoutButton.click();
        click(logoutButton);

    }

    @Step("Go to Index page")
    public void indexPage() {
//        indexPage.click();
        click(indexPage);
    }


    @Step("Go to Queue Manager page")
    public void queueManagerPage() {
        queueManagerPage.waitUntil(enabled, 5000);
        if (queueManagerPage.attr("class").contains("close")) {
            click(queueManagerPage);
        }
    }

    @Step("Go to Topics list")
    public void topicsList() {
        queueManagerPage.waitUntil(enabled, 5000);
        if (queueManagerPage.attr("class").contains("close")) {
            click(queueManagerPage);
        }
        click(topicsList);
    }

    @Step("Go to Queue list")
    public void queueListPage() {
        queueManagerPage.waitUntil(enabled, 5000);
        if (queueManagerPage.attr("class").contains("close")) {
//            queueManagerPage.click();
            click(queueManagerPage);

        }
//        queuelist.waitUntil(enabled, 2000).click();
        click(queuelist);
    }


    @Step("Go to Message page")
    public void messagePage() {
        click(messagePage);
    }

    @Step("Go to SOPS page")
    public void sopsPage() {
        sopsPage.waitUntil(enabled, 5000);
        sleep(1000);
        if ($x("//span[contains(text(),'Домены Брокера')]/../..").attr("class").contains("close")) {
            click(sopsPage);
        }
    }

    @Step("Go to Configuration page")
    public void configurationPage() {
//        configurationPage.click();
        click(configurationPage);
    }

    @Step("Go to Wrench page")
    public void wrenchPage() {
//        wrenchPage.click();
        click(wrenchPage);
    }

    @Step("Go to Wrench page")
    public void logsPage() {
//        logsPage.click();
        click(logsPage);

    }

    @Step("Go to settings page")
    public void settingsPage() {
        settingsPage.waitUntil(enabled, 3000);
        if (settingsPage.attr("class").contains("close")) {
//            settingsPage.click();
            click(settingsPage);
        }

    }

    public void waitClickableElement(SelenideElement element) {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void click(SelenideElement element) {
        element.waitUntil(visible, 10000);
        String Locator = element.toString();
        spinnerNotVisible();
        element.click();
        sleep(500);
        spinnerNotVisible();
        System.out.println("click " + Locator);
//        System.out.println("URL: " + currentFrameUrl());
        if (!url().equals(CurrentURL)) {
            CurrentURL = url();
            System.out.println("Текущий URL: " + CurrentURL);
            sleep(4000);
        }
        spinnerNotVisible();
    }

    public void contextClick(SelenideElement element) {
        spinnerNotVisible();
        element.shouldBe(visible);
        String Locator = element.toString();
        element.contextClick();
        System.out.println("context click " + Locator);
    }

    public static void doubleClick(SelenideElement element) {
        element.waitUntil(visible, 10000);
        String Locator = element.toString();
        spinnerNotVisible();
        element.doubleClick();
        sleep(500);
        spinnerNotVisible();
        System.out.println("doubleclick " + Locator);
//        System.out.println("URL: " + currentFrameUrl());
        if (!url().equals(CurrentURL)) {
            CurrentURL = url();
            System.out.println("Текущий URL: " + CurrentURL);
            sleep(4000);
        }
        spinnerNotVisible();
    }

    public static void val(SelenideElement element, String text) {
        spinnerNotVisible();
        element.waitUntil(visible, 10000);
        String Locator = element.toString();
        sleep(500);
        element.clear();
        element.val(text);
        System.out.println("Написали: " + text + " в: " + Locator);
        element.shouldHave(value(text));
    }

    public void valAndPressEnter(SelenideElement element, String text) {
        element.shouldBe(visible);
        String Locator = element.toString();
        element.clear();
        element.val(text);
        System.out.println("Написали: " + text + " в: " + Locator + "и нажали Enter");
        element.shouldHave(value(text)).pressEnter();
    }

    public static void spinnerNotVisible() {
        for (SelenideElement spinner : spinners) {
            if (spinner.isDisplayed()) {
                spinner.waitUntil(not(visible), 10000);
            }
        }
    }
}
