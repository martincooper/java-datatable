package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.DataRow;

/**
 * DataRowSorter. Handles the quick sorting of DataRows.
 * Created by Martin Cooper on 25/07/2017.
 */
public class DataRowSorter {

    private static int compareValues(DataRow rowOne, DataRow rowTwo, SortItem sortItem) {

        Object valueOne = valueFromIdentity(rowOne, sortItem.columnIdentity());
        Object valueTwo = valueFromIdentity(rowTwo, sortItem.columnIdentity());

        return sortItem.sortOrder() == SortOrder.Ascending
                ? compareValues(valueOne, valueTwo)
                : compareValues(valueTwo, valueOne);
    }

    private static Object valueFromIdentity(DataRow dataRow, ColumnIdentity columnIdentity) {
        return columnIdentity.getCellData(dataRow).get();
    }

    private static int compareValues(Object valueOne, Object valueTwo) {
        return valueOne == null
                ? (valueTwo == null ? 0 : Integer.MIN_VALUE)
                : (valueTwo == null ? Integer.MAX_VALUE : ((Comparable)valueOne).compareTo(valueTwo));
    }
}
