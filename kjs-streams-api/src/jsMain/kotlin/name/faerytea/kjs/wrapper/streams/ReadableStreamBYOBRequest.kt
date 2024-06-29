package name.faerytea.kjs.wrapper.streams

import org.khronos.webgl.ArrayBufferView

/**
 * The `ReadableStreamBYOBRequest` interface of the Streams API represents a "pull request"
 * for data from an underlying source that will made as a zero-copy transfer to a consumer
 * (bypassing the stream's internal queues).
 *
 * [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/ReadableStreamBYOBRequest)
 *
 * **Note**: Not supported in Safari (17.5)
 */
external interface ReadableStreamBYOBRequest {
    /**
     * Returns the current view. This is a view on a buffer
     * that will be transferred to the consumer when [respond] is called.
     */
    val view: ArrayBufferView?

    /**
     * Signals the associated readable byte stream that the specified number of bytes
     * were written into the current [view], which then causes the pending request from
     * the consumer to be resolved.
     *
     * Note that after this method is called the `view` is transferred and no longer modifiable.
     */
    fun respond(bytesWritten: Long)

    /**
     * Signals to the associated readable byte stream [view] passed as an argument
     * should be transferred to the consumer of the readable byte stream.
     * This new view must use the same buffer as the original view,
     * start at the same offset, and be the same length or shorter.
     *
     * Note that after this method is called the view is transferred and no longer modifiable.
     *
     * @param view An [TypedArray][ArrayBufferView] or a [DataView][org.khronos.webgl.DataView]
     * that the consumer of the associated readable byte stream should write to
     * instead of [this.view][ReadableStreamBYOBRequest.view].
     */
    fun respondWithNewView(view: ArrayBufferView)
}