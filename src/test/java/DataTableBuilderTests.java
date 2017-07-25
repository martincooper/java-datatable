import datatable.DataTable;
import datatable.DataTableBuilder;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Data Table Builder.
 * Created by Martin Cooper on 18/07/2017.
 */
public class DataTableBuilderTests {

    @Test
    public void testBuilderDataTableCreationFromIterable() {
        Try<DataTable> table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", List.of("AA", "BB", "CC", "DD"))
                .withColumn(Integer.class, "IntCol", List.of(3, 5, 9, 11))
                .withColumn(Boolean.class, "BoolCol", List.of(true, false, true, false))
                .build();

        assertTrue(table.isSuccess());
        assertTrue(table.get().columns().count() == 3);
        assertTrue(table.get().rowCount() == 4);
    }

    @Test
    public void testBuilderDataTableCreationFromVarArgs() {
        Try<DataTable> table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "CC", "DD")
                .withColumn(Integer.class, "IntCol", 3, 5, 9, 11)
                .withColumn(Boolean.class, "BoolCol", true, false, true, false)
                .build();

        assertTrue(table.isSuccess());
        assertTrue(table.get().columns().count() == 3);
        assertTrue(table.get().rowCount() == 4);
    }
}
