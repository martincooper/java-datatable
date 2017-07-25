package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.DataRow;
import io.vavr.collection.Seq;

/**
 * DataRowSorter. Handles the details of the quick sorting of DataRows.
 * Created by Martin Cooper on 25/07/2017.
 */
public class DataRowSorter {

    /**
     * Recursive sort method, handles multi-sort on columns.
     *
     * @param rowOne The first row to compare.
     * @param rowTwo The second row to compare.
     * @param sortItems The collection of Sort Item details.
     * @return Returns a single integer for the sort.
     */
    private static int compareBySortItem(DataRow rowOne, DataRow rowTwo, Seq<SortItem> sortItems) {

        // No more items to sort, then return 0;
        if (sortItems.isEmpty()) return 0;

        // Compare the values in the two rows for the current sort item.
        Integer compareResult = compareValues(rowOne, rowTwo, sortItems.head());

        // If it's the same, then compare again on the next item until finished.
        return compareResult == 0
                ? compareBySortItem(rowOne, rowTwo, sortItems.tail())
                : compareResult;
    }

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
