package com.example.atlas.website.gmail;

import com.example.atlas.website.gmail.pages.LoginPage;
import com.example.atlas.website.gmail.pages.MailPage;
import io.qameta.atlas.webdriver.WebSite;
import io.qameta.atlas.webdriver.extension.*;

public interface GMailWebsite extends WebSite {

    @Page(url = "/signin/v2")
    LoginPage loginPage();

    @Page(url = "/mail/u/0/#{{ mail }}")
    MailPage mailPage(@Param("mail") String mail);

}
