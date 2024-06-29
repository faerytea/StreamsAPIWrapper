package name.faerytea.kjs.wrapper.streams

/**
 * The `TransformStream` interface of the Streams API represents a concrete implementation
 * of the _pipe chain transform stream_ concept.
 *
 * [Full doc on MDN](https://developer.mozilla.org/en-US/docs/Web/API/TransformStream)
 *
 * @param transformer An object representing the [Transformer].
 * If not supplied the resulting stream will be an identity transform stream
 * which forwards all chunks written to its writable side to its readable side,
 * without any changes.
 *
 * @see [ReadableStream.pipeThrough]
 */
open external class TransformStream(
    transformer: Transformer = definedExternally,
    writableStrategy: QueuingStrategy = definedExternally,
    readableStrategy: QueuingStrategy = definedExternally,
): ReadableWritablePair {
    override val readable: ReadableStream
    override val writable: WritableStream
}