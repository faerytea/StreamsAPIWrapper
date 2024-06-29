package name.faerytea.kjs.wrapper.streams

import org.khronos.webgl.ArrayBufferView
import kotlin.js.Promise

/**
 * @see ReadableStreamDefaultReader
 * @see ReadableStreamBYOBReader
 */
sealed external interface ReadableStreamReader {
    /**
     * Returns a [Promise] that fulfills when the stream closes,
     * or rejects if the stream throws an error or the reader's lock is released.
     * This property enables you to write code that responds to an end to the streaming process.
     */
    val closed: Promise<Unit>

    /**
     * Returns a [Promise] that resolves when the stream is canceled.
     * Calling this method signals a loss of interest in the stream by a consumer.
     * The supplied `reason` argument will be given to the underlying source, which may or may not use it.
     *
     * @param reason A human-readable reason for the cancellation. This value may or may not be used.
     */
    fun cancel(reason: String): Promise<Unit>

    /** Releases the reader's lock on the stream. */
    fun releaseLock()
}

/**
 * The `ReadableStreamDefaultReader` interface of the Streams API represents
 * a default reader that can be used to read stream data supplied from a network
 * (such as a fetch request).
 *
 * [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/ReadableStreamDefaultReader)
 *
 * @see ReadableStream.getDefaultReader
 */
external class ReadableStreamDefaultReader(stream: ReadableStream): ReadableStreamReader {
    override val closed: Promise<Unit> = definedExternally

    override fun cancel(reason: String): Promise<Unit> = definedExternally

    override fun releaseLock() = definedExternally

    /**
     * @return A [Promise] providing access to the next chunk in the stream's internal queue.
     */
    fun read(): Promise<ReadableStreamReadResult>
}

/**
 * The `ReadableStreamBYOBReader` interface of the Streams API defines a reader
 * for a [ReadableStream] that supports zero-copy reading from an underlying byte source.
 * It is used for efficient copying from underlying sources where the data is delivered
 * as an "anonymous" sequence of bytes, such as files.
 *
 * [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/ReadableStreamBYOBReader)
 *
 * @see ReadableStream.getByobReader
 */
external class ReadableStreamBYOBReader(stream: ReadableStream): ReadableStreamReader {
    override val closed: Promise<Unit> = definedExternally

    override fun cancel(reason: String): Promise<Unit> = definedExternally

    override fun releaseLock() = definedExternally

    /**
     * Passes a view into which data must be written, and returns a [Promise] that resolves
     * with the next chunk in the stream or rejects with an indication
     * that the stream is closed or has errored.
     *
     * @param view The view that data is to be read into.
     * @return A [Promise] which fulfills with an object that has properties `value` and `done`
     * when data comes available, or if the stream is cancelled.
     * If the stream is errored, the promise will be rejected with the relevant error object.
     */
    fun read(
        view: ArrayBufferView,
        options: ReadableStreamBYOBReaderReadOptions = definedExternally
    ): Promise<ReadableStreamReadResult>
}
