package com.github.martincooper.datatable;

import io.vavr.collection.Stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * LambdaCollect. Custom Collector.
 * Used to pass the contents of a collection to a
 * single method, returning a single value.
 *
 * @param <T> The collection type.
 * @param <U> The return type.
 */
public class LambdaCollect<T, U> implements Collector<T, ArrayList<T>, U> {

    private final Function<Stream<T>, U> function;

    /**
     * LambdaCollect constructor.
     *
     * @param function The function to run on the collected items.
     */
    public LambdaCollect(Function<Stream<T>, U> function) {
        this.function = function;
    }

    /**
     * as method. Static helper to return a new instance of the Lambda Collector class.
     *
     * @param function The function to run on the full collection.
     * @param <T> The collection type.
     * @param <U> The return type.
     * @return
     */
    public static <T, U> LambdaCollect<T, U> as(Function<Stream<T>, U> function) {
        return new LambdaCollect<>(function);
    }

    /**
     * The supplier. Returns a new ArrayList which
     * builds up the final item list.
     *
     * @return Returns the ArrayList.
     */
    @Override
    public Supplier<ArrayList<T>> supplier() {
        return new Supplier<ArrayList<T>>() {
            @Override
            public ArrayList<T> get() {
                return new ArrayList<T>();
            }
        } ;
    }

    /**
     * The accumulator. In this case, just append the item to the list.
     *
     * @return Return the ArrayList
     */
    @Override
    public BiConsumer<ArrayList<T>, T> accumulator() {
        return ArrayList::add;
    }

    // Not implemented : Parallel Stream.
    @Override
    public BinaryOperator<ArrayList<T>> combiner() {
        return (acc1, acc2) -> {
            throw new UnsupportedOperationException();
        };
    }

    /**
     * The finisher has all the items in the list. Now we just need
     * to run the requested lambda function on it to get the final results.
     *
     * @return Returns the result after passed through the lambda.
     */
    @Override
    public Function<ArrayList<T>, U> finisher() {
        return (acc) -> function.apply(Stream.ofAll(acc));
    }


    /**
     * Default set of characteristics.
     *
     * @return Returns the default set of characteristics.
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}