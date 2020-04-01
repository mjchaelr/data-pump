package com.mjchael.datapump.cli.command;

import com.mjchael.datapump.cli.strategy.CommandLineStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
//@Configuration
//@ConfigurationProperties(prefix="app") FIXME programm cannot read property app
public class VersionCommand implements CommandLineStrategy {

    private String version;

    @Override
    public void executeCommand(String... args) {
        System.out.println("0.0.1");
    }
}
