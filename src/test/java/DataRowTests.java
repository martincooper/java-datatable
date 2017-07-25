import datatable.*;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit Tests for the DataRow class.
 * Created by Martin Cooper on 15/07/2017.
 */
public class DataRowTests {

    @Test
    public void testDataRowGetAllData() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);

        Object[] data = row.data();

        assertTrue(data.length == 3);
        assertTrue(data[0] == "BB");
        assertTrue((Integer)data[1] == 7);
        assertFalse((Boolean)data[2]);
    }

    @Test
    public void testDataRowGetItemAsUntypedByColIndex() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Try<Object> itemData = row.get(1);

        assertTrue(itemData.isSuccess());
        assertTrue((Integer)itemData.get() == 7);
    }

    @Test
    public void testDataRowGetItemAsUntypedByInvalidColIndex() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Try<Object> itemData = row.get(10000);

        assertTrue(itemData.isFailure());
        assertTrue(itemData.getCause().getMessage().equals("Column index out of bounds"));
    }

    @Test
    public void testDataRowGetItemAsUntypedByColName() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Try<Object> itemData = row.get("IntegerCol");

        assertTrue(itemData.isSuccess());
        assertTrue((Integer)itemData.get() == 7);
    }

    @Test
    public void testDataRowGetItemAsUntypedByInvalidColName() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Try<Object> itemData = row.get("InvalidColName");

        assertTrue(itemData.isFailure());
        assertTrue(itemData.getCause().getMessage().equals("Invalid column name."));
    }

    @Test
    public void testDataRowGetUncheckedItemAsTypeByColIndex() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Integer itemData = row.getAs(Integer.class, 1);

        assertTrue(itemData == 7);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDataRowGetUncheckedItemAsTypeByInvalidColIndex() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);

        // Should throw exception, when column index out of bounds.
        Integer itemData = row.getAs(Integer.class, 10000);
    }

    @Test(expected = DataTableException.class)
    public void testDataRowGetUncheckedItemAsInvalidTypeColIndex() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);

        // Should throw exception, requesting a type different to what it actually is.
        Boolean itemData = row.getAs(Boolean.class, 1);
    }

    @Test
    public void testDataRowGetUncheckedItemAsTypedByColName() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Integer itemData = row.getAs(Integer.class, "IntegerCol");

        assertTrue(itemData == 7);
    }

    @Test
    public void testDataRowGetCheckedItemAsTypedByColIndex() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Try<Integer> itemData = row.tryGetAs(Integer.class, 1);

        assertTrue(itemData.isSuccess());
        assertTrue(itemData.get() == 7);
    }

    @Test
    public void testDataRowGetCheckedItemAsTypedByColName() {
        DataTable table = createDataTable();
        DataRow row = table.row(1);
        Try<Integer> itemData = row.tryGetAs(Integer.class, "IntegerCol");

        assertTrue(itemData.isSuccess());
        assertTrue(itemData.get() == 7);
    }

    private DataTable createDataTable() {
        IDataColumn[] cols = { createStringColumn(), createIntegerColumn(), createBooleanColumn() };
        return DataTable.build("NewTable", cols).get();
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
