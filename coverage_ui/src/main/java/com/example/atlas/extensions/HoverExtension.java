package com.example.atlas.extensions;

import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.Extension;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.webdriver.context.WebDriverContext;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.lang.reflect.Method;

public class HoverExtension implements MethodExtension {

    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) {
        final WebDriver driver = config.getContext(WebDriverContext.class)
                .orElseThrow(() -> new AtlasException("Context doesn't exist")).getValue();
        Actions actions = new Actions(driver);
        Point location = ((WebElement) proxy).getLocation();
        Dimension size = ((WebElement) proxy).getSize();
        //int x = location.getX() + size.width / 2;
        //int y = location.getY() + size.height / 2;
        //actions.moveToElement((WebElement) proxy, x, y).build().perform();
        actions.moveToElement((WebElement) proxy).build().perform();
        // Hover with Java Script
        /*String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";
        final JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(javaScript, (WebElement) proxy);*/
        return proxy;
    }

    @Override
    public boolean test(Method method) {
        return method.getName().equals("hover");
    }

}
