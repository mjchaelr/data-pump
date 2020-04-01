package com.mjchael.datapump.core.service;

import com.mjchael.datapump.core.model.AllTableColumn;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DataPumpService {
    @Autowired
    public AllTableColumnService allTableColumnService;
    @Autowired
    public CustomQueryService customQueryService;

    public List<String> generateInsert(String owner, String tableName, String whereClause) {
        List<AllTableColumn> list = allTableColumnService.findByTableNameAndOwner(tableName.toUpperCase(), owner.toUpperCase());
        list.sort(Comparator.comparing(AllTableColumn::getColumnId));
        List<Object[]> dataList=  customQueryService.executeQuery(generateStringQuery(list, whereClause));
        return buildInsertStatement(dataList, list, tableName);
    }

    public void writeInFile(String owner, String tableName, String whereClause){

        List<String> insertList = generateInsert(
                owner,
                tableName,
                whereClause
        );

        File fileOut = new File("out/insert_"+tableName+".sql");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut)))) {
            bufferedWriter.write("-- delete target records " + tableName);
            bufferedWriter.newLine();
            bufferedWriter.write("delete from " + tableName + " where " + whereClause + ";");
            bufferedWriter.newLine();
            bufferedWriter.write("-- insert " + tableName);
            bufferedWriter.newLine();

            for (String insertStatement : insertList) {
                bufferedWriter.write(insertStatement);
                bufferedWriter.newLine();
            }

            bufferedWriter.write("commit;");
            bufferedWriter.newLine();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private List<String> buildInsertStatement(List<Object[]> dataList, List<AllTableColumn> list,  String tableName){
        List<String> insertStatements = new ArrayList<>();

        for (Object[] record : dataList) {
            String insert = "insert into "+ tableName + "(" + getColumnQuery(list) +") values (";
            StringBuilder insertValues = new StringBuilder();
            for (Object object : record){
                if (!insertValues.toString().trim().isEmpty()){
                    insertValues.append(",");
                }
                if (object instanceof BigDecimal){
                    insertValues.append(formatBigDecimal(object));
                }
                else if (object instanceof String || object instanceof  Character){
                    insertValues.append(formatString(object));
                }
                else if (object instanceof Timestamp){
                    insertValues.append(formatDate(object));
                }
                else if (Optional.ofNullable(object).equals(Optional.empty())){
                    insertValues.append(object);
                }
                else {
                    throw new AssertionError("Unhandled data type" + object.getClass());
                }
            }
            insert = insert + insertValues + ");";
            insertStatements.add(insert);
        }
        return insertStatements;
    }

    private String formatDate(Object o){

        LocalDateTime localDateTime = ((Timestamp) o).toLocalDateTime() ;
        return "to_date('"
                + StringUtils.leftPad(String.valueOf(localDateTime.getDayOfMonth()), 2, "0")
                +"/"
                + StringUtils.leftPad(String.valueOf(localDateTime.getMonthValue()), 2, "0")
                +"/"
                + localDateTime.getYear()
                +" "
                + StringUtils.leftPad(String.valueOf(localDateTime.getHour()), 2, "0")
                +":"
                + StringUtils.leftPad(String.valueOf(localDateTime.getMinute()), 2, "0")
                +":"
                + StringUtils.leftPad(String.valueOf(localDateTime.getSecond()), 2, "0")
                +"', 'DD/MM/YYYY HH24:MI:SS')";
    }

    private String formatString(Object o){
        return "'"+o+"'";
    }

    private String formatBigDecimal(Object o){
        return o.toString();
    }

    /**
     * Generates an sql statement given the list of columns and the where clause
     * @param list
     * @param whereClause
     * @return String
     */
    private String generateStringQuery(List<AllTableColumn> list, String whereClause) {
        if (list.isEmpty()) {
            throw new AssertionError("Table does not exist");
        }
        if (whereClause.trim().isEmpty()) {
            throw new AssertionError("Where clause cannot be null");
        }
        return "select " + getColumnQuery((list))
                + " from " + list.stream().findFirst().map(AllTableColumn::getTableName).get()
                + " where " + whereClause;
    }

    /**
     * Method that builds the list of column
     * @param list
     * @return String of {@link AllTableColumn column}
     */
    private String getColumnQuery(List<AllTableColumn> list) {
        return list
                .stream()
                .map(AllTableColumn::getColumnName)
                .reduce("",
                        (generatedQuery, column) -> (generatedQuery + column + " "),
                        String::concat)
                .trim()
                .replace(" ", ", ");
    }
}
