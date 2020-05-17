package com.example.atlas.website.gmail.pages;

import com.example.atlas.elements.AtlasElement;
import com.example.atlas.models.User;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Selector;

import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;

public interface LoginPage extends WebPage {

    @FindBy(selector = Selector.NAME, value = "identifier")
    AtlasElement emailField();

    @FindBy(selector = Selector.NAME, value = "password")
    AtlasElement passwordField();

    @FindBy(selector = Selector.CSS, value = "div[id*='Next']")
    AtlasElement nextButton();

    default void loginWith(User user){
        emailField().shouldBe(displayed()).setValue(user.login);
        nextButton().should(displayed()).click();
        passwordField().shouldBe(displayed()).sendKeys(user.password);
        nextButton().should(displayed()).click();
    }

    default void loginWithEnters(User user){
        emailField().waitUntil(displayed()).setValue(user.login).pressEnter();
        passwordField().waitUntil(displayed()).setValue(user.password).pressEnter();
    }

}
