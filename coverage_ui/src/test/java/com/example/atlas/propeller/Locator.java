package com.example.atlas.propeller;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Locator {

    private String fullPath;
    private String urls;
    private String tests;
    private int count;

}
