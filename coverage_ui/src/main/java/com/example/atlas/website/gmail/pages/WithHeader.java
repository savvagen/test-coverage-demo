package com.example.atlas.website.gmail.pages;

import io.qameta.atlas.webdriver.extension.FindBy;

public interface WithHeader {
    @FindBy("//header[@role='banner']")
    Header header();
}