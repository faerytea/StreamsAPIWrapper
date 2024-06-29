package name.faerytea.kjs.wrapper.streams

/**
 * The `WritableStreamDefaultController` interface of the Streams API represents
 * a controller allowing control of a [WritableStream]'s state.
 * When constructing a `WritableStream`, the underlying sink is given
 * a corresponding `WritableStreamDefaultController` instance to manipulate.
 */
external interface WritableStreamDefaultController {
    /**
     * Returns the `AbortSignal` associated with the controller.
     */
    val signal: dynamic

    /**
     * Causes any future interactions with the associated stream to error.
     */
    fun error(e: String = definedExternally)
}