package com.example.atlas.website.gmail.pages;

import com.example.atlas.elements.AtlasElement;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;
import io.qameta.atlas.webdriver.extension.Selector;

public interface Email extends AtlasElement {

    @FindBy(selector = Selector.CSS, value = "div[role='checkbox']")
    AtlasElement checkbox();

    @FindBy(selector = Selector.CSS, value = "span[email='{{ sender }}']")
    AtlasElement senderField(@Param("sender") String sender);

    @FindBy(selector = Selector.CSS, value = "div[role='link'] span.bog  > span")
    AtlasElement subject();

    @FindBy(selector = Selector.CSS, value = "div[role='link'] > div > span > span")
    AtlasElement text();
}
