import com.github.martincooper.datatable.DataTable;
import com.github.martincooper.datatable.DataTableBuilder;
import com.github.martincooper.datatable.DataView;
import com.github.martincooper.datatable.sorting.SortItem;
import com.github.martincooper.datatable.sorting.SortOrder;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Sorting Profiling Tests for the Data Table.
 * Created by Martin Cooper on 19/07/2017.
 */
public class DataTableSortingTests {

    private final Random rand = new Random();
    private static Integer ROW_COUNT = 1000000;

    // Check performance of performing a multi sort on a Data Table..
    @Ignore
    @Test
    public void testDataTableSortingPerformance() {
        DataTable table = DataTableBuilder
                .create("NewTable")
                .withColumn(String.class, "StrCol", randomStringData(ROW_COUNT))
                .withColumn(Integer.class, "IntCol", randomIntegerData(ROW_COUNT))
                .withColumn(Double.class, "DoubleCol", randomDoubleData(ROW_COUNT))
                .withColumn(Boolean.class, "BoolCol", randomBooleanData(ROW_COUNT))
                .build().get();

        SortItem sortOne = new SortItem("StrCol", SortOrder.Ascending);
        SortItem sortTwo = new SortItem("IntCol", SortOrder.Descending);
        SortItem sortThree = new SortItem("DoubleCol", SortOrder.Ascending);
        SortItem sortFour = new SortItem("BoolCol", SortOrder.Descending);

        long startTime = System.nanoTime();

        Try<DataView> view = table.quickSort(Stream.of(sortOne, sortTwo, sortThree, sortFour));

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        System.out.println("Sorting took " + duration + " milliseconds");
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
