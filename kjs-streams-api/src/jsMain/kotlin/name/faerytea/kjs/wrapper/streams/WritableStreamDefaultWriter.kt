package name.faerytea.kjs.wrapper.streams

import kotlin.js.Promise

/**
 * The `WritableStreamDefaultWriter` interface of the Streams API is the object
 * returned by [WritableStream.getWriter] and once created locks the writer to
 * the `WritableStream` ensuring that no other streams can write to the underlying sink.
 */
open external class WritableStreamDefaultWriter(stream: WritableStream) {
    /**
     * Allows you to write code that responds to an end to the streaming process.
     * Returns a promise that fulfills if the stream becomes closed,
     * or rejects if the stream errors or the writer's lock is released.
     */
    val closed: Promise<Unit>

    /** The desired size required to fill the stream's internal queue. */
    val desiredSize: Long?

    /**
     * A [Promise] that resolves when the desired size of the stream's internal queue
     * transitions from non-positive to positive, signaling that it is no longer applying backpressure.
     */
    val ready: Promise<Unit>

    /**
     * Aborts the stream, signaling that the producer can no longer successfully write to the stream
     * and it is to be immediately moved to an error state, with any queued writes discarded.
     *
     * @return A [Promise], which fulfills to undefined when the stream is aborted,
     * or rejects with an error if the writer was inactive or the receiver stream is invalid.
     */
    fun abort(reason: String = definedExternally): Promise<Unit>

    /** Closes the associated writable stream. */
    fun close(): Promise<Unit>

    /**
     * Releases the writer's lock on the corresponding stream.
     * After the lock is released, the writer is no longer active.
     * If the associated stream is errored when the lock is released,
     * the writer will appear errored in the same way from now on;
     * otherwise, the writer will appear closed.
     */
    fun releaseLock()

    /**
     * Writes a passed chunk of data to a [WritableStream] and its underlying sink,
     * then returns a [Promise] that resolves to indicate
     * the success or failure of the write operation.
     */
    fun write(chunk: dynamic = definedExternally): Promise<Unit>
}