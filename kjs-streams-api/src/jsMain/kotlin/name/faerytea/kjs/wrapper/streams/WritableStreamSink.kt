package name.faerytea.kjs.wrapper.streams

import kotlin.js.Promise

external interface WritableStreamSink {
    /**
     * This is a method, called immediately when the object is constructed.
     * The contents of this method are defined by the developer,
     * and should aim to get access to the underlying sink.
     * If this process is to be done asynchronously,
     * it can return a promise to signal success or failure.
     *
     * @return A [Promise] or [Unit]
     */
    fun start(controller: WritableStreamDefaultController): Any

    /**
     * This method will be called when a new chunk of data (specified in the [chunk] parameter)
     * is ready to be written to the underlying sink.
     * It can return a promise to signal success or failure of the write operation.
     * This method will be called only after previous writes have succeeded,
     * and never after the stream is [closed][WritableStream.close] or [aborted][WritableStream.abort].
     *
     * @return A [Promise] or [Unit]
     */
    fun write(chunk: dynamic, controller: WritableStreamDefaultController): Any

    /**
     * This method will be called if the app signals that it has finished writing chunks to the stream.
     * The contents should do whatever is necessary to finalize writes to the underlying sink,
     * and release access to it.
     * If this process is asynchronous, it can return a promise to signal success or failure.
     * This method will be called only after all queued-up writes have succeeded.
     *
     * @return A [Promise] or [Unit]
     */
    fun close(controller: WritableStreamDefaultController): Any

    /**
     * This method will be called if the app signals that it wishes to abruptly close
     * the stream and put it in an errored state.
     * It can clean up any held resources, much like [close], but `abort()`
     * will be called even if writes are queued up — those chunks will be thrown away.
     * If this process is asynchronous, it can return a promise to signal success or failure.
     * The reason parameter contains a string describing why the stream was aborted.
     *
     * @return A [Promise] or [Unit]
     */
    fun abort(reason: String): Any
}

inline fun WritableStreamSink(
    crossinline write: (chunk: dynamic, controller: WritableStreamDefaultController) -> Unit
) = object : WritableStreamSink {
    override fun start(controller: WritableStreamDefaultController) = Unit

    override fun write(chunk: dynamic, controller: WritableStreamDefaultController) = write(chunk, controller)

    override fun close(controller: WritableStreamDefaultController) = Unit

    override fun abort(reason: String) = Unit
}