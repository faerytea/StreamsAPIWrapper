package name.faerytea.kjs.wrapper.streams

import kotlin.js.Promise
import kotlin.js.collections.JsArray

/**
 * The `ReadableStream` interface of the
 * [Streams API](https://developer.mozilla.org/en-US/docs/Web/API/Streams_API)
 * represents a readable stream of byte data.
 * The [Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API)
 * offers a concrete instance of a `ReadableStream` through the `body`
 * property of a [Response][org.w3c.fetch.Response] object.
 *
 * [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/ReadableStreamBYOBRequest)
 *
 * @see ReadableStreamDefaultSource
 * @see ReadableStreamByteSource
 */
open external class ReadableStream(
    underlyingSource: ReadableStreamSource<*> = definedExternally,
    queuingStrategy: QueuingStrategy = definedExternally,
) {
    /** Returns a boolean indicating whether or not the readable stream is locked to a reader. */
    val locked: Boolean

    /**
     * Returns a [Promise] that resolves when the stream is canceled.
     * Calling this method signals a loss of interest in the stream by a consumer.
     * The supplied `reason` argument will be given to the underlying source, which may or may not use it.
     *
     * @param reason A human-readable reason for the cancellation.
     *               This is passed to the underlying source, which may or may not use it.
     *
     * @return A [Promise], which fulfills with [undefined] value.
     */
    fun cancel(reason: Any? = definedExternally): Promise<Unit>

    /**
     * Creates a reader and locks the stream to it.
     * While the stream is locked, no other reader can be acquired until this one is released.
     *
     * Do not use it directly, since it is loose-typed
     *
     * @see getDefaultReader
     * @see getByobReader
     */
    fun getReader(options: ReadableStreamGetReaderOptions = definedExternally): ReadableStreamReader

    /**
     * Provides a chainable way of piping the current stream
     * through a transform stream or any other writable/readable pair.
     *
     * Piping a stream will generally lock it for the duration of the pipe, preventing other readers from locking it.
     *
     * @param transform A [TransformStream] (or an object with the structure `{writable, readable}`)
     *                  consisting of a readable stream and a writable stream working together to
     *                  transform some data from one form to another.
     *                  Data written to the `writable` stream can be read in some transformed state
     *                  by the readable stream.
     *                  For example, a TextDecoder, has bytes written to it and strings read from it,
     *                  while a video decoder has encoded bytes written to it and uncompressed video frames read from it.
     * @param streamPipeOptions The options that should be used when piping to the writable stream.
     * @return The [readable][ReadableWritablePair.readable] side of the `transform`
     */
    fun pipeThrough(
        transform: ReadableWritablePair,
        streamPipeOptions: StreamPipeOptions = definedExternally,
    ): ReadableStream

    /**
     * Pipes the current `ReadableStream` to a given [WritableStream] and returns a [Promise]
     * that fulfills when the piping process completes successfully, or rejects if any errors were encountered.
     *
     * @param destination A [WritableStream] that acts as the final destination for the `ReadableStream`.
     * @param streamPipeOptions The options that should be used when piping to the writable stream.
     * @return A [Promise] that resolves when the piping process has completed.
     */
    fun pipeTo(destination: WritableStream, streamPipeOptions: StreamPipeOptions = definedExternally): Promise<Unit>

    /**
     * The tee method [tees](https://streams.spec.whatwg.org/#tee-a-readable-stream)
     * this readable stream, returning a two-element array containing
     * the two resulting branches as new `ReadableStream` instances.
     * Each of those streams receives the same incoming data.
     *
     * @see teeTyped
     */
    @OptIn(ExperimentalJsCollectionsApi::class)
    fun tee(): JsArray<ReadableStream>
}

/** Typed version of [ReadableStream.getReader] for obtaining default reader */
inline fun ReadableStream.getDefaultReader() = getReader() as ReadableStreamDefaultReader
/** Typed version of [ReadableStream.getReader] for obtaining BYOB reader */
inline fun ReadableStream.getByobReader() = getReader(byob()) as ReadableStreamBYOBReader

/**
 * Version of [ReadableStream.tee] which returns a normal kotlin [Pair]
 */
@OptIn(ExperimentalJsCollectionsApi::class)
inline fun ReadableStream.teeTyped(): Pair<ReadableStream, ReadableStream> {
    val teed = tee().unsafeCast<Array<ReadableStream>>()
    return teed[0] to teed[1]
}
