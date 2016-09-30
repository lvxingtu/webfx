package naga.commons.util.async;

import naga.commons.util.function.Callable;
import naga.commons.util.function.Consumer;
import naga.commons.util.function.Function;

/**
 * @author Bruno Salmon
 */
public interface Future<T> extends AsyncResult<T> {
    /**
     * Create a future that hasn't completed yet
     *
     * @param <T>  the result type
     * @return  the future
     */
    static <T> Future<T> future() {
        return new FutureImpl<>();
    }

    /**
     * Create a succeeded future with a null result
     *
     * @param <T>  the result type
     * @return  the future
     */
    static <T> Future<T> succeededFuture() {
        return new FutureImpl<>((T)null);
    }

    /**
     * Created a succeeded future with the specified result.
     *
     * @param result  the result
     * @param <T>  the result type
     * @return  the future
     */
    static <T> Future<T> succeededFuture(T result) {
        return new FutureImpl<>(result);
    }

    /**
     * Create a failed future with the specified failure cause.
     *
     * @param t  the failure cause as a Throwable
     * @param <T>  the result type
     * @return  the future
     */
    static <T> Future<T> failedFuture(Throwable t) {
        return new FutureImpl<>(t);
    }

    /**
     * Create a failed future with the specified failure message.
     *
     * @param failureMessage  the failure message
     * @param <T>  the result type
     * @return  the future
     */
    static <T> Future<T> failedFuture(String failureMessage) {
        return new FutureImpl<>(failureMessage, true);
    }

    /**
     * Wrap a runnable into a future that complete immediately or fail if an exception is thrown.
     *
     * @param runnable  the runnable
     * @return  the future
     */
    static Future<Void> runAsync(Runnable runnable) {
        try {
            runnable.run();
            return succeededFuture();
        } catch (Throwable t) {
            // temporary tracing the exception while exception handling mechanism is not finished
            System.out.println("Exception raised in Future.runAsync(): " + t.getMessage());
            t.printStackTrace();
            return failedFuture(t);
        }
    }

    /**
     * Wrap a consumer into a future that complete immediately or fail if an exception is thrown.
     *
     * @param consumer  the consumer
     * @param arg  the argument to pass to the consumer
     * @param <T>  the argument type
     * @return  the future
     */
    static <T> Future<Void> consumeAsync(Consumer<T> consumer, T arg) {
        try {
            consumer.accept(arg);
            return succeededFuture();
        } catch (Throwable t) {
            // temporary tracing the exception while exception handling mechanism is not finished
            System.out.println("Exception raised in Future.runAsync(): " + t.getMessage());
            t.printStackTrace();
            return failedFuture(t);
        }
    }

    /**
     * The result of the operation. This will be null if the operation failed.
     */
    T result();

    /**
     * An exception describing failure. This will be null if the operation succeeded.
     */
    Throwable cause();

    /**
     * Did it succeed?
     */
    boolean succeeded();

    /**
     * Did it fail?
     */
    boolean failed();

    /**
     * Has it completed?
     */
    boolean isComplete();

    /**
     * Set a handler for the result. It will get called when it's complete
     */
    void setHandler(Handler<AsyncResult<T>> handler);

    void complete(T result);

    void complete();

    /**
     * Set the failure. Any handler will be called, if there is one
     */
    void fail(Throwable throwable);

    void fail(String failureMessage);

    /**
     * Compose this future with a provided {@code next} future.<p>
     *
     * When this (the one on which {@code compose} is called) future succeeds, the {@code handler} will be called with
     * the completed value, this handler should complete the next future.<p>
     *
     * If the {@code handler} throws an exception, the returned future will be failed with this exception.<p>
     *
     * When this future fails, the failure will be propagated to the {@code next} future and the {@code handler}
     * will not be called.
     *
     * @param handler the handler
     * @param next the next future
     * @return the next future, used for chaining
     */
    default <U> Future<U> compose(Handler<T> handler, Future<U> next) {
        setHandler(ar -> {
            if (ar.succeeded()) {
                try {
                    handler.handle(ar.result());
                } catch (Throwable err) {
                    if (next.isComplete())
                        throw err;
                    next.fail(err);
                }
            } else
                next.fail(ar.cause());
        });
        return next;
    }

    /**
     * Compose this future with a {@code mapper} function.<p>
     *
     * When this future (the one on which {@code compose} is called) succeeds, the {@code mapper} will be called with
     * the completed value and this mapper returns another future object. This returned future completion will complete
     * the future returned by this method call.<p>
     *
     * If the {@code mapper} throws an exception, the returned future will be failed with this exception.<p>
     *
     * When this future fails, the failure will be propagated to the returned future and the {@code mapper}
     * will not be called.
     *
     * @param mapper the mapper function
     * @return the composed future
     */
    default <U> Future<U> compose(Function<T, Future<U>> mapper) {
        Future<U> ret = Future.future();
        setHandler(ar -> {
            if (ar.succeeded()) {
                Future<U> apply;
                try {
                    apply = mapper.apply(ar.result());
                } catch (Throwable e) {
                    ret.fail(e);
                    return;
                }
                apply.setHandler(ret.completer());
            } else
                ret.fail(ar.cause());
        });
        return ret;
    }

    /**
     * Apply a {@code mapper} function on this future.<p>
     *
     * When this future succeeds, the {@code mapper} will be called with the completed value and this mapper
     * returns a value. This value will complete the future returned by this method call.<p>
     *
     * If the {@code mapper} throws an exception, the returned future will be failed with this exception.<p>
     *
     * When this future fails, the failure will be propagated to the returned future and the {@code mapper}
     * will not be called.
     *
     * @param mapper the mapper function
     * @return the mapped future
     */
    default <U> Future<U> map(Function<T, U> mapper) {
        Future<U> ret = Future.future();
        setHandler(ar -> {
            if (ar.succeeded()) {
                U mapped;
                try {
                    mapped = mapper.apply(ar.result());
                } catch (Throwable e) {
                    ret.fail(e);
                    return;
                }
                ret.complete(mapped);
            } else
                ret.fail(ar.cause());
        });
        return ret;
    }

    default <U> Future<U> map(Callable<U> mapper) {
        return map(arg -> mapper.call());
    }

    /**
     * Map the result of a future to a specific {@code value}.<p>
     *
     * When this future succeeds, this {@code value} will complete the future returned by this method call.<p>
     *
     * When this future fails, the failure will be propagated to the returned future.
     *
     * @param value the value that eventually completes the mapped future
     * @return the mapped future
     */
    default <V> Future<V> map(V value) {
        Future<V> ret = Future.future();
        setHandler(ar -> {
            if (ar.succeeded())
                ret.complete(value);
            else
                ret.fail(ar.cause());
        });
        return ret;
    }

    /**
     * @return an handler completing this future
     */
    default Handler<AsyncResult<T>> completer() {
        return ar -> {
            if (ar.succeeded())
                complete(ar.result());
            else
                fail(ar.cause());
        };
    }

}
