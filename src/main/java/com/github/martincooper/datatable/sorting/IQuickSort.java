package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.DataView;
import io.vavr.control.Try;

/**
 * Created by Martin Cooper on 29/07/2017.
 */
public interface IQuickSort {

    /**
     * Table QuickSort by single column name.
     *
     * @param columnName The column name to sort.
     * @return Returns the results as a sorted Data View.
     */
    Try<DataView> quickSort(String columnName);

    /**
     * Table QuickSort by single column name and a sort order.
     *
     * @param columnName The column name to sort.
     * @param sortOrder The sort order.
     * @return Returns the results as a sorted Data View.
     */
    Try<DataView> quickSort(String columnName, SortOrder sortOrder);

    /**
     * Table QuickSort by single column index.
     *
     * @param columnIndex The column index to sort.
     * @return Returns the results as a sorted Data View.
     */
    Try<DataView> quickSort(Integer columnIndex);

    /**
     * Table QuickSort by single column index and a sort order.
     *
     * @param columnIndex The column index to sort.
     * @param sortOrder The sort order.
     * @return Returns the results as a sorted Data View.
     */
    Try<DataView> quickSort(Integer columnIndex, SortOrder sortOrder);

    /**
     * Table QuickSort by single sort item.
     *
     * @param sortItem The sort item.
     * @return Returns the results as a sorted Data View.
     */
    Try<DataView> quickSort(SortItem sortItem);

    /**
     * Table QuickSort by multiple sort items.
     *
     * @param sortItems The sort items.
     * @return Returns the results as a sorted Data View.
     */
    Try<DataView> quickSort(Iterable<SortItem> sortItems);
}

