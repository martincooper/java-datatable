import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for the DataTable class.
 * Created by Martin Cooper on 15/07/2017.
 */
public class DataColumnCollectionTests {

    @Test
    public void testAddColumn() {
        DataTable oldTable = createDataTable();
        IDataColumn newCol = createDoubleColumn();

        Try<DataTable> newTable = oldTable.columns().add(newCol);

        assertTrue(newTable.isSuccess());
        assertTrue(oldTable.columns().count() == 3);
        assertTrue(newTable.get().columns().count() == 4);
        assertTrue(newTable.get().columns().get(3).name().equals("DoubleCol"));
    }

    @Test
    public void testRemoveColumn() {
        DataTable oldTable = createDataTable();

        Try<DataTable> newTable = oldTable.columns().remove("IntegerCol");

        assertTrue(newTable.isSuccess());
        assertTrue(oldTable.columns().count() == 3);
        assertTrue(newTable.get().columns().count() == 2);
        assertTrue(newTable.get().columns().get(1).name().equals("BooleanCol"));
    }

    @Test
    public void testInsertColumn() {
        DataTable oldTable = createDataTable();

        IDataColumn newCol = createDoubleColumn();
        Try<DataTable> newTable = oldTable.columns().insert(0, newCol);

        assertTrue(newTable.isSuccess());
        assertTrue(oldTable.columns().count() == 3);
        assertTrue(newTable.get().columns().count() == 4);

        // Check the column was inserted in the correct place.
        assertTrue(newTable.get().columns().get(0).name().equals("DoubleCol"));
        assertTrue(newTable.get().columns().get(1).name().equals("StringCol"));
        assertTrue(newTable.get().columns().get(2).name().equals("IntegerCol"));
    }

    @Test
    public void testReplaceColumn() {
        DataTable oldTable = createDataTable();

        IDataColumn newCol = createDoubleColumn();
        Try<DataTable> newTable = oldTable.columns().replace("IntegerCol", newCol);

        assertTrue(newTable.isSuccess());
        assertTrue(oldTable.columns().count() == 3);
        assertTrue(newTable.get().columns().count() == 3);

        // Check the correct column was replaced.
        assertTrue(newTable.get().columns().get(0).name().equals("StringCol"));
        assertTrue(newTable.get().columns().get(1).name().equals("DoubleCol"));
        assertTrue(newTable.get().columns().get(2).name().equals("BooleanCol"));
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

    private DataColumn<Double> createDoubleColumn() {
        List<Double> data = List.of(1.1, 2.2, 3.3);
        return new DataColumn<>(Double.class, "DoubleCol", data);
    }
}
