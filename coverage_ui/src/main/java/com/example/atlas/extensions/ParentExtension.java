package com.example.atlas.extensions;

import com.example.atlas.elements.AtlasElement;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.webdriver.AtlasWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

public class ParentExtension implements MethodExtension {
    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) {
        //WebElement parentElement = ((WebElement) proxy).findElement(By.xpath("./.."));
        proxy = ((WebElement) proxy).findElement(By.xpath("./parent::*"));
        return proxy;
    }

    @Override
    public boolean test(Method method) {
        return method.getName().equals("parent");
    }

}
