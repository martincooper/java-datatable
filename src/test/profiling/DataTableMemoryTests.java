import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Memory Tests for the Data Table.
 * Created by Martin Cooper on 19/07/2017.
 */
public class DataTableMemoryTests {

    private final Random rand = new Random();

    @Test
    public void testBuilderDataTableCreationFromIterable() {
        Try<DataTable> table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", List.of("AA", "BB", "CC", "DD"))
                .withColumn(Integer.class, "IntCol", List.of(3, 5, 9, 11))
                .withColumn(Boolean.class, "BoolCol", List.of(true, false, true, false))
                .build();

        assertTrue(table.isSuccess());
        assertTrue(table.get().columns().count() == 3);
        assertTrue(table.get().rowCount() == 4);
    }

    private Integer[] createRandomIntegerData(Long dataSize) {
        return Stream
                .range(0, dataSize)
                .map(i -> rand.nextInt())
                .toJavaArray(Integer.class);
    }

    private Boolean[] createRandomBooleanData(Long dataSize) {
        return Stream
                .range(0, dataSize)
                .map(i -> rand.nextBoolean())
                .toJavaArray(Boolean.class);
    }

    private Double[] createRandomDoubleData(Long dataSize) {
        return Stream
                .range(0, dataSize)
                .map(i -> rand.nextDouble())
                .toJavaArray(Double.class);
    }

    private String[] createRandomStringData(Long dataSize) {
        return Stream
                .range(0, dataSize)
                .map(i -> createRandomString(10))
                .toJavaArray(String.class);
    }

    private String createRandomString(Integer length) {
        return Stream
                .range(0, length)
                .map(i -> (char)(rand.nextInt((int)(Character.MAX_VALUE))))
                .toString();
    }
}
