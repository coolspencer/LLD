package rdms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private String databaseName;
    private Map<String, Table> tableMap = new HashMap();

    public Database(String databaseName) {
        this.databaseName = databaseName;
    }

    public void createTable(String tableName, List<Column> columns) {

        if (checkIfTableExists(tableName))
            System.out.println("TableName: " + tableName + " already exists!");
        Table table = new Table(tableName, columns);
        tableMap.put(tableName, table);
        return;
    }

    public void dropTable(String tableName) {

        if (!checkIfTableExists(tableName))
            return;
        tableMap.remove(tableName);
        System.out.println("TableName: " + tableName + " dropped!");
        return;
    }

    public void truncate(String tableName) {

        if (!checkIfTableExists(tableName))
            return;
        Table table = tableMap.get(tableName);
        table.truncateRows();
    }

    public void insertTableRows(String tableName, Map<Column, Object> columnValues) {

        if (!checkIfTableExists(tableName))
            return;
        Table table = tableMap.get(tableName);
        table.insertRow(columnValues);
    }

    public void printTableAllRows(String tableName) {

        if (!checkIfTableExists(tableName))
            return;
        Table table = tableMap.get(tableName);
        table.printRows();
    }

    public void filterTableRecordsByColumnValue(String tableName, Column column, Object value) {

        if (!checkIfTableExists(tableName))
            return;
        Table table = tableMap.get(tableName);
        table.getRecordsByColumnValue(column, value);
    }

    private Boolean checkIfTableExists(String tableName) {

        if (!tableMap.containsKey(tableName)) {
            System.out.println("TableName: " + tableName + " does not exists");
            return false;
        }
        return true;
    }
}