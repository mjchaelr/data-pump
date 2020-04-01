package com.mjchael.datapump.cli.command;

import com.mjchael.datapump.cli.strategy.CommandLineStrategy;

public class HelpCommand implements CommandLineStrategy {

    @Override
    public void executeCommand(String... args) {
        System.out.println("usage: java -jar data-pump.jar --db.connect=username/password@sid [run] [file] [version] [help]  \n" +
                "[run OPTIONS]\n" +
                "\t Option 1:\n" +
                "\t\t [<owner] [tableName] [whereClause]\n" +
                "[file OPTIONS]\n" +
                "\t Option 1:\n" +
                "\t\t [file.yml]");
    }
}
