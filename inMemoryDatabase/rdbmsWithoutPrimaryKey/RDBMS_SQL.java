import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum DataType {
    INTEGER, STRING
}

class Column {
    String name;
    DataType type;

    public Column(String name, DataType type) {
        this.name = name;
        this.type = type;
    }
}

class Row {
    Object[] values;

    public Row(Object[] values) {
        this.values = values;
    }
}

class Table {
    String tableName;
    List<Column> columns;
    List<Row> rows;

    public Table(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    public void insertRecord(Object[] record) {
        if (record.length != columns.size()) {
            throw new IllegalArgumentException("Record length doesn't match column count.");
        }
        rows.add(new Row(record));
    }

    public void printAllRecords() {
        for (Row row : rows) {
            for (int i = 0; i < row.values.length; i++) {
                System.out.print(columns.get(i).name + ": " + row.values[i] + "\t");
            }
            System.out.println();
        }
    }

    public void filterAndDisplay(String columnName, Object value) {
        int columnIndex = -1;
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).name.equals(columnName)) {
                columnIndex = i;
                break;
            }
        }
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Column '" + columnName + "' not found.");
        }
        final int c=columnIndex;
        List<Row> filteredRows = rows.stream()
                .filter(row -> row.values[c].equals(value))
                .collect(Collectors.toList());

        if (filteredRows.isEmpty()) {
            System.out.println("No records found matching the criteria.");
            return;
        }

        System.out.println("Records matching '" + value + "':");
        for (Row row : filteredRows) {
            for (int i = 0; i < row.values.length; i++) {
                System.out.print(columns.get(i).name + ": " + row.values[i] + "\t");
            }
            System.out.println();
        }
    }
}

public class RDBMS_SQL {
    List<Table> tables;

    public RDBMS_SQL() {
        this.tables = new ArrayList<>();
    }

    public void createTable(String tableName, List<Column> columns) {
        tables.add(new Table(tableName, columns));
    }

    public void deleteTable(String tableName) {
        tables.removeIf(table -> table.tableName.equals(tableName));
    }

    public static void main(String[] args) {
        RDBMS_SQL db = new RDBMS_SQL();

        // Create table
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", DataType.INTEGER));
        columns.add(new Column("name", DataType.STRING));
        db.createTable("employees", columns);

        // Insert records
        db.tables.get(0).insertRecord(new Object[] { 1, "John" });
        db.tables.get(0).insertRecord(new Object[] { 2, "Alice" });
        db.tables.get(0).insertRecord(new Object[] { 3, "Bob" });

        // Print all records
        db.tables.get(0).printAllRecords();

        // Filter and display records
        db.tables.get(0).filterAndDisplay("name", "Alice");
    }
}