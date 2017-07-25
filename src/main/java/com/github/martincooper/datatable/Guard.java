package com.github.martincooper.datatable;

import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.Objects;

/**
 * Guard class. Used to validate class arguments.
 * Created by Martin on 13/07/2017.
 */
public class Guard {

    /**
     * Asserts the value is not null. Throws an IllegalArgumentException if it is.
     *
     * @param argument The argument to check.
     * @param name The name of the argument.
     */
    public static void notNull(Object argument, String name) {
        if (argument == null)
            throw new IllegalArgumentException("Invalid value [NULL] for argument " + name);
    }

    /**
     * Asserts the argument, and none of it's iterable items, is null.
     * Throws an IllegalArgumentException if it is.
     *
     * @param argument The argument to check.
     * @param name The name of the argument.
     */
    public static <T> void itemsNotNull(Iterable<T> argument, String name) {
        notNull(argument, name);

        // Check none of the items in the collection is null.
        if (!Stream.ofAll(argument).find(Objects::isNull).isEmpty())
            throw new IllegalArgumentException("Invalid value [NULL] in collection for argument " + name);
    }

    /**
     * Asserts the argument, and none of it's items, is null.
     * Throws an IllegalArgumentException if it is.
     *
     * @param argument The argument to check.
     * @param name The name of the argument.
     */
    public static <T> void itemsNotNull(T[] argument, String name) {
        notNull(argument, name);
        itemsNotNull(Stream.of(argument), name);
    }

    /**
     * Asserts the argument is not null. Returns in a Try.
     *
     * @param argument The argument to check.
     * @param name The name of the argument.
     */
    public static <T> Try<T> tryNotNull(T argument, String name) {
        return argument == null
                ? Try.failure(new IllegalArgumentException("Invalid value [NULL] for argument " + name))
                : Try.success(argument);
    }
}
