package com.example.atlas.listeners;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ElementInfo {

    private String findBy;
    private String name;

    public ElementInfo(String findBy, String name){
        this.findBy = findBy;
        this.name = name;
    }

}
