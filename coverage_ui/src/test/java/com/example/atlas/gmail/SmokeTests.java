package com.example.atlas.gmail;
import com.example.atlas.website.gmail.pages.MailPage;
import io.qameta.atlas.webdriver.AtlasWebElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;


import java.io.File;

import static org.hamcrest.Matchers.*;
import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;
import static ru.yandex.qatools.matchers.webdriver.TextMatcher.*;

@Tag("gmail")
public class SmokeTests extends BaseTest {

    @AfterEach
    public void tearDownTest(){
        onPage(MailPage.class).logout(gmailUser);
        //driver.get("https://accounts.google.com/Logout");
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("window.location.reload(true)");

    }

    @Test
    public void shouldLoginToGmail(){
        onLoginPage().open();
        onLoginPage().loginWith(gmailUser);
        onWebSite().mailPage("inbox").open();
        onWebSite().mailPage("inbox").header().accountButton(gmailUser.login).should(displayed());
    }

    @Test
    public void shouldLoginToGmailWithEnters(){
        onLoginPage().open();
        onLoginPage().loginWithEnters(gmailUser);
        onWebSite().mailPage("inbox").open();
        onWebSite().mailPage("inbox").header().accountButton(gmailUser.login).should(displayed());
    }

    @Test
    public void shouldCreateNewMessage(){
        onLoginPage().open();
        onLoginPage().loginWith(gmailUser);
        onWebSite().mailPage("inbox").open();
        onPage(MailPage.class).newMessage().submitMessage(
                        System.getenv("TARGET_EMAIL"),
                        "Test", "Hello World!",
                        new File("src/test/resources/test.txt"));
        onPage(MailPage.class).mailAlert().message().shouldBe(displayed()).should(text("Письмо отправлено."));
        onWebSite().mailPage("sent").open();
        onWebSite().mailPage("sent").emails()
                .filter(AtlasWebElement::isDisplayed)
                .should(hasSize(greaterThan(2)))
                .get(0).senderField(gmailUser.login).should(text(gmailUser.username));
    }

}

