import com.github.martincooper.datatable.*;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit Tests for the DataRowCollectionModifiable class.
 * Created by Martin Cooper on 23/08/2017.
 */
public class DataRowModificationTests {

    // Test add row methods...

    @Test
    public void testDataAddRow() {
        DataTable table = createDataTable();

        Object[] rowValues = { "ZZ", 100, true };
        Try<DataTable> result = table.rows().add(rowValues);

        assertTrue(result.isSuccess());
        testDataTableOnAdd(result.get());
    }

    @Test
    public void testDataAddRowValues() {
        DataTable table = createDataTable();

        Try<DataTable> result = table.rows().addValues("ZZ", 100, true);

        assertTrue(result.isSuccess());
        testDataTableOnAdd(result.get());
    }

    private void testDataTableOnAdd(DataTable table) {
        assertTrue(table.rowCount() == 5);

        assertTrue(table.column("StrCol").valueAt(0) == "AA");
        assertTrue(table.column("StrCol").valueAt(1) == "BB");
        assertTrue(table.column("StrCol").valueAt(2) == "CC");
        assertTrue(table.column("StrCol").valueAt(3) == "DD");
        assertTrue(table.column("StrCol").valueAt(4) == "ZZ");

        assertTrue((int)table.column("IntCol").valueAt(0) == 3);
        assertTrue((int)table.column("IntCol").valueAt(1) == 5);
        assertTrue((int)table.column("IntCol").valueAt(2) == 9);
        assertTrue((int)table.column("IntCol").valueAt(3) == 11);
        assertTrue((int)table.column("IntCol").valueAt(4) == 100);

        assertTrue((boolean)table.column("BoolCol").valueAt(0));
        assertTrue(!(boolean)table.column("BoolCol").valueAt(1));
        assertTrue((boolean)table.column("BoolCol").valueAt(2));
        assertTrue(!(boolean)table.column("BoolCol").valueAt(3));
        assertTrue((boolean)table.column("BoolCol").valueAt(4));
    }

    @Test
    public void testDataAddRowWithInvalidType() {
        DataTable table = createDataTable();

        // Add data which includes an invalid type.
        Object[] rowValues = { "ZZ", 100, 500 };
        Try<DataTable> result = table.rows().add(rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("tryAdd failed. Item of invalid type passed."));
    }

    @Test
    public void testDataAddRowWithNulls() {
        DataTable table = createDataTable();

        // Add data which includes nulls for different types.
        Object[] rowValues = { null, null, null };
        Try<DataTable> result = table.rows().add(rowValues);

        assertTrue(result.isSuccess());
        assertTrue(result.get().column("StrCol").valueAt(4) == null);
        assertTrue(result.get().column("IntCol").valueAt(4) == null);
        assertTrue(result.get().column("BoolCol").valueAt(4) == null);
    }

    @Test
    public void testDataAddRowValuesWithNulls() {
        DataTable table = createDataTable();

        // Add data which includes nulls for different types.
        Try<DataTable> result = table.rows().addValues(null, null, null);

        assertTrue(result.isSuccess());
        assertTrue(result.get().column("StrCol").valueAt(4) == null);
        assertTrue(result.get().column("IntCol").valueAt(4) == null);
        assertTrue(result.get().column("BoolCol").valueAt(4) == null);
    }

    @Test
    public void testDataAddRowWithInvalidValueCount() {
        DataTable table = createDataTable();

        // Add data where the number of values doesn't match the number of columns.
        Object[] rowValues = { "ZZ" };
        Try<DataTable> result = table.rows().add(rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("Number of values does not match number of columns."));
    }

    // Test insert row methods...

    @Test
    public void testDataInsertRow() {
        DataTable table = createDataTable();

        Object[] rowValues = { "ZZ", 100, true };
        Try<DataTable> result = table.rows().insert(2, rowValues);

        assertTrue(result.isSuccess());
        testDataTableOnInsert(result.get());
    }

    @Test
    public void testDataInsertRowValues() {
        DataTable table = createDataTable();

        Try<DataTable> result = table.rows().insertValues(2, "ZZ", 100, true);

        assertTrue(result.isSuccess());
        testDataTableOnInsert(result.get());
    }

    private void testDataTableOnInsert(DataTable table) {
        assertTrue(table.rowCount() == 5);

        assertTrue(table.column("StrCol").valueAt(0) == "AA");
        assertTrue(table.column("StrCol").valueAt(1) == "BB");
        assertTrue(table.column("StrCol").valueAt(2) == "ZZ");
        assertTrue(table.column("StrCol").valueAt(3) == "CC");
        assertTrue(table.column("StrCol").valueAt(4) == "DD");

        assertTrue((int)table.column("IntCol").valueAt(0) == 3);
        assertTrue((int)table.column("IntCol").valueAt(1) == 5);
        assertTrue((int)table.column("IntCol").valueAt(2) == 100);
        assertTrue((int)table.column("IntCol").valueAt(3) == 9);
        assertTrue((int)table.column("IntCol").valueAt(4) == 11);

        assertTrue((boolean)table.column("BoolCol").valueAt(0));
        assertTrue(!(boolean)table.column("BoolCol").valueAt(1));
        assertTrue((boolean)table.column("BoolCol").valueAt(2));
        assertTrue((boolean)table.column("BoolCol").valueAt(3));
        assertTrue(!(boolean)table.column("BoolCol").valueAt(4));
    }

