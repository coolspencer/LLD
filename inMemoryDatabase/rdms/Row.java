package rdms;

import java.util.Map;

public class Row {

    private Integer rowId;
    private Map<Column, Object> columnData;

    public Row(Integer rowId, Map<Column, Object> columnData) {

        this.rowId = rowId;
        this.columnData = columnData;
    }

    public Integer getRowId() {

        return this.rowId;
    }

    public Map<Column, Object> getColumnData() {

        return this.columnData;
    }
}