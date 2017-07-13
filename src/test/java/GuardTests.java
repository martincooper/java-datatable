import io.vavr.collection.List;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Unit Tests for the Guard class.
 * Created by Martin on 13/07/2017.
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
}