    @Test
    public void testDataInsertRowWithInvalidIndex() {
        DataTable table = createDataTable();

        // Insert data at an invalid index.
        Object[] rowValues = { "ZZ", 100, true };
        Try<DataTable> result = table.rows().insert(200, rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause() instanceof IndexOutOfBoundsException);
    }

    @Test
    public void testDataInsertRowWithInvalidType() {
        DataTable table = createDataTable();

        // Insert data which includes an invalid type.
        Object[] rowValues = { "ZZ", 100, 500 };
        Try<DataTable> result = table.rows().insert(2, rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("tryInsert failed. Item of invalid type passed."));
    }

    @Test
    public void testDataInsertRowWithInvalidValueCount() {
        DataTable table = createDataTable();

        // Insert data where the number of values doesn't match the number of columns.
        Object[] rowValues = { "ZZ" };
        Try<DataTable> result = table.rows().insert(2, rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("Number of values does not match number of columns."));
    }

    // Test replace / update row methods...

    @Test
    public void testDataReplaceRow() {
        DataTable table = createDataTable();

        Object[] rowValues = { "ZZ", 100, true };
        Try<DataTable> result = table.rows().replace(2, rowValues);

        assertTrue(result.isSuccess());
        testDataTableOnReplace(result.get());
    }

    @Test
    public void testDataReplaceRowValues() {
        DataTable table = createDataTable();

        Try<DataTable> result = table.rows().replaceValues(2, "ZZ", 100, true);

        assertTrue(result.isSuccess());
        testDataTableOnReplace(result.get());
    }

    private void testDataTableOnReplace(DataTable table) {
        assertTrue(table.rowCount() == 4);

        assertTrue(table.column("StrCol").valueAt(0) == "AA");
        assertTrue(table.column("StrCol").valueAt(1) == "BB");
        assertTrue(table.column("StrCol").valueAt(2) == "ZZ");
        assertTrue(table.column("StrCol").valueAt(3) == "DD");

        assertTrue((int)table.column("IntCol").valueAt(0) == 3);
        assertTrue((int)table.column("IntCol").valueAt(1) == 5);
        assertTrue((int)table.column("IntCol").valueAt(2) == 100);
        assertTrue((int)table.column("IntCol").valueAt(3) == 11);

        assertTrue((boolean)table.column("BoolCol").valueAt(0));
        assertTrue(!(boolean)table.column("BoolCol").valueAt(1));
        assertTrue((boolean)table.column("BoolCol").valueAt(2));
        assertTrue(!(boolean)table.column("BoolCol").valueAt(3));
    }

    @Test
    public void testDataReplaceRowWithInvalidIndex() {
        DataTable table = createDataTable();

        // Replace data at an invalid index.
        Object[] rowValues = { "ZZ", 100, true };
        Try<DataTable> result = table.rows().replace(200, rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause() instanceof IndexOutOfBoundsException);
    }

    @Test
    public void testDataReplaceRowWithInvalidType() {
        DataTable table = createDataTable();

        // Replace data which includes an invalid type.
        Object[] rowValues = { "ZZ", 100, 500 };
        Try<DataTable> result = table.rows().replace(2, rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("tryReplace failed. Item of invalid type passed."));
    }

    @Test
    public void testDataReplaceRowWithInvalidValueCount() {
        DataTable table = createDataTable();

        // Replace data where the number of values doesn't match the number of columns.
        Object[] rowValues = { "ZZ" };
        Try<DataTable> result = table.rows().replace(2, rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("Number of values does not match number of columns."));
    }

    // Test remove row methods...

    @Test
    public void testDataRemoveRow() {
        DataTable table = createDataTable();

        // Remove row at row index 2.
        Try<DataTable> result = table.rows().remove(2);

        assertTrue(result.isSuccess());

        DataTable newTable = result.get();
        assertTrue(newTable.rowCount() == 3);

        assertTrue(newTable.column("StrCol").valueAt(0) == "AA");
        assertTrue(newTable.column("StrCol").valueAt(1) == "BB");
        assertTrue(newTable.column("StrCol").valueAt(2) == "DD");

        assertTrue((int)newTable.column("IntCol").valueAt(0) == 3);
        assertTrue((int)newTable.column("IntCol").valueAt(1) == 5);
        assertTrue((int)newTable.column("IntCol").valueAt(2) == 11);

        assertTrue((boolean)newTable.column("BoolCol").valueAt(0));
        assertTrue(!(boolean)newTable.column("BoolCol").valueAt(1));
        assertTrue(!(boolean)newTable.column("BoolCol").valueAt(2));
    }

    @Test
    public void testDataRemoveInvalidRow() {
        DataTable table = createDataTable();

        // Remove row at row index 200 (invalid index).
        Try<DataTable> newTable = table.rows().remove(200);

        assertTrue(newTable.isFailure());
        assertTrue(newTable.getCause() instanceof IndexOutOfBoundsException);
    }

    private DataTable createDataTable() {
        return DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", "AA", "BB", "CC", "DD")
                .withColumn(Integer.class, "IntCol", 3, 5, 9, 11)
                .withColumn(Boolean.class, "BoolCol", true, false, true, false)
                .build()
                .get();
    }
}
