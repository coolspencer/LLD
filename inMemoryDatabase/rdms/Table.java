package rdms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

    private Integer autoIncrementId;
    private String name;
    private Map<String, Column> columnMap = new HashMap();
    List<Row> rows = new ArrayList();

    public Table(String tableName, List<Column> columns) {

        this.autoIncrementId = 1;
        this.name = tableName;
        populateColumnMap(columns);
    }

    protected void truncateRows() {

        this.rows.clear();
    }

    protected void insertRow(Map<Column, Object> columnValues) {

        for (Column column : columnValues.keySet()) {
            if (!checkIfColumnExists(column.getColumnName()))
                return;
        }
        Integer rowId = getAutoIncrementId();
        Map<Column, Object> columnData = new HashMap(columnValues);
        Row row = new Row(rowId, columnData);
        this.rows.add(row);
    }

    protected void printRows() {

        System.out.println("Printing all rows for Table: " + this.name);
        printRecords(this.rows);
    }

    protected void getRecordsByColumnValue(Column column, Object value) {

        List<Row> rows = new ArrayList();
        for (Row row : this.rows) {
            Object columnValue = row.getColumnData().get(column);
            if (columnValue.equals(value)) {
                rows.add(row);
            }
        }
        System.out.println("Printing matching rows for Table: " + this.name);
        printRecords(rows);
    }

    private void printRecords(List<Row> rows) {

        System.out.print("\t");
        for (Map.Entry<String, Column> entry : this.columnMap.entrySet()) {
            System.out.print("\t" + entry.getKey() + "\t");
        }
        for (Row row : rows) {
            System.out.print("\n\t" + row.getRowId() + ".");
            for (Map.Entry<Column, Object> entry : row.getColumnData().entrySet()) {
                System.out.print("\t" + entry.getValue() + "\t");
            }
        }
        System.out.print("\n");
    }

    private void populateColumnMap(List<Column> columns) {

        for (Column column : columns) {
            columnMap.put(column.getColumnName(), column);
        }
    }

    private synchronized Integer getAutoIncrementId() {
        return this.autoIncrementId++;
    }

    private Boolean checkIfColumnExists(String columnName) {

        if (!columnMap.containsKey(columnName)) {
            System.out.println("TableName: " + this.name + " does not contains column: " + columnName);
            return false;
        }
        return true;
    }
}