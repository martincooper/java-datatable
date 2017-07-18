import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example DataTable usage using the Data Table Builder.
 * Created by Martin on 18/07/2017.
 */
public class DataTableExamples {

    @Test
    public void testEmptyDataTableCreation() {

        Try<DataTable> table = DataTableBuilder
                .build("NewTable")
                .withColumn(String.class, "StrCol", List.of("AA", "BB", "CC"))
                .withColumn(Integer.class, "IntCol", List.of(3, 5, 9))
                .withColumn(Boolean.class, "BoolCol", List.of(true, false, true))
                .create();

        assertTrue(table.isSuccess());
    }
}
