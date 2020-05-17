package com.example.atlas.website.gmail.pages;

import com.example.atlas.elements.AtlasElement;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Selector;
import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;
import static ru.yandex.qatools.matchers.webdriver.TextMatcher.text;
import static ru.yandex.qatools.matchers.webdriver.ExistsMatcher.exists;
import static org.hamcrest.Matchers.*;
import java.io.File;

public interface MailForm extends AtlasElement {

    @FindBy(selector = Selector.XPATH, value = "//textarea[@name='to']")
    AtlasElement destinationField();

    @FindBy(selector = Selector.XPATH, value = "//input[@name='subjectbox']")
    AtlasElement subjectField();

    @FindBy(selector = Selector.XPATH, value = "//div[@role='textbox']")
    AtlasElement bodyField();

    @FindBy(selector = Selector.CSS, value = "input[type='file'][name='Filedata']")
    AtlasElement fileInput();

    @FindBy(selector = Selector.CSS, value = "div[role='progressbar'")
    AtlasElement fileLoader();

    @FindBy(selector = Selector.XPATH, value = "//div[contains(@aria-label, '(⌘Enter)') and text() = 'Отправить']")
    AtlasElement sendButton();

    default void submitMessage(String to, String subject, String body, File attachment){
        destinationField().shouldBe(displayed()).click();
        destinationField().sendKeys(to);
        subjectField().sendKeys(subject);
        bodyField().sendKeys(body);
        fileInput().sendKeys(attachment.getAbsolutePath());
        fileLoader().should(exists());
        fileLoader().waitUntil(not(displayed()));
        sendButton().click();
    }

}
