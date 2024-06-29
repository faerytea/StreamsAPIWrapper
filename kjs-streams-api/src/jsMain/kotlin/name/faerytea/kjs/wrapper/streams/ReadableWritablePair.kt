package name.faerytea.kjs.wrapper.streams

/** @see TransformStream */
external interface ReadableWritablePair {
    val readable: ReadableStream
    val writable: WritableStream
}

inline fun mkRWPair(
    readable: ReadableStream,
    writable: WritableStream,
) = object : ReadableWritablePair {
    override val readable: ReadableStream = readable
    override val writable: WritableStream = writable
}

inline fun Pair<ReadableStream, WritableStream>.toRWPair() = mkRWPair(first, second)