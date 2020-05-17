package com.example.atlas.propeller;

import com.example.atlas.website.propeller.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@Tag("propeller")
public class LoginTests extends BaseTest {

    @Test
    @DisplayName("should login")
    public void shouldLogin(){
        driver.get("http://localhost:8080/");
        loginPage.loginWith(sdmin).submitLogin();
        loginPage.getWrappedDriver().switchTo().alert().accept();
    }

}
