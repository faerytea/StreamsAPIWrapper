package name.faerytea.kjs.wrapper.streams

import org.w3c.fetch.Response
import org.w3c.files.Blob
import org.w3c.files.File

/**
 * `Response.body`, but properly typed
 */
inline val Response.readableStream: ReadableStream
    get() = body.unsafeCast<ReadableStream>()

inline fun File.stream(): ReadableStream = asDynamic().stream().unsafeCast<ReadableStream>()

inline fun Blob.stream(): ReadableStream = asDynamic().stream().unsafeCast<ReadableStream>()

external interface ReadableStreamGetReaderOptions {
    val mode: String?
}

inline fun byob() = object : ReadableStreamGetReaderOptions {
    override val mode: String = "byob"
}

external interface ReadableStreamIteratorOptions {
    val preventCancel: Boolean
}

inline fun readableStreamIteratorOptions(preventCancel: Boolean = false) = object : ReadableStreamIteratorOptions {
    override val preventCancel: Boolean = false
}

external interface ReadableStreamReadResult {
    val done: Boolean
    val value: dynamic
}

external interface ReadableStreamBYOBReaderReadOptions {
    val min: ULong?
}

inline fun byobReadOptions(min: ULong?): ReadableStreamBYOBReaderReadOptions = object : ReadableStreamBYOBReaderReadOptions {
    override val min: ULong? = min
}
