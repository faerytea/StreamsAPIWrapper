package name.faerytea.kjs.wrapper.streams

/**
 * @see ReadableStreamDefaultController
 * @see ReadableByteStreamController
 */
sealed external interface ReadableStreamController {
    /** Closes the associated stream. */
    fun close()
    /** Enqueues a given chunk in the associated stream. */
    fun enqueue(chunk: dynamic = definedExternally)
    /** Causes any future interactions with the associated stream to error. */
    fun error(e: dynamic = definedExternally)
    /** Returns the desired size required to fill the stream's internal queue. */
    val desiredSize: Long?
}

/** [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/ReadableStreamDefaultController) */
external interface ReadableStreamDefaultController: ReadableStreamController

/**
 * The `ReadableByteStreamController` interface of the Streams API represents a controller
 * for a readable byte stream. It allows control of the state and internal queue of a
 * [ReadableStream] with an underlying byte source, and enables efficient zero-copy
 * transfer of data from the underlying source to a consumer when the stream's internal queue is empty.
 *
 * [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/ReadableByteStreamController)
 *
 * **Note**: Not supported in Safari (17.5)
 */
external interface ReadableByteStreamController: ReadableStreamController {
    /**
     * The current [BYOB pull request][ReadableStreamBYOBRequest], or null if there's no outstanding request.
     *
     * **Note**: Not supported in Safari (17.5)
     */
    val byobRequest: ReadableStreamBYOBRequest?
}
