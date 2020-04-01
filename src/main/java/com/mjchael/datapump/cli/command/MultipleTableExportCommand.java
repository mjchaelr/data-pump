package com.mjchael.datapump.cli.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mjchael.datapump.cli.strategy.CommandLineStrategy;
import com.mjchael.datapump.core.config.ExportTableSpecification;
import com.mjchael.datapump.core.config.QueryObject;
import com.mjchael.datapump.core.service.DataPumpService;
import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MultipleTableExportCommand implements CommandLineStrategy {

    private DataPumpService dataPumpService;

    public MultipleTableExportCommand(DataPumpService dataPumpService){
        this.dataPumpService = dataPumpService;
    }


    @SneakyThrows
    @Override
    public void executeCommand(String... args) {
        File directoryOut = new File("out");
        if (!directoryOut.exists()) {
            boolean newDir = directoryOut.mkdirs();
            if ( newDir){
                System.out.println("Created new directory: "+directoryOut.getPath());
            }
        }

        File fileRead = new File(args[0]);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ExportTableSpecification objectExportDefinition
                = mapper.readValue(fileRead, ExportTableSpecification.class);

        String owner = objectExportDefinition.getOwner().toUpperCase();

        File fileOut = new File("out/insert_"+args[0].split("\\.")[0]+".sql"); // for split could have used regex '[.]' also
        FileOutputStream fos = new FileOutputStream(fileOut);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos))) {
            // perform all preinsert.
            List<String> preInsertList = new ArrayList<>();
            List<String> postInsertList = new ArrayList<>();
            List<QueryObject> queryObjects = new ArrayList<>();

            extractPreInsertScript(objectExportDefinition, preInsertList);
            extractPostInsertScript(objectExportDefinition, postInsertList);
            extractQueryObject(objectExportDefinition, queryObjects);


            // preInsert conditions
            preInsertList.forEach(script -> {
                try {
                    bufferedWriter.write(script);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // reverse order for delete
            queryObjects.stream()
                    .collect(Collectors.collectingAndThen(Collectors.toList(),
                            list -> {
                                Collections.reverse(list);
                                return list.stream();
                            }
                    ))
                    .forEach(
                            r -> {
                                try {
                                    bufferedWriter.write("-- delete target records " + r.getTableName());
                                    bufferedWriter.newLine();
                                    bufferedWriter.write("delete from " + r.getTableName() + " where " + r.getWhereClause() + ";");
                                    bufferedWriter.newLine();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                    );

            // file order for insert statements
            queryObjects.forEach(r -> {

                List<String> x = dataPumpService.generateInsert(
                        owner,
                        r.getTableName(),
                        r.getWhereClause()
                );
                try {
                    bufferedWriter.write("--Insert for table: " + r.getTableName());
                    bufferedWriter.newLine();
                    for (String insertStatement : x) {
                        bufferedWriter.write(insertStatement);
                        bufferedWriter.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            bufferedWriter.write("commit;");
            bufferedWriter.newLine();

            // post insert condition
            postInsertList.forEach(script -> {
                try {
                    bufferedWriter.write(script);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            bufferedWriter.newLine();
        }
    }


    private void extractQueryObject(ExportTableSpecification objectExportDefinition, List<QueryObject> queryObjects) {
        System.out.print("-> Validating where clause & tableName for table ");
        if (Optional.ofNullable(objectExportDefinition.getTableName()).equals(Optional.empty())){
            throw new AssertionError("Table name  cannot be null");
        }
        else if (Optional.ofNullable(objectExportDefinition.getWhereClause()).equals(Optional.empty())) {
            System.out.println(objectExportDefinition.getTableName());
            throw new AssertionError("Where clause cannot be null");
        } else {
            System.out.println(objectExportDefinition.getTableName());
            queryObjects.add(new QueryObject(objectExportDefinition.getTableName(), objectExportDefinition.getWhereClause()));
        }

        if (!Optional.ofNullable(objectExportDefinition.getDependencies()).equals(Optional.empty())) {
            System.out.println("-> Reading dependencies for table "+ objectExportDefinition.getTableName());
            objectExportDefinition.getDependencies().forEach(dependency -> extractQueryObject(dependency, queryObjects));
        }

        if (!Optional.ofNullable(objectExportDefinition.getRelatedTables()).equals(Optional.empty())) {
            System.out.println("-> Reading related tables for table "+ objectExportDefinition.getTableName());
            objectExportDefinition.getRelatedTables().forEach(relatedTable -> extractQueryObject(relatedTable, queryObjects));
        }
    }

    private void extractPreInsertScript(ExportTableSpecification objectExportDefinition, List<String> list) {

        if (!Optional.ofNullable(objectExportDefinition.getPreInsertScript()).equals(Optional.empty())
                && !objectExportDefinition.getPreInsertScript().equals("none")) {
            list.add(objectExportDefinition.getPreInsertScript());
        }

        if (!Optional.ofNullable(objectExportDefinition.getDependencies()).equals(Optional.empty())) {
            objectExportDefinition.getDependencies().forEach(dependency -> extractPreInsertScript(dependency, list));
        }

        if (!Optional.ofNullable(objectExportDefinition.getRelatedTables()).equals(Optional.empty())) {
            objectExportDefinition.getRelatedTables().forEach(relatedTable -> extractPreInsertScript(relatedTable, list));
        }
    }

    private void extractPostInsertScript(ExportTableSpecification objectExportDefinition, List<String> list) {

        if (!Optional.ofNullable(objectExportDefinition.getPostInsertScript()).equals(Optional.empty())
                && !objectExportDefinition.getPostInsertScript().equals("none")) {
            list.add(objectExportDefinition.getPostInsertScript());
        }

        if (!Optional.ofNullable(objectExportDefinition.getDependencies()).equals(Optional.empty())) {
            objectExportDefinition.getDependencies().forEach(dependency -> extractPostInsertScript(dependency, list));
        }

        if (!Optional.ofNullable(objectExportDefinition.getRelatedTables()).equals(Optional.empty())) {
            objectExportDefinition.getRelatedTables().forEach(relatedTable -> extractPostInsertScript(relatedTable, list));
        }
    }
}
