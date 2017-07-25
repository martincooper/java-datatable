import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for the DataTable class.
 * Created by Martin on 15/07/2017.
 */
public class DataTableTests {

    @Test
    public void testEmptyDataTableCreation() {
        DataTable table = DataTable.build("NewTable");

        assertNotNull(table);
        assertEquals(table.name(), "NewTable");
        assertEquals(table.columns().count(), 0);
        assertTrue(table.rowCount() == 0);
    }

    @Test
    public void testSimpleDataTableCreation() {
        IDataColumn[] cols = { createStringColumn(), createIntegerColumn(), createBooleanColumn() };
        Try<DataTable> table = DataTable.build("NewTable", cols);

        assertTrue(table.isSuccess());
        assertEquals(table.get().name(), "NewTable");
        assertEquals(table.get().columns().count(), 3);
        assertTrue(table.get().rowCount() == 3);
        assertEquals(table.get().column(0).name(), "StringCol");
        assertEquals(table.get().column(1).name(), "IntegerCol");
        assertEquals(table.get().column(2).name(), "BooleanCol");
    }

    @Test
    public void testDuplicateColumnNames() {
        List<String> dataOne = List.of("AA", "BB", "CC");
        IDataColumn colOne = new DataColumn<>(String.class, "StringCol", dataOne);

        List<String> dataTwo = List.of("XX", "YY", "ZZ");
        IDataColumn colTwo = new DataColumn<>(String.class, "StringCol", dataTwo);

        IDataColumn[] cols = { colOne, colTwo };
        Try<DataTable> table = DataTable.build("NewTable", cols);

        assertTrue(table.isFailure());
        assertEquals(table.getCause().getMessage(), "Columns contain duplicate names.");
    }

    @Test
    public void testColumnLengthMismatch() {
        List<String> dataOne = List.of("AA", "BB", "CC", "DD");
        IDataColumn colOne = new DataColumn<>(String.class, "StringCol", dataOne);

        List<String> dataTwo = List.of("XX", "YY", "ZZ");
        IDataColumn colTwo = new DataColumn<>(String.class, "StringColTwo", dataTwo);

        IDataColumn[] cols = { colOne, colTwo };
        Try<DataTable> table = DataTable.build("NewTable", cols);

        assertTrue(table.isFailure());
        assertEquals(table.getCause().getMessage(), "Columns have different lengths.");
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
