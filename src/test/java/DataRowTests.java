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
        Try<DataRow> row = DataRow.build(table, 1);

        assertTrue(row.isSuccess());

        Object[] data = row.get().data();

        assertTrue(data.length == 3);
        assertTrue(data[0] == "BB");
        assertTrue((Integer)data[1] == 7);
        assertFalse((Boolean)data[2]);
    }

    @Test
    public void testDataRowGetItemAsUntypedByColIndex() {
        DataTable table = createDataTable();
        DataRow row = DataRow.build(table, 1).get();
        Try<Object> itemData = row.get(1);

        assertTrue(itemData.isSuccess());
        assertTrue((Integer)itemData.get() == 7);
    }

    @Test
    public void testDataRowGetItemAsUntypedByColName() {
        DataTable table = createDataTable();
        DataRow row = DataRow.build(table, 1).get();
        Try<Object> itemData = row.get("IntegerCol");

        assertTrue(itemData.isSuccess());
        assertTrue((Integer)itemData.get() == 7);
    }

    @Test
    public void testDataRowGetUncheckedItemAsTypedByColIndex() {
        DataTable table = createDataTable();
        DataRow row = DataRow.build(table, 1).get();
        Object itemData = row.getAs(Integer.class, 1);

        assertTrue((Integer)itemData == 7);
    }

    @Test
    public void testDataRowGetUncheckedItemAsTypedByColName() {
        DataTable table = createDataTable();
        DataRow row = DataRow.build(table, 1).get();
        Object itemData = row.getAs(Integer.class, "IntegerCol");

        assertTrue((Integer)itemData == 7);
    }

    @Test
    public void testDataRowGetCheckedItemAsTypedByColIndex() {
        DataTable table = createDataTable();
        DataRow row = DataRow.build(table, 1).get();
        Try<Integer> itemData = row.tryGetAs(Integer.class, 1);

        assertTrue(itemData.isSuccess());
        assertTrue(itemData.get() == 7);
    }

    @Test
    public void testDataRowGetCheckedItemAsTypedByColName() {
        DataTable table = createDataTable();
        DataRow row = DataRow.build(table, 1).get();
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
