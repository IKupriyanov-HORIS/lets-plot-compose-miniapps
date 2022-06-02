package jetbrains.datalore.vis.svgMapper.skia

object Base64 {
    private const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private const val padChar = '='

    fun encode(bytes: ByteArray): String {
        val result = StringBuilder()
        bytes.asSequence()
            .map { it.toInt() and 0b11111111 } // reset a sign bit in case of negative value
            .windowed(3, step = 3, partialWindows = true)
            .forEach { chunk ->
                val word =
                    (chunk.getOrElse(0) { 0 } shl 16) or
                    (chunk.getOrElse(1) { 0 } shl 8) or
                    (chunk.getOrElse(2) { 0 })

                val c4 = alphabet[word and 0b111111]
                val c3 = alphabet[word ushr 6 and 0b111111]
                val c2 = alphabet[word ushr 12 and 0b111111]
                val c1 = alphabet[word ushr 18 and 0b111111]

                when (chunk.size) {
                    3 -> result.append(c1).append(c2).append(c3).append(c4)
                    2 -> result.append(c1).append(c2).append(c3).append(padChar)
                    1 -> result.append(c1).append(c2).append(padChar).append(padChar)
                    else -> Unit
                }
        }

        return result.toString()
    }

    fun ByteArray.encodeToBase64(): String = encode(this)
}