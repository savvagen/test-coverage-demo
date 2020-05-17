package com.example.atlas.listeners.element;

import com.example.atlas.listeners.annotation.ListElement;
import io.qameta.atlas.webdriver.ElementsCollection;

public interface ExtElementsCollection<E> extends ElementsCollection<E> {

    @ListElement
    E element(int i);

}
