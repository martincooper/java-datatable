import io.vavr.collection.List;
import io.vavr.collection.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit Tests for the DataColumn class.
 * Created by Martin on 13/07/2017.
 */
public class DataColumnTests {

    @Test
    public void testEmptyDataColumnCreation() {
        DataColumn<String> column = new DataColumn<>(String.class, "StringCol");

        assertEquals(column.getName(), "StringCol");
        assertEquals(column.getType(), String.class);
    }

    @Test
    public void testArrayDataColumnCreation() {
        String[] data = new String[] { "AA", "BB", "CC" };
        DataColumn<String> column = new DataColumn<>(String.class, "StringCol", data);

        assertEquals(column.getData().length(), 3);
        assertEquals(column.getData().get(1), "BB");
    }

    @Test
    public void testIterableDataColumnCreation() {
        List<String> data = List.of("AA", "BB", "CC");
        DataColumn<String> column = new DataColumn<>(String.class, "StringCol", data);

        assertEquals(column.getData().length(), 3);
        assertEquals(column.getData().get(1), "BB");
    }

    @Test
    public void testVectorDataColumnCreation() {
        Vector<Integer> data = Vector.of(5, 7, 9);
        DataColumn<Integer> column = new DataColumn<>(Integer.class, "IntegerCol", data);

        assertEquals(column.getData().length(), 3);
        assertTrue(column.getData().get(1) == 7);
    }
}
