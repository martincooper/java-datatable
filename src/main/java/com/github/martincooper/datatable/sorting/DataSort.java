package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.*;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.util.List;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;

/**
 * DataSort. Main QuickSort implementation.
 * Created by Martin Cooper on 25/07/2017.
 */
public class DataSort {

    public static Try<DataView> quickSort(DataTable table, SortItem sortItem) {
        return quickSort(table, table.rows().asSeq(), Stream.of(sortItem));
    }

    public static Try<DataView> quickSort(DataTable table, Seq<SortItem> sortItems) {
        return quickSort(table, table.rows().asSeq(), sortItems);
    }

    public static Try<DataView> quickSort(DataView dataView, SortItem sortItem) {
        return quickSort(dataView.table(), dataView.rows().asSeq(), Stream.of(sortItem));
    }

    public static Try<DataView> quickSort(DataView dataView, Seq<SortItem> sortItems) {
        return quickSort(dataView.table(), dataView.rows().asSeq(), sortItems);
    }

    public static Try<DataView> quickSort(DataTable table, Seq<DataRow> rows, Seq<SortItem> sortItems) {
        return Match(validateSortColumns(table, sortItems)).of(
                Case($Success($()), cols -> Try.success(performQuickSort(table, rows, sortItems))),
                Case($Failure($()), Try::failure)
        );
    }

    /**
     * Convert the data to a List<DataRow> which we can then sort, and rebuild back into a DataView.
     *
     * @param table The original table.
     * @param dataRows The collection of data rows.
     * @param sortItems The sort item details.
     * @return Returns a sorted Data View.
     */
    private static DataView performQuickSort(DataTable table, Seq<DataRow> dataRows, Seq<SortItem> sortItems) {

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
    private static Try<Void> validateSortColumns(DataTable table, Seq<SortItem> sortItems) {
        return validateSortColumnIdentity(table, sortItems)
                .flatMap(DataSort::validateColumnsAreComparable);
    }

    /**
     * Validates that all the columns specified in the collection of items
     * to sort are actually contained and identifiable in the DataTable.
     *
     * @param table The table to sort.
     * @param sortItems The collection of sort items.
     * @return Returns validation as a Try.
     */
    private static Try<Seq<IDataColumn>> validateSortColumnIdentity(DataTable table, Seq<SortItem> sortItems) {

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
    private static Try<Void> validateColumnsAreComparable(Seq<IDataColumn> columns) {
        Option<IDataColumn> invalidCol = columns.find(col -> !col.IsComparable());

        return invalidCol.isEmpty()
                ? Try.success(null)
                : DataTableException.tryError("Column '" + invalidCol.get().name() + "' doesn't support comparable.");
    }
}
