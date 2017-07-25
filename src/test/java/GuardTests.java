import com.github.martincooper.datatable.Guard;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit Tests for the Guard class.
 * Created by Martin Cooper on 13/07/2017.
 */
public class GuardTests {

    @Test
    public void testSingleValidArgument() {
        try{
            Guard.notNull("Test", "ArgumentOne");
        }
        catch(Exception e){
            fail("Should not have thrown any exception");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSingleInvalidArgument() {
            Guard.notNull(null, "ArgumentOne");
    }

    @Test
    public void testIterableValidArgument() {
        try{
            List<String> myList = List.of("a", "b", "c");
            Guard.itemsNotNull(myList, "ArgumentOne");
        }
        catch(Exception e){
            fail("Should not have thrown any exception");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIterableInvalidArgument() {
        List<String> myList = List.of("a", null, "c");
        Guard.itemsNotNull(myList, "ArgumentOne");
    }

    @Test
    public void testArrayValidArgument() {
        try{
            String myArray[] = { "one", "two", "three" };
            Guard.itemsNotNull(myArray, "ArgumentOne");
        }
        catch(Exception e){
            fail("Should not have thrown any exception");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayInvalidArgument() {
        String myArray[] = { "one", null, "three" };
        Guard.itemsNotNull(myArray, "ArgumentOne");
    }

    @Test
    public void testTryNotNullValidArgument() {
        Try<Integer> result = Guard.tryNotNull(12345, "MyArgName");

        assertTrue(result.isSuccess());
        assertTrue(result.get() == 12345);
    }

    @Test
    public void testTryNotNullInvalidArgument() {
        Try<Integer> result = Guard.tryNotNull(null, "MyArgName");

        assertTrue(result.isFailure());
        assertTrue(result.getCause().getMessage().equals("Invalid value [NULL] for argument MyArgName"));
    }
}
