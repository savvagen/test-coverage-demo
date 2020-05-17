package com.example.atlas.website.propeller.pages;

import com.example.atlas.elements.AtlasElement;
import com.example.atlas.models.User;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Page;
import io.qameta.atlas.webdriver.extension.Selector;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;

import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;
import static ru.yandex.qatools.matchers.webdriver.TextMatcher.text;
import static ru.yandex.qatools.matchers.webdriver.EnabledMatcher.enabled;

public interface LoginPage extends WebPage {

    @FindBy(selector = Selector.ID, value = "loginInput")
    AtlasElement loginField();

    @FindBy(selector = Selector.ID, value = "passwordInput")
    AtlasElement passwordField();

    @FindBy(selector = Selector.CSS, value = "*[class*='card-footer'] button:nth-child(2)")
    AtlasElement submitButton();

    @Retry(timeout = 11_000L)
    @FindBy(selector = Selector.CSS, value = "*[src='sign.png']")
    AtlasElement signInButton();


    @FindBy(selector = Selector.ID, value = "loader")
    AtlasElement loader();


    default LoginPage loginWith(User user){
        loginField().shouldBe(displayed()).parent().click();
        loginField().setValue(user.login);
        passwordField().shouldBe(displayed()).parent().click();
        passwordField().setValue(user.password);
        submitButton().shouldBe(enabled());
        return this;
    }

    default LoginPage submitLogin(){
        submitButton().hover();
        submitButton().should(text("Wait for some time..."));
        signInButton().waitUntil(displayed(), 11);
        signInButton().click();
        return this;
    }

    default void confirmLogin(){
        Assertions.assertEquals("Are you sure you want to login?", getWrappedDriver().switchTo().alert().getText());
        getWrappedDriver().switchTo().alert().accept();
        loader().waitUntil(displayed());
        Assertions.assertEquals("Really sure?", getWrappedDriver().switchTo().alert().getText());
        getWrappedDriver().switchTo().alert().accept();
    }

}
