package name.faerytea.kjs.wrapper.streams.compression

import name.faerytea.kjs.wrapper.streams.TransformStream

/**
 * @param format Any of [GZIP], [DEFLATE], [DEFLATE_RAW]
 */
external class CompressionStream(format: String): TransformStream