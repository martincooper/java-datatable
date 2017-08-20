package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.IDataColumn;

/**
 * ColumnValuePair class. Used to map value to their respective columns.
 * Created by Martin Cooper on 30/07/2017.
 */
public class ColumnValuePair {

    private final IDataColumn column;
    private final Object value;

    /**
     * ColumnValuePair constructor.
     *
     * @param column The column.
     * @param value The value.
     */
    public ColumnValuePair(IDataColumn column, Object value) {
        this.column = column;
        this.value = value;
    }

    /**
     * The column in the Column Value Pair
     *
     * @return Returns the column.
     */
    public IDataColumn getColumn() {
        return column;
    }

    /**
     * The value in the Column Value Pair
     *
     * @return Returns the value.
     */
    public Object getValue() {
        return value;
    }
}
