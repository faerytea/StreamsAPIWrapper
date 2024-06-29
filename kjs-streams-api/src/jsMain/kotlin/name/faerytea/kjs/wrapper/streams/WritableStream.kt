package name.faerytea.kjs.wrapper.streams

import kotlin.js.Promise

/**
 * The `WritableStream` interface of the Streams API provides a standard abstraction
 * for writing streaming data to a destination, known as a sink.
 * This object comes with built-in backpressure and queuing.
 *
 * [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/ReadableStreamBYOBRequest)
 */
open external class WritableStream(
    underlyingSink: WritableStreamSink = definedExternally,
    queuingStrategy: QueuingStrategy = definedExternally,
) {
    /** A boolean indicating whether the `WritableStream` is locked to a writer. */
    val locked: Boolean

    /**
     * Aborts the stream, signaling that the producer can no longer successfully write
     * to the stream and it is to be immediately moved to an error state,
     * with any queued writes discarded.
     */
    fun abort(reason: String): Promise<Unit>

    /** Closes the stream. */
    fun close(): Promise<Unit>

    /**
     * Returns a new instance of [WritableStreamDefaultWriter]
     * and locks the stream to that instance.
     * While the stream is [locked], no other writer can be acquired
     * until this one is released.
     */
    fun getWriter(): WritableStreamDefaultWriter
}