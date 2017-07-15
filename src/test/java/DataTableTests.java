import com.sun.org.apache.xpath.internal.operations.Bool;
import io.vavr.collection.List;
import io.vavr.collection.Vector;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit Tests for the DataTable class.
 * Created by Martin on 15/07/2017.
 */
public class DataTableTests {

    @Test
    public void testEmptyDataTableCreation() {
        DataTable table = DataTable.build("NewTable");

        assertNotNull(table);
        assertEquals(table.getName(), "NewTable");
        assertEquals(table.getColumns().size(), 0);
    }

    @Test
    public void testSimpleDataTableCreation() {
        IDataColumn[] cols = { createStringColumn(), createIntegerColumn(), createBooleanColumn() };
        Try<DataTable> table = DataTable.build("NewTable", cols);

        assertTrue(table.isSuccess());
        assertEquals(table.get().getName(), "NewTable");
        assertEquals(table.get().getColumns().size(), 3);
        assertEquals(table.get().getColumns().get(0).getName(), "StringCol");
        assertEquals(table.get().getColumns().get(1).getName(), "IntegerCol");
        assertEquals(table.get().getColumns().get(2).getName(), "BooleanCol");
    }

    private DataColumn<String> createStringColumn() {
        List<String> data = List.of("AA", "BB", "CC");
        return new DataColumn<>(String.class, "StringCol", data);
    }

    private DataColumn<Integer> createIntegerColumn() {
        List<Integer> data = List.of(5, 7, 9);
        return new DataColumn<>(Integer.class, "IntegerCol", data);
    }

    private DataColumn<Boolean> createBooleanColumn() {
        List<Boolean> data = List.of(true, false, true);
        return new DataColumn<>(Boolean.class, "BooleanCol", data);
    }
}
