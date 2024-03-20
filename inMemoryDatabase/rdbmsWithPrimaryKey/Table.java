import java.util.Date;
import java.util.HashMap;
import Row
public class Table {
    private String tableName;
    private HashMap<String, Row> rows;
    private Date createdAt;

    public Table(String tableName) {
        this.tableName = tableName;
        this.rows = new HashMap<>();
        this.createdAt = new Date();
    }

    public void insertEntry(String rowId, HashMap<String, String> columnsMap) {
        if (rows.containsKey(rowId)) {
            System.out.println("Duplicate primary key : " + " Insertion failed");
        } else {
            Row row =  new Row(rowId, columnsMap, new Date(), new Date());
            rows.put(rowId, row);
            System.out.println("Successfully added a row");
        }
    }

    public void updateEntry(String rowId, HashMap<String, String>valuesMap) {
        Row row = rows.get(rowId);
        valuesMap.forEach( (k, v) -> {
            row.getColumnValuesMap().put(k, v);
        });
        System.out.println("Row successfully updated");
        row.setUpdatedAt(new Date());
    }

    public void deleteEntry(String rowId) {
        System.out.println("Row successfully deleted");
        rows.remove(rowId);
    }

    public HashMap<String, String> readEntry(String rowId) {
        return rows.get(rowId).getColumnValuesMap();
    }


    public HashMap<String, Row> getRows() {
        return rows;
    }

    public void setRows(HashMap<String, Row> rows) {
        this.rows = rows;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}