package com.example.atlas.extensions;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import org.openqa.selenium.*;


import java.lang.reflect.Method;

public class PressEnterExtension implements MethodExtension {
    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) {
        WebElement webElement = ((WebElement) proxy);
        webElement.sendKeys(Keys.ENTER);
        return proxy;
    }

    @Override
    public boolean test(Method method) {
        return method.getName().equals("pressEnter");
    }
}
