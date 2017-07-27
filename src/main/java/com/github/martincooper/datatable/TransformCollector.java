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
 * TransformCollector. Custom Collector.
 * Used to pass the contents of a collection to a
 * single method, returning a single value.
 *
 * @param <T> The collection type.
 * @param <U> The return type.
 */
public class TransformCollector<T, U> implements Collector<T, ArrayList<T>, U> {

    private final Function<Stream<T>, U> function;

    /**
     * TransformCollector constructor.
     *
     * @param function The function to run on the collected items.
     */
    public TransformCollector(Function<Stream<T>, U> function) {
        this.function = function;
    }

    /**
     * as method. Static helper to return a new instance of the Transform Collector class.
     *
     * @param function The function to run on the full collection.
     * @param <T> The collection type.
     * @param <U> The return type.
     * @return Returns a new instance of the collector.
     */
    public static <T, U> TransformCollector<T, U> transform(Function<Stream<T>, U> function) {
        return new TransformCollector<>(function);
    }

    /**
     * The supplier. Returns a new ArrayList which
     * builds up the final item list.
     *
     * @return Returns the ArrayList.
     */
    @Override
    public Supplier<ArrayList<T>> supplier() {
        return ArrayList::new;
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
     * to run the requested transform function on it to get the final results.
     *
     * @return Returns the result after passed through the transform function.
     */
    @Override
    public Function<ArrayList<T>, U> finisher() {
        return (items) -> function.apply(Stream.ofAll(items));
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
