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
