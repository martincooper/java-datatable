import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Examples for Data Table row filtering.
 * Created by Martin Cooper on 20/07/2017.
 */
public class Filtering {

    //@Ignore
    @Test
    public void testDataTableWithRowData() {
        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "AA", "BB")
                .withColumn(Integer.class, "IntCol", 1, 100, 1000, 10000)
                .withColumn(Double.class, "DoubleCol", 1.1, 5.5, 10.5, 100.5)
                .withColumn(Boolean.class, "BoolCol", true, true, false, false)
                .build().get();

        DataView view = table.filter(row -> "BB".equals(row.get(0)));

        assertTrue(view.rowCount() == 2);
    }
}
