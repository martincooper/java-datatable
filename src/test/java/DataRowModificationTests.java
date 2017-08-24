import com.github.martincooper.datatable.*;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit Tests for the DataRowCollectionModifiable class.
 * Created by Martin Cooper on 23/08/2017.
 */
public class DataRowModificationTests {

    @Test
    public void testDataAddRow() {
        DataTable table = createDataTable();

        Object[] rowValues = { "ZZ", 100, true };
        Try<DataTable> result = table.rows().add(rowValues);

        assertTrue(result.isSuccess());

        DataTable newTable = result.get();
        assertTrue(newTable.rowCount() == 5);

        assertTrue(newTable.column("StrCol").valueAt(0) == "AA");
        assertTrue(newTable.column("StrCol").valueAt(1) == "BB");
        assertTrue(newTable.column("StrCol").valueAt(2) == "CC");
        assertTrue(newTable.column("StrCol").valueAt(3) == "DD");
        assertTrue(newTable.column("StrCol").valueAt(4) == "ZZ");

        assertTrue((int)newTable.column("IntCol").valueAt(0) == 3);
        assertTrue((int)newTable.column("IntCol").valueAt(1) == 5);
        assertTrue((int)newTable.column("IntCol").valueAt(2) == 9);
        assertTrue((int)newTable.column("IntCol").valueAt(3) == 11);
        assertTrue((int)newTable.column("IntCol").valueAt(4) == 100);

        assertTrue((boolean)newTable.column("BoolCol").valueAt(0));
        assertTrue(!(boolean)newTable.column("BoolCol").valueAt(1));
        assertTrue((boolean)newTable.column("BoolCol").valueAt(2));
        assertTrue(!(boolean)newTable.column("BoolCol").valueAt(3));
        assertTrue((boolean)newTable.column("BoolCol").valueAt(4));
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
    public void testDataAddRowWithInvalidValueCount() {
        DataTable table = createDataTable();

        // Add data where the number of values doesn't match the number of columns.
        Object[] rowValues = { "ZZ" };
        Try<DataTable> result = table.rows().add(rowValues);

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("Number of values does not match number of columns."));
    }

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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDataRemoveInvalidRow() {
        DataTable table = createDataTable();

        // Remove row at row index 200 (invalid index).
        Try<DataTable> newTable = table.rows().remove(200);

        assertTrue(newTable.isFailure());

        // Should throw IndexOutOfBoundsException.
        newTable.get();
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
