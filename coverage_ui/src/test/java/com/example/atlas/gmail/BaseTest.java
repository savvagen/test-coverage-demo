package com.example.atlas.gmail;

import com.example.atlas.extensions.HoverExtension;
import com.example.atlas.extensions.ParentExtension;
import com.example.atlas.extensions.PressEnterExtension;
import com.example.atlas.listeners.AllureListener;
import com.example.atlas.models.User;
import com.example.atlas.website.gmail.GMailWebsite;
import com.example.atlas.website.gmail.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import io.qameta.atlas.webdriver.WebPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    public static Atlas atlas;
    public static WebDriver driver;
    public static User gmailUser;


    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        atlas = new Atlas(new WebDriverConfiguration(driver, "http://mail.google.com"))
                .extension(new PressEnterExtension())
                .extension(new HoverExtension())
                .extension(new ParentExtension())
                .listener(new AllureListener());

        gmailUser = new User("Savva Genchevskiy",
                System.getenv("SECRET_EMAIL"),
                System.getenv("SECRET_PASSWORD"));

    }


    @AfterAll
    public static void tearDown(){
        driver.close();
        driver.quit();
    }


    public GMailWebsite onWebSite(){
        return atlas.create(driver, GMailWebsite.class);
    }

    public LoginPage onLoginPage(){
        return atlas.create(driver, LoginPage.class);
    }

    public <T extends WebPage> T onPage(Class<T> page) {
        return atlas.create(driver, page);
    }



}
