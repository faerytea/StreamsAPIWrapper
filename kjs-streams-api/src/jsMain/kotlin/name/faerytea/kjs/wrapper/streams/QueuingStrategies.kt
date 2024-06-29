package name.faerytea.kjs.wrapper.streams

typealias QueuingStrategySize = (chunk: dynamic) -> Double

/**
 * @see CountQueuingStrategy
 * @see ByteLengthQueuingStrategy
 */
external interface QueuingStrategy {
    /**
     * A non-negative number indicating the
     * [high water mark](https://streams.spec.whatwg.org/#high-water-mark)
     * of the stream using this queuing strategy.
     */
    val highWaterMark: Double?

    /**
     * A function that computes and returns the finite non-negative size of the given chunk value.
     *
     * The result is used to determine backpressure, manifesting via the appropriate desiredSize property:
     * either defaultController.desiredSize, byteController.desiredSize, or writer.desiredSize,
     * depending on where the queuing strategy is being used.
     * For readable streams, it also governs when the underlying source's pull() method is called.
     *
     * This function has to be idempotent and not cause side effects; very strange results can occur otherwise.
     *
     * For readable byte streams, this function is not used, as chunks are always measured in bytes.
     */
    val size: QueuingStrategySize?
}

/** @see qsInit */
external interface QueuingStrategyInit {
    /** @see QueuingStrategy.highWaterMark */
    val highWaterMark: Double
}

/**
 * Creates initializer object for [QueuingStrategy]ies
 *
 * @param highWaterMark see [QueuingStrategy.highWaterMark]
 */
inline fun qsInit(highWaterMark: Double) = object : QueuingStrategyInit {
    override val highWaterMark = highWaterMark
}

/**
 * A common [queuing strategy][QueuingStrategy] when dealing with bytes is to wait
 * until the accumulated byteLength properties of the incoming chunks reaches a specified high-water mark.
 * As such, this is provided as a built-in queuing strategy that can be used when constructing streams.
 *
 * @see qsInit
 */
external class ByteLengthQueuingStrategy(
    queuingStrategy: QueuingStrategyInit
): QueuingStrategy {
    override val highWaterMark: Double
    override val size: QueuingStrategySize
}

/**
 * A common [queuing strategy][QueuingStrategy] when dealing with streams of generic objects
 * is to simply count the number of chunks that have been accumulated so far,
 * waiting until this number reaches a specified high-water mark.
 * As such, this strategy is also provided out of the box.
 *
 * @see qsInit
 */
external class CountQueuingStrategy(
    queuingStrategy: QueuingStrategyInit
): QueuingStrategy {
    override val highWaterMark: Double
    override val size: QueuingStrategySize
}