package com.example.atlas.website.gmail.pages;

import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Selector;

public interface WithMessageForm {

    @FindBy(selector = Selector.CSS, value = "div[role='dialog']")
    MailForm mailForm();

}
