package com.example.atlas.website.gmail.pages;

import com.example.atlas.elements.AtlasElement;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Selector;

public interface MailAlert extends AtlasElement {

    @FindBy(selector = Selector.CSS, value = "div > div:nth-child(2) span span:nth-child(1)")
    AtlasElement message();

}
