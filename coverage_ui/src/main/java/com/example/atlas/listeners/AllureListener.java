package com.example.atlas.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import io.qameta.atlas.core.api.Listener;
import io.qameta.atlas.core.api.Target;
import io.qameta.atlas.core.context.TargetContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import org.hamcrest.Matcher;

import java.lang.reflect.Method;
import java.util.*;

public class AllureListener implements Listener {

    private final AllureLifecycle lifecycle = Allure.getLifecycle();
    private final Map<String, MethodFormatter> loggableMethods;

    public AllureListener() {
        loggableMethods = new HashMap<>();
        //loggableMethods.put("open", (name, args) -> String.format("Opening page \'%s\'", args[0]));
        loggableMethods.put("open", (name, args) -> String.format("Opening page \'%s\'", name));
        loggableMethods.put("click", (name, args) -> String.format("Clicking at web element \'%s\'", name));
        loggableMethods.put("submit", (name, args) -> String.format("Submitting to web element \'%s\'", name));
        loggableMethods.put("clear", (name, args) -> String.format("Clearing web element' input \'%s\'", name));
        loggableMethods.put("hover", (name, args) -> String.format("Hover web element' input \'%s\'", name));
        loggableMethods.put("parent", (name, args) -> String.format("Switching to parent web element' input \'%s\'", name));

        loggableMethods.put("sendKeys", (name, args) -> {
            String arguments = Arrays.toString(((CharSequence[]) args[0]));
            return String.format("Typing to the web element \'%s\' value [%s]", name, arguments);
        });
        loggableMethods.put("setValue", (name, args) -> {
            String arguments = Arrays.toString(((CharSequence[]) args[0]));
            return String.format("Setting value to the web element \'%s\' value [%s]", name, arguments);
        });
        loggableMethods.put("waitUntil", (name, args) -> {
            Matcher matcher = (Matcher) (args[0] instanceof Matcher ? args[0] : args[1]);
            return String.format("Waiting until the web element \'%s\' has state [%s]", name, matcher);
        });
        loggableMethods.put("should", (description, args) -> {
            Matcher matcher = (Matcher) (args[0] instanceof Matcher ? args[0] : args[1]);
            return String.format("The web element should be \'%s\' in the state [%s]", description, matcher);
        });
    }

    @Override
    public void beforeMethodCall(final MethodInfo methodInfo,
                                 final Configuration configuration) {
        getMethodFormatter(methodInfo.getMethod()).ifPresent(formatter -> {
            final String name = configuration.getContext(TargetContext.class)
                    .map(TargetContext::getValue)
                    .map(Target::name)
                    .orElse(methodInfo.getMethod().getName());
            final Object[] args = methodInfo.getArgs();
            lifecycle.startStep(Objects.toString(methodInfo.hashCode()),
                    new StepResult().setName(formatter.format(name, args)).setStatus(Status.PASSED));
        });
    }

    @Override
    public void afterMethodCall(final MethodInfo methodInfo,
                                final Configuration configuration) {
        getMethodFormatter(methodInfo.getMethod())
                .ifPresent(title -> lifecycle.stopStep(Objects.toString(methodInfo.hashCode())));
    }

    @Override
    public void onMethodReturn(final MethodInfo methodInfo,
                               final Configuration configuration,
                               final Object returned) {
    }

    @Override
    public void onMethodFailure(final MethodInfo methodInfo,
                                final Configuration configuration,
                                final Throwable throwable) {
        getMethodFormatter(methodInfo.getMethod()).ifPresent(title ->
                lifecycle.updateStep(stepResult -> {
                    stepResult.setStatus(ResultsUtils.getStatus(throwable).orElse(Status.BROKEN));
                    stepResult.setStatusDetails(ResultsUtils.getStatusDetails(throwable).orElse(null));
                })
        );
    }

    private Optional<MethodFormatter> getMethodFormatter(Method method) {
        return Optional.ofNullable(loggableMethods.get(method.getName()));
    }


    @FunctionalInterface
    private interface MethodFormatter {
        String format(String name, Object[] args);
    }

}