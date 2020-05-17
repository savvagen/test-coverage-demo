package com.example.atlas.listeners;


import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import io.qameta.atlas.core.api.Listener;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.webdriver.context.WebDriverContext;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hamcrest.Matcher;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;

@Accessors(chain = true)
public class AllureListenerRu implements Listener {

    @Getter
    @Setter
    private List<FindByContext> locatorsList;
    private final AllureLifecycle lifecycle = Allure.getLifecycle();
    private final Map<String, MethodFormat> loggableMethods;

    public AllureListenerRu() {
        loggableMethods = new HashMap<>();
        loggableMethods.put("click", (description, args) -> String.format("Кликаем на элемент «%s»", description));
        loggableMethods.put("submit", (description, args) -> String.format("Нажимаем на элемент «%s»", description));
        loggableMethods.put("clear", (description, args) -> String.format("Очищаем элемент «%s»", description));
        loggableMethods.put("hover", (description, args) -> String.format("Фокусируемся на элемент «%s»", description));
        loggableMethods.put("parent", (description, args) -> String.format("Ищем родительский элемент «%s»", description));

        loggableMethods.put("sendKeys", (description, args) -> {
            String arguments = Arrays.toString(((CharSequence[]) args[0]));
            return String.format("Вводим в элемент «%s» значение [%s]", description, arguments);
        });
        loggableMethods.put("setValue", (description, args) -> {
            String arguments = Arrays.toString(((CharSequence[]) args[0]));
            return String.format("Вводим в элемент «%s» значение [%s]", description, arguments);
        });
        loggableMethods.put("waitUntil", (description, args) -> {
            Matcher matcher = (Matcher) (args[0] instanceof Matcher ? args[0] : args[1]);
            return String.format("Ждем пока элемент «%s» будет в состоянии [%s]", description, matcher);
        });
        loggableMethods.put("should", (description, args) -> {
            Matcher matcher = (Matcher) (args[0] instanceof Matcher ? args[0] : args[1]);
            return String.format("Проверяем что элемент «%s» в состоянии [%s]", description, matcher);
        });
    }

    private Optional<MethodFormat> getStepTitle(MethodInfo method) {
        return method.getMethod().isDefault()
                ? Optional.empty()
                : Optional.ofNullable(loggableMethods.get(method.getMethod().getName()));
    }

    @Override
    public void beforeMethodCall(MethodInfo methodInfo, Configuration configuration) {
        if (isSupported(methodInfo)) {
            Optional<WebDriverContext> driverContext = configuration.getContext(WebDriverContext.class);

            if (driverContext.isPresent()) {
                String url = driverContext.get().getValue().getCurrentUrl();
                String session = ((RemoteWebDriver) driverContext.get().getValue()).getSessionId().toString();
                configuration.getContext(FindByContext.class)
                        .ifPresent(findByContext -> findByContext.setUrl(url).setSession(session).currentContext());
            }
        }

        String name = methodInfo.getMethod().getName();
        if (configuration.getContext(FindByContext.class).isPresent()) {
            name = configuration.getContext(FindByContext.class).get().name();
        }
        String finalName = name;
        getStepTitle(methodInfo).ifPresent(formatter ->
                lifecycle.startStep(UUID.randomUUID().toString(),
                        new StepResult()
                                .setName(formatter.format(finalName, methodInfo.getArgs()))
                                .setStatus(Status.PASSED)
                ));
    }

    @Override
    public void afterMethodCall(MethodInfo methodInfo, Configuration configuration) {
        if (isSupported(methodInfo)) {
            configuration.getContext(FindByContext.class)
                    .ifPresent(findByContext -> {
                        locatorsList.add(findByContext.copy());
                    });
        }
        getStepTitle(methodInfo).ifPresent(title -> lifecycle.stopStep());

        System.out.println(Arrays.toString(locatorsList.toArray()));

    }

    @Override
    public void onMethodReturn(MethodInfo methodInfo, Configuration configuration, Object o) {

    }

    @Override
    public void onMethodFailure(MethodInfo methodInfo, Configuration configuration, Throwable throwable) {
        getStepTitle(methodInfo).ifPresent(title ->
                lifecycle.updateStep(stepResult -> {
                    stepResult.setStatus(ResultsUtils.getStatus(throwable).orElse(Status.BROKEN));
                    stepResult.withStatusDetails(ResultsUtils.getStatusDetails(throwable).orElse(null));
                })
        );
    }

    @FunctionalInterface
    private interface MethodFormat {

        String format(String description, Object[] args);

    }

    private boolean isSupported(MethodInfo methodInfo) {
        String name = methodInfo.getMethod().getName();
        return name.equals("click")
                || name.equals("waitUntil")
                || name.equals("should")
                || name.equals("sendKeys")
                || name.equals("shouldBe")
                || name.equals("setValue")
                || name.equals("clear")
                || name.equals("getText");
    }

}