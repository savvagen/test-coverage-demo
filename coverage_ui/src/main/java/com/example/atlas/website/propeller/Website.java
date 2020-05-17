package com.example.atlas.website.propeller;

import com.example.atlas.website.propeller.pages.LoginPage;
import io.qameta.atlas.webdriver.WebSite;
import io.qameta.atlas.webdriver.extension.Page;
import io.qameta.atlas.webdriver.extension.Param;

public interface Website extends WebSite {


    default String baseUrl(){
        return "http://localhost:8080";
    }

    @Page(url = "{{baseUrl}}/index.html")
    LoginPage loginPage(@Param("baseUrl") String baseUrl);

}
