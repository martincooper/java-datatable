import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;


/**
 * Memory Tests for the Data Table.
 * Created by Martin Cooper on 19/07/2017.
 */
public class DataTableMemoryTests {

    private final Random rand = new Random();
    private static Integer ROW_COUNT = 1000000;

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
        return Stream
                .range(0, dataSize)
                .map(i -> rand.nextInt())
                .toJavaArray(Integer.class);
    }

    private Boolean[] randomBooleanData(int dataSize) {
        return Stream
                .range(0, dataSize)
                .map(i -> rand.nextBoolean())
                .toJavaArray(Boolean.class);
    }

    private Double[] randomDoubleData(int dataSize) {
        return Stream
                .range(0, dataSize)
                .map(i -> rand.nextDouble())
                .toJavaArray(Double.class);
    }

    private String[] randomStringData(int dataSize) {
        return Stream
                .range(0, dataSize)
                .map(i -> randomString(10))
                .toJavaArray(String.class);
    }

    private String randomString(int length) {
        return Stream
                .range(0, length)
                .map(i -> (char)(rand.nextInt((int)(Character.MAX_VALUE))))
                .toString();
    }
}
