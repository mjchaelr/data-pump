package com.mjchael.datapump.cli.model;

public enum CommandLineEnum {
    RUN("RUN"),
    FILE("FILE"),
    HELP("HELP"),
    VERSION("VERSION"),
    UNDEFINED("UNDEFINED");

    public final String label;

    CommandLineEnum(String label){
        this.label=label.toUpperCase();
    }
}
