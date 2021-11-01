package com.github.martincooper.datatable;

import io.vavr.control.Try;

/**
 * Contains helper methods for handling generic / casting functionality.
 * Created by Martin Cooper on 14/07/2017.
 */
class GenericExtensions {

    /**
     * Used to perform a type check and cast on an object instance.
     * Returns a Try success or failure depending on if it was valid cast.
     *
     * @param <T> The type to return.
     * @param type The type to cast to.
     * @param obj The object to cast.
     * @return Returns a Success<T> or a Failure.
     */
    @SuppressWarnings({"unchecked"})
    static <T> Try<T> tryCast(Class<T> type, Object obj) {
        return type.isInstance(obj) || obj == null
                ? Try.success((T)obj)
                : Try.failure(new DataTableException("Invalid cast."));
    }
}
