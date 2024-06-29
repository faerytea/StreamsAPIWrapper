package name.faerytea.kjs.wrapper.streams

import kotlin.js.Promise

external interface Transformer {
    /**
     * Called when the [TransformStream] is constructed.
     * It is typically used to enqueue chunks using [TransformStreamDefaultController.enqueue].
     */
    fun start(controller: TransformStreamDefaultController)

    /**
     * Called when a chunk written to the writable side is ready to be transformed,
     * and performs the work of the transformation stream.
     * It can return a [Promise] to signal success or failure of the write operation.
     * If no [transform] method is supplied, the identity transform is used,
     * and the chunk will be enqueued with no changes.
     */
    fun transform(chunk: dynamic, controller: TransformStreamDefaultController): Any

    /**
     * Called after all chunks written to the writable side have been successfully transformed,
     * and the writable side is about to be closed.
     */
    fun flush(controller: TransformStreamDefaultController)
}

inline fun Transformer(
    crossinline transformer: (chunk: dynamic, controller: TransformStreamDefaultController) -> Promise<Unit>
) = object : Transformer {
    override fun start(controller: TransformStreamDefaultController) = Unit

    override fun transform(chunk: dynamic, controller: TransformStreamDefaultController) = transformer(chunk, controller)

    override fun flush(controller: TransformStreamDefaultController) = Unit
}