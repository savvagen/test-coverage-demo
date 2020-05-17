package com.example;

import org.aeonbits.owner.Config;

@Config.Sources({"file:./test.properties"})
public interface TestConfig extends Config {
    int port();
    String hostname();
    @DefaultValue("42")
    int maxThreads();
}
