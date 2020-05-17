package com.example.atlas.propeller;

import com.example.atlas.extensions.HoverExtension;
import com.example.atlas.extensions.ParentExtension;
import com.example.atlas.extensions.PressEnterExtension;
import com.example.atlas.listeners.AllureListenerRu;
import com.example.atlas.listeners.ContextWebDriverConfiguration;
import com.example.atlas.listeners.FindByContext;
import com.example.atlas.models.User;
import com.example.atlas.website.propeller.Website;
import com.example.atlas.website.propeller.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.WebPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    public static Atlas atlas;
    public static WebDriver driver;
    public static User sdmin;
    public static LoginPage loginPage;

    private static List<FindByContext> locators = new ArrayList<>();

    @RegisterExtension
    static SaveLocatorsListener sll = SaveLocatorsListener.builder()
            .locators(locators)
            .build();


    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        atlas = new Atlas(new ContextWebDriverConfiguration(driver))
                .extension(new PressEnterExtension())
                .extension(new HoverExtension())
                .extension(new ParentExtension())
                .listener(new AllureListenerRu().setLocatorsList(locators));

        sdmin = new User("Admin Admin", "test", "test");
        loginPage = atlas.create(driver, LoginPage.class);
    }


    @AfterAll
    public static void tearDown(){
        driver.close();
        driver.quit();
    }


    public Website onWebSite(){
        return atlas.create(driver, Website.class);
    }

    public LoginPage onLoginPage(){
        return atlas.create(driver, LoginPage.class);
    }

    public <T extends WebPage> T onPage(Class<T> page) {
        return atlas.create(driver, page);
    }



}
