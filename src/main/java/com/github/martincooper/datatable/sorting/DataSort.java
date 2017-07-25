package com.github.martincooper.datatable.sorting;

import com.github.martincooper.datatable.DataTableException;
import com.github.martincooper.datatable.IDataColumn;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;

/**
 * DataSort. Main QuickSort implementation.
 * Created by Martin Cooper on 25/07/2017.
 */
public class DataSort {


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
