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
