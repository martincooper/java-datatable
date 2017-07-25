import com.github.martincooper.datatable.DataTable;
import com.github.martincooper.datatable.DataTableBuilder;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Memory Profiling Tests for the Data Table.
 * Created by Martin Cooper on 19/07/2017.
 */
public class DataTableMemoryTests {

    private final Random rand = new Random();
    private static Integer ROW_COUNT = 1000000;

    // Check memory usage of the data in it's raw form (plain Java Arrays).
    @Ignore
    @Test
    public void testRawRowData() {
        String[] stringData = randomStringData(ROW_COUNT);
        Integer[] intData = randomIntegerData(ROW_COUNT);
        Double[] doubleData = randomDoubleData(ROW_COUNT);
        Boolean[] boolData = randomBooleanData(ROW_COUNT);

        long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("MB: " + (mem / 1024) / 1024);
    }

    // Check memory usage of the data stored in a Data Table.
    @Ignore
    @Test
    public void testDataTableWithRowData() {
        Try<DataTable> table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", randomStringData(ROW_COUNT))
                .withColumn(Integer.class, "IntCol", randomIntegerData(ROW_COUNT))
                .withColumn(Double.class, "DoubleCol", randomDoubleData(ROW_COUNT))
                .withColumn(Boolean.class, "BoolCol", randomBooleanData(ROW_COUNT))
                .build();

        long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("MB: " + (mem / 1024) / 1024);
    }

    private Integer[] randomIntegerData(int dataSize) {
        return generateRange(Integer.class, dataSize, rand::nextInt);
    }

    private Boolean[] randomBooleanData(int dataSize) {
        return generateRange(Boolean.class, dataSize, rand::nextBoolean);
    }

    private Double[] randomDoubleData(int dataSize) {
        return generateRange(Double.class, dataSize, rand::nextDouble);
    }

    private String[] randomStringData(int dataSize) {
        return generateRange(String.class, dataSize, () -> randomString(10));
    }

    private String randomString(int length) {
        return Stream
                .range(0, length)
                .map(i -> (char)(rand.nextInt((int)(Character.MAX_VALUE))))
                .toString();
    }

    private <T> T[] generateRange(Class<T> classType, int size, Supplier<T> supplier) {
        return Stream
                .range(0, size)
                .map(i -> supplier.get())
                .toJavaArray(classType);
    }
}
