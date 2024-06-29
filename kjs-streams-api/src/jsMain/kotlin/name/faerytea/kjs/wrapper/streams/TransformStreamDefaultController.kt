package name.faerytea.kjs.wrapper.streams

/**
 * The `TransformStreamDefaultController` interface of the Streams API
 * provides methods to manipulate the associated [ReadableStream] and [WritableStream].
 */
external interface TransformStreamDefaultController {
    /** Returns the desired size to fill the readable side of the stream's internal queue. */
    val desiredSize: Long?
    /** Enqueues a chunk (single piece of data) in the readable side of the stream. */
    fun enqueue(chunk: dynamic = definedExternally)
    /** Errors both the readable and writable side of the transform stream. */
    fun error(reason: dynamic = definedExternally)
    /** Closes the readable side and errors the writable side of the stream. */
    fun terminate()
}