package com.example.atlas.propeller;

import com.example.atlas.listeners.FindByContext;
import com.google.gson.Gson;
import io.qameta.allure.Attachment;
import lombok.Builder;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public class SaveLocatorsListener implements BeforeAllCallback,
        AfterAllCallback,
        BeforeEachCallback,
        AfterEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback {


    @Setter
    private List<FindByContext> locators;

    @Attachment(value = "locators", fileExtension = ".locators")
    private String locators(String attach) {
        return attach;
    }

    private String testName(ExtensionContext description) {
        String param = "";
        String regex = ".*\\[(.*)\\].*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(description.getDisplayName());
        if (matcher.find()) {
            param = String.format("[%s]", matcher.group(1));
        }
        return Optional.of(description.getTestMethod().get().getAnnotation(DisplayName.class).value() + param)
                .orElse(description.getDisplayName());
    }


    @Override
    public void afterAll(ExtensionContext context) throws Exception {

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        List<Locator> results = new ArrayList<>();

        Map<String, FindByContext> map = new HashMap<>();
        locators.forEach(s -> map.put(s.locator(), s));

        String testName = testName(context);

        map.forEach((k, v) -> results
                .add(new Locator().setFullPath(k)
                        .setTests(testName)
                        .setUrls(v.getUrl())));

        locators(new Gson().toJson(results));
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {

    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {

    }
}
