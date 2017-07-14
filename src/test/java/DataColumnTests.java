import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Tests for the DataColumn class.
 * Created by Martin on 13/07/2017.
 */
public class DataColumnTests {

    @Test
    public void testDataColumnCreation() {

        DataColumn<String> column = new DataColumn<>(String.class, "StringCol");

        assertEquals(column.getName(), "StringCol");
        assertEquals(column.getType(), String.class);

    }

}
