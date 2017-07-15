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
