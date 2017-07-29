import com.github.martincooper.datatable.DataTable;
import com.github.martincooper.datatable.DataTableBuilder;
import com.github.martincooper.datatable.DataView;
import com.github.martincooper.datatable.sorting.SortItem;
import com.github.martincooper.datatable.sorting.SortOrder;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for Quick Sorting.
 * Created by Martin Cooper on 29/07/2017.
 */
public class QuickSortTests {

    @Test
    public void testSimpleQuickSort() {

        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(Integer.class, "IntCol", List.of(3, 20, 4, 18, 0, -30, 100))
                .build().get();

        Try<DataView> view = table.quickSort("IntCol", SortOrder.Descending);

        assertTrue(view.isSuccess());

        // Check IntCol values are in descending order.
        assertTrue(view.get().row(0).getAs(Integer.class, "IntCol") == 100);
        assertTrue(view.get().row(1).getAs(Integer.class, "IntCol") == 20);
        assertTrue(view.get().row(2).getAs(Integer.class, "IntCol") == 18);
        assertTrue(view.get().row(3).getAs(Integer.class, "IntCol") == 4);
        assertTrue(view.get().row(4).getAs(Integer.class, "IntCol") == 3);
        assertTrue(view.get().row(5).getAs(Integer.class, "IntCol") == 0);
        assertTrue(view.get().row(6).getAs(Integer.class, "IntCol") == -30);
    }

    @Test
    public void testMultiQuickSort() {

        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(Integer.class, "IndexCol", List.of(1, 2, 3, 4, 5, 6, 7))
                .withColumn(Integer.class, "NumberCol", List.of(2, 3, 3, 4, 4, 5, 5))
                .withColumn(String.class, "StringCol", List.of("aa", "bb", "cc", "dd", "ee", "ff", "gg"))
                .build().get();

        SortItem sortOne = new SortItem("NumberCol", SortOrder.Ascending);
        SortItem sortTwo = new SortItem("StringCol", SortOrder.Descending);

        Try<DataView> view = table.quickSort(Stream.of(sortOne, sortTwo));

        assertTrue(view.isSuccess());

        // Check Index Column values are as expected.
        assertTrue(view.get().row(0).getAs(Integer.class, "IndexCol") == 1);
        assertTrue(view.get().row(1).getAs(Integer.class, "IndexCol") == 3);
        assertTrue(view.get().row(2).getAs(Integer.class, "IndexCol") == 2);
        assertTrue(view.get().row(3).getAs(Integer.class, "IndexCol") == 5);
        assertTrue(view.get().row(4).getAs(Integer.class, "IndexCol") == 4);
        assertTrue(view.get().row(5).getAs(Integer.class, "IndexCol") == 7);
        assertTrue(view.get().row(6).getAs(Integer.class, "IndexCol") == 6);
    }
}
