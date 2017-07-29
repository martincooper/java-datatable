import com.github.martincooper.datatable.DataTable;
import com.github.martincooper.datatable.DataTableBuilder;
import com.github.martincooper.datatable.DataView;
import com.github.martincooper.datatable.sorting.SortOrder;
import io.vavr.collection.List;
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
                .withColumn(String.class, "StrCol", List.of("AA", "BB", "CC", "DD"))
                .withColumn(Integer.class, "IntCol", List.of(3, 5, 9, 11))
                .withColumn(Boolean.class, "BoolCol", List.of(true, false, true, false))
                .build().get();

        Try<DataView> view = table.quickSort("IntCol", SortOrder.Descending);

        assertTrue(view.isSuccess());

        assertTrue(view.get().row(0).getAs(Integer.class, "IntCol") == 11);
        assertTrue(view.get().row(1).getAs(Integer.class, "IntCol") == 9);
        assertTrue(view.get().row(2).getAs(Integer.class, "IntCol") == 5);
        assertTrue(view.get().row(3).getAs(Integer.class, "IntCol") == 3);
    }
}
