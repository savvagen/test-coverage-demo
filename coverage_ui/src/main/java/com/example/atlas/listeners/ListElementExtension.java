package com.example.atlas.listeners;

import com.example.atlas.listeners.annotation.ListElement;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.Method;
import java.util.List;

public class ListElementExtension implements MethodExtension {

    @Override
    public boolean test(Method method) {
        return method.isAnnotationPresent(ListElement.class);
    }

    @Override
    public Object invoke(Object proxy,
                         MethodInfo methodInfo,
                         Configuration configuration) throws Throwable {
        assert proxy instanceof List;
        int position = (int) methodInfo.getArgs()[0];

        configuration.getContext(FindByContext.class)
                .ifPresent(findByContext -> findByContext.addElement(new ElementInfo(
                        String.format("[%s]", position+1),
                        String.format("Элемент[%s]", position+1))
                ));

        return ((List) proxy).get(position);
    }
}
