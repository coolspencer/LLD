package rdms;
public class Column {

    private String columnName;
    public enum Type {INT,STRING};
    private Type columnType;

    public Column(String columnName, Type columnType) {

        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return this.columnName;
    }
}