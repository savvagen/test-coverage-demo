package com.example.atlas.website.gmail.pages;

import com.example.atlas.elements.AtlasElement;
import com.example.atlas.models.User;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;
import io.qameta.atlas.webdriver.extension.Selector;
import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;
import static ru.yandex.qatools.matchers.webdriver.EnabledMatcher.enabled;


public interface Header extends AtlasWebElement {

    @FindBy(selector = Selector.CSS, value = "a[href^='https://accounts.google.com/Logout']")
    AtlasElement logoutButton();

    @Retry(timeout = 10_000L, polling = 500L)
    @FindBy(selector = Selector.CSS, value = "a[aria-label*='({{ email }})']")
    AtlasElement accountButton(@Param("email") String email);

    default void logout(User user){
        accountButton(user.login).shouldBe(displayed()).click();
        logoutButton().waitUntil(enabled()).click();
    }

}
