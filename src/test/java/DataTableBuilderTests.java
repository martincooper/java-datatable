import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Data Table Builder.
 * Created by Martin on 18/07/2017.
 */
public class DataTableBuilderTests {

    @Test
    public void testBuilderDataTableCreationFromIterable() {
        Try<DataTable> table = DataTableBuilder
                .build("NewTable")
                .withColumn(String.class, "StrCol", List.of("AA", "BB", "CC"))
                .withColumn(Integer.class, "IntCol", List.of(3, 5, 9))
                .withColumn(Boolean.class, "BoolCol", List.of(true, false, true))
                .create();

        assertTrue(table.isSuccess());
    }

    @Test
    public void testBuilderDataTableCreationFromVarArgs() {
        Try<DataTable> table = DataTableBuilder
                .build("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "CC")
                .withColumn(Integer.class, "IntCol", 3, 5, 9)
                .withColumn(Boolean.class, "BoolCol", true, false, true)
                .create();

        assertTrue(table.isSuccess());
    }
}
