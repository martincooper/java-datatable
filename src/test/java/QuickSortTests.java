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
}
