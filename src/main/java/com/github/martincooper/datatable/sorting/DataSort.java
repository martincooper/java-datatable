package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.*;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.List;

/**
 * DataSort. Main QuickSort implementation.
 * Created by Martin Cooper on 25/07/2017.
 */
public class DataSort {

    /**
     * Convert the data to a List<DataRow> which we can then sort, and rebuild back into a DataView.
     *
     * @param table The original table.
     * @param dataRows The collection of data rows.
     * @param sortItems The sort item details.
     * @return Returns a sorted Data View.
     */
    private DataView performQuickSort(DataTable table, Seq<DataRow> dataRows, Seq<SortItem> sortItems) {

        // Convert the data to a List<DataRow> which we can then sort, and rebuild back into a DataView.
        List<DataRow> rowList = dataRows.toJavaList();
        rowList.sort(new DataRowComparator(sortItems));

        return DataView.build(table, rowList).get();
    }

    /**
     * Validate the sort items and columns are ok to perform a sort.
     *
     * @param table The underlying data table.
     * @param sortItems The sort items.
     * @return Return the validation result.
     */
    private Try<Void> validateSortColumns(DataTable table, Seq<SortItem> sortItems) {
        return validateSortColumnIdentity(table, sortItems)
                .flatMap(this::validateColumnsAreComparable);
    }

    /**
     * Validates that all the columns specified in the collection of items
     * to sort are actually contained and identifiable in the DataTable.
     *
     * @param table The table to sort.
     * @param sortItems The collection of sort items.
     * @return Returns validation as a Try.
     */
    private Try<Seq<IDataColumn>> validateSortColumnIdentity(DataTable table, Seq<SortItem> sortItems) {

        // Validate we can get the column for all sort items.
        Seq<Try<IDataColumn>> checkCols = sortItems.map(item -> item.getColumn(table));

        // Any failures, return error else return columns.
        return checkCols.find(Try::isFailure).isEmpty()
                ? Try.success(checkCols.map(Try::get))
                : DataTableException.tryError("Column for Sort Item not found.");
    }

    /**
     * Validates that all the columns being sorted are comparable.
     *
     * @param columns The columns to sort.
     * @return Returns the result as a Try : Success / Failure.
     */
    private Try<Void> validateColumnsAreComparable(Seq<IDataColumn> columns) {
        Option<IDataColumn> invalidCol = columns.find(col -> !col.IsComparable());

        return invalidCol.isEmpty()
                ? DataTableException.tryError("Column '" + invalidCol.get().name() + "' doesn't support comparable.")
                : Try.success(null);
    }
}
