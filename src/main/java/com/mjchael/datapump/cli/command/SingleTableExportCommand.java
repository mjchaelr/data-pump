package com.mjchael.datapump.cli.command;

import com.mjchael.datapump.cli.strategy.CommandLineStrategy;
import com.mjchael.datapump.core.service.DataPumpService;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;


public class SingleTableExportCommand implements CommandLineStrategy {

    private DataPumpService dataPumpService;

    public SingleTableExportCommand(DataPumpService dataPumpService){
        this.dataPumpService = dataPumpService;
    }

    @SneakyThrows
    @Override
    public void executeCommand(String... args) {
        String owner = args[0].toUpperCase();
        String tableName = args[1].toUpperCase();
        String whereClause = args[2];

        List<String> x = dataPumpService.generateInsert(
                owner,
                tableName,
                whereClause
        );

        File fileOut = new File("out/insert_"+tableName+".sql");
        File directoryOut = new File("out");
        if (!directoryOut.exists()) {
            boolean newDir = directoryOut.mkdirs();
            if ( newDir){
                System.out.println("Created new directory: "+directoryOut.getPath());
            }
        }

        try (FileOutputStream fos = new FileOutputStream(fileOut)) {


            try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos))) {
                bufferedWriter.write("-- delete target records " + tableName);
                bufferedWriter.newLine();
                bufferedWriter.write("delete from " + tableName + " where " + whereClause + ";");
                bufferedWriter.newLine();
                bufferedWriter.write("-- insert " + tableName);
                bufferedWriter.newLine();

                for (String insertStatement : x) {
                    bufferedWriter.write(insertStatement);
                    bufferedWriter.newLine();
                }

                bufferedWriter.write("commit;");
                bufferedWriter.newLine();
            }
        }
    }
}
