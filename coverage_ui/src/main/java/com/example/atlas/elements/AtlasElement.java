package com.example.atlas.elements;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.AtlasWebElement;
import org.hamcrest.Matcher;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public interface AtlasElement extends AtlasWebElement {

    WebElement hover();

    WebElement pressEnter();

    WebElement parent();

    default AtlasElement shouldBe(Matcher matcher) {
        return (AtlasElement) should(matcher);
    }

    default AtlasElement shouldBe(Matcher matcher, int timeoutInSeconds) {
        return should(matcher, timeoutInSeconds);
    }

    default AtlasElement should(Matcher matcher, Integer timeoutInSeconds){
        return should(matcher, timeoutInSeconds);
    }

    default AtlasElement should(String message, Matcher matcher){
        return should(message, matcher);
    }

    default AtlasElement waitUntil(Matcher matcher){ return waitUntil(matcher); }

    /*default AtlasElement pressEnter(){
        sendKeys(Keys.ENTER);
        return this;
    }*/

    default AtlasElement setValue(CharSequence... keysToSend){
        clear();
        sendKeys(keysToSend);
        return this;
    }
}
