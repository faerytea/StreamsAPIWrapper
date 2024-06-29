package name.faerytea.kjs.wrapper.streams

import kotlin.js.Promise

/**
 * @see ReadableStreamDefaultSource
 * @see ReadableStreamByteSource
 */
external interface ReadableStreamSource<T: ReadableStreamController> {
    /**
     * This is a method, called immediately when the object is constructed.
     * The contents of this method are defined by the developer,
     * and should aim to get access to the stream source,
     * and do anything else required to set up the stream functionality.
     *
     * If this process is to be done asynchronously,
     * it can return a [Promise] to signal success or failure.
     *
     * @return A [Promise] or [Unit]
     */
    fun start(controller: T): Any

    /**
     * This method will be called repeatedly when the stream's internal queue of chunks
     * is not full, up until it reaches its high water mark.
     * This can be used by the developer to control the stream as more chunks are fetched.
     *
     * If `pull()` returns a [Promise], then it won't be called again until that promise fulfills;
     * if the promise [rejects][Promise.reject], the stream will become errored.
     *
     * This function will not be called until [start] successfully completes.
     * Additionally, it will only be called repeatedly if it enqueues at least one chunk
     * or fulfills a BYOB request; a no-op `pull()` implementation will not be continually called.
     *
     * @return A [Promise] or [Unit]
     */
    fun pull(controller: T): Any

    /**
     * This method will be called if the app signals that the stream is to be cancelled
     * (e.g. if [ReadableStream.cancel] is called).
     * The contents should do whatever is necessary to release access to the stream source.
     *
     * If this process is asynchronous, it can return a promise to signal success or failure.
     *
     * @param reason parameter contains a string describing why the stream was cancelled.
     * @returns A [Promise] or [Unit]
     */
    fun cancel(reason: String): Any

    /**
     * Controls what type of readable stream is being dealt with.
     */
    val type: String?

    /**
     * For [byte streams][ReadableStreamByteSource], the developer can set the
     * `autoAllocateChunkSize` with a positive integer value to turn on the stream's auto-allocation feature.
     * With this is set, the stream implementation will automatically allocate a view buffer
     * of the specified size in [ReadableByteStreamController.byobRequest] when required.
     *
     * This must be set to enable zero-copy transfers to be used with a default [ReadableStreamDefaultReader].
     * If not set, a default reader will still stream data, but [ReadableByteStreamController.byobRequest]
     * will always be `null` and transfers to the consumer must be via the stream's internal queues.
     */
    val autoAllocateChunkSize: ULong?
}

abstract class ReadableStreamDefaultSource: ReadableStreamSource<ReadableStreamDefaultController> {
    override fun start(controller: ReadableStreamDefaultController): Any = Unit
    override fun pull(controller: ReadableStreamDefaultController): Any = Unit
    override fun cancel(reason: String): Any = Unit
    final override val type: String? = undefined
    override val autoAllocateChunkSize: ULong? = undefined
}

abstract class ReadableStreamByteSource: ReadableStreamSource<ReadableByteStreamController> {
    override fun start(controller: ReadableByteStreamController): Any = Unit
    override fun pull(controller: ReadableByteStreamController): Any = Unit
    override fun cancel(reason: String): Any = Unit
    final override val type: String = "bytes"
}

inline fun ReadableStreamSource(
    crossinline pull: (controller: ReadableStreamDefaultController) -> Unit
) = object : ReadableStreamDefaultSource() {
    override fun pull(controller: ReadableStreamDefaultController): Unit = pull(controller)
}

inline fun ReadableStreamSource(
    crossinline pull: (controller: ReadableStreamDefaultController) -> Promise<Unit>
) = object : ReadableStreamDefaultSource() {
    override fun pull(controller: ReadableStreamDefaultController): Promise<Unit> = pull(controller)
}