package com.example.atlas.website.gmail.pages;

import com.example.atlas.elements.AtlasElement;
import com.example.atlas.models.User;
import io.qameta.allure.Description;
import io.qameta.atlas.webdriver.ElementsCollection;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Selector;

import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;

public interface MailPage extends WebPage, WithHeader, WithMessageForm  {

    @FindBy(selector = Selector.XPATH, value = "//div[@role='button'][contains(text(), 'Написать')]")
    AtlasElement newMessageButton();

    @FindBy(selector = Selector.CSS, value = "div[role='alert']")
    MailAlert mailAlert();

    @Description("Email List")
    @FindBy(selector = Selector.CSS, value = "table[role='grid'] tr[role='row']")
    ElementsCollection<Email> emails();

    @FindBy(selector = Selector.CSS, value = "div[data-tooltip='Отправленные']")
    AtlasElement sentButton();

    default void logout(User user){
        header().logout(user);
    }

    default MailForm newMessage(){
        newMessageButton().shouldBe(displayed()).click();
        return mailForm();
    }

}
