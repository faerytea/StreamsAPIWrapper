package name.faerytea.kjs.wrapper.streams

import org.w3c.dom.events.EventTarget

/**
 * @see streamPipeOptions
 */
external interface StreamPipeOptions {
    /**
     * If this is set to `true`, closing the source [ReadableStream]
     * will no longer cause the destination [WritableStream] to be closed.
     */
    val preventClose: Boolean?
        get() = definedExternally

    /**
     * If this is set to `true`, errors in the source [ReadableStream]
     * will no longer abort the destination [WritableStream].
     */
    val preventAbort: Boolean?
        get() = definedExternally

    /**
     * If this is set to `true`, errors in the destination [WritableStream]
     * will no longer cancel the source [ReadableStream].
     */
    val preventCancel: Boolean?
        get() = definedExternally

    /**
     * If set to an `AbortSignal` object, ongoing pipe operations
     * can then be aborted via the corresponding `AbortController`.
     */
    var signal: EventTarget?
}

/**
 * Creates [StreamPipeOptions]
 *
 * @param preventClose see [StreamPipeOptions.preventClose]
 * @param preventAbort see [StreamPipeOptions.preventAbort]
 * @param preventCancel see [StreamPipeOptions.preventCancel]
 * @param signal see [StreamPipeOptions.signal]
 */
inline fun streamPipeOptions(
    preventClose: Boolean? = undefined,
    preventAbort: Boolean? = undefined,
    preventCancel: Boolean? = undefined,
    signal: EventTarget? = undefined,
) = object : StreamPipeOptions {
    override val preventClose: Boolean? = preventClose
    override val preventAbort: Boolean? = preventAbort
    override val preventCancel: Boolean? = preventCancel
    override var signal: EventTarget? = signal
}
