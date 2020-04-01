package com.mjchael.datapump.cli.strategy;

import com.mjchael.datapump.cli.command.HelpCommand;
import com.mjchael.datapump.cli.command.MultipleTableExportCommand;
import com.mjchael.datapump.cli.command.SingleTableExportCommand;
import com.mjchael.datapump.cli.command.VersionCommand;
import com.mjchael.datapump.cli.model.CommandLineEnum;
import com.mjchael.datapump.core.service.DataPumpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Context {
    @Autowired
    private DataPumpService dataPumpService;

    public void executeStrategy(String ... args){
        CommandLineStrategy commandLineStrategy;

        switch (getCommand(args[1])){
            case RUN:
                commandLineStrategy = new SingleTableExportCommand(dataPumpService); // TODO check if this is a good practice to do so...
                commandLineStrategy.executeCommand(args[2], args[3], args[4]);
                break;
            case FILE:
                commandLineStrategy = new MultipleTableExportCommand(dataPumpService); // TODO check if this is a good practice to do so...
                commandLineStrategy.executeCommand(args[2]);
                break;
            case VERSION:
                commandLineStrategy = new VersionCommand();
                commandLineStrategy.executeCommand();
                break;
            default:
                commandLineStrategy = new HelpCommand();
                commandLineStrategy.executeCommand();
                break;

        }
    }

    private CommandLineEnum getCommand(String command) {
        CommandLineEnum commandLineEnum;
        try {
            commandLineEnum = CommandLineEnum.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandLineEnum = CommandLineEnum.UNDEFINED;
        }
        return commandLineEnum;
    }
}
