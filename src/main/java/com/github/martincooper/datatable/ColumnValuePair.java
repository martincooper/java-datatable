package com.github.martincooper.datatable;

/**
 * ColumnValuePair class. Holds mapping between a column and a value.
 * Created by Martin Cooper on 24/08/2017.
 */
public class ColumnValuePair {

    private final IDataColumn column;
    private final Object value;

    /**
     * ColumnValuePair constructor.
     * @param column The column.
     * @param value The value.
     */
    public ColumnValuePair(IDataColumn column, Object value) {
        this.column = column;
        this.value = value;
    }

    /**
     * The column.
     * @return Returns the column.
     */
    public IDataColumn column() {
        return this.column;
    }

    /**
     * The value.
     * @return Returns the value.
     */
    public Object value() {
        return this.value;
    }
}
