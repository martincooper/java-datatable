import datatable.DataTable;
import datatable.DataTableBuilder;
import datatable.DataView;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Examples for Data Table row filtering.
 * Created by Martin Cooper on 20/07/2017.
 */
public class DataTableFilteringTests {

    @Test
    public void testUntypedDataTableFilterByColumnIndex() {
        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "AA", "BB")
                .withColumn(Integer.class, "IntCol", 1, 100, 1000, 10000)
                .withColumn(Double.class, "DoubleCol", 1.1, 5.5, 10.5, 100.5)
                .withColumn(Boolean.class, "BoolCol", true, true, false, false)
                .build().get();

        // Filter the table, only returning where BoolCol values == false;
        DataView view = table.filter(row -> !(boolean)row.get(3).get());

        assertTrue(view.name().equals(table.name()));
        assertTrue(view.rowCount() == 2);
        assertTrue(view.row(0).getAs(Integer.class, "IntCol") == 1000);
        assertTrue(view.row(1).getAs(Integer.class, "IntCol") == 10000);
        assertTrue(view.row(0).getAs(Double.class, "DoubleCol") == 10.5);
        assertTrue(view.row(1).getAs(String.class, "StrCol").equals("BB"));
    }

    @Test
    public void testUntypedDataTableFilterByColumnName() {
        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "AA", "BB")
                .withColumn(Integer.class, "IntCol", 1, 100, 1000, 10000)
                .withColumn(Double.class, "DoubleCol", 1.1, 5.5, 10.5, 100.5)
                .withColumn(Boolean.class, "BoolCol", true, true, false, false)
                .build().get();

        // Filter the table, only returning where BoolCol values == false;
        DataView view = table.filter(row -> !(boolean)row.get("BoolCol").get());

        assertTrue(view.name().equals(table.name()));
        assertTrue(view.rowCount() == 2);
        assertTrue(view.row(0).getAs(Integer.class, "IntCol") == 1000);
        assertTrue(view.row(1).getAs(Integer.class, "IntCol") == 10000);
        assertTrue(view.row(0).getAs(Double.class, "DoubleCol") == 10.5);
        assertTrue(view.row(1).getAs(String.class, "StrCol").equals("BB"));
    }

    @Test
    public void testTypedDataTableFilterByColumnName() {
        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "CC", "DD")
                .withColumn(Integer.class, "IntCol", 10000, 1000, 100, 10)
                .withColumn(Double.class, "DoubleCol", 1.1, 5.5, 10.5, 100.5)
                .withColumn(Boolean.class, "BoolCol", true, true, false, false)
                .build().get();

        // Filter the table, only returning where IntCol values > 100 and DoubleCol values are < 10
        DataView view = table.filter(row ->
                row.getAs(Integer.class, "IntCol") > 100 &&
                row.getAs(Double.class, "DoubleCol") < 10);

        assertTrue(view.rowCount() == 2);
        assertTrue(view.row(0).getAs(Integer.class, "IntCol") == 10000);
        assertTrue(view.row(1).getAs(Integer.class, "IntCol") == 1000);
        assertTrue(view.row(0).getAs(Double.class, "DoubleCol") == 1.1);
        assertTrue(view.row(0).getAs(String.class, "StrCol").equals("AA"));
    }

    @Test
    public void testTypedDataTableFilterByColumnIndex() {
        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "CC", "DD")
                .withColumn(Integer.class, "IntCol", 10000, 1000, 100, 10)
                .withColumn(Double.class, "DoubleCol", 1.1, 5.5, 10.5, 100.5)
                .withColumn(Boolean.class, "BoolCol", true, true, false, false)
                .build().get();

        // Filter the table, only returning where IntCol values > 100;
        DataView view = table.filter(row -> row.getAs(Integer.class, 1) > 100);

        assertTrue(view.rowCount() == 2);
        assertTrue(view.row(0).getAs(Integer.class, "IntCol") == 10000);
        assertTrue(view.row(1).getAs(Integer.class, "IntCol") == 1000);
        assertTrue(view.row(0).getAs(Double.class, "DoubleCol") == 1.1);
        assertTrue(view.row(0).getAs(String.class, "StrCol").equals("AA"));
    }
}
