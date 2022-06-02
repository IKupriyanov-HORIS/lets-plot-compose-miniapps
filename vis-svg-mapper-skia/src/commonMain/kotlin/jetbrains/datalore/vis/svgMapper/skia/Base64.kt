package jetbrains.datalore.vis.svgMapper.skia

object Base64 {
    private const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private const val padChar = '='
    private val decoderMap = alphabet.mapIndexed { index, c -> c to index }.toMap()

    private fun Char.alphabetToByte(): Int {
        return decoderMap.getOrElse(this) { 0 }
    }

    fun encode(bytes: ByteArray): String {
        val result = StringBuilder()
        bytes.asSequence()
            .map { it.toInt() and 0b11111111 } // reset a sign bit in case of negative value
            .windowed(3, step = 3, partialWindows = true)
            .forEach { block ->
                val word =
                    (block.getOrElse(0) { 0 } shl 16) or
                    (block.getOrElse(1) { 0 } shl 8) or
                    (block.getOrElse(2) { 0 })

                val c1 = alphabet[word ushr 18 and 0b111111]
                val c2 = alphabet[word ushr 12 and 0b111111]
                val c3 = alphabet[word ushr 6 and 0b111111]
                val c4 = alphabet[word and 0b111111]

                when (block.size) {
                    3 -> result.append(c1, c2, c3, c4)
                    2 -> result.append(c1, c2, c3, padChar)
                    1 -> result.append(c1, c2, padChar, padChar)
                    else -> Unit
                }
        }

        return result.toString()
    }

    fun decode(data: String): ByteArray {
        require(data.length % 4 == 0) { "Invalid string length: ${data.length}" }

        val result = mutableListOf<Byte>()
        data.windowed(4, step = 4)
            .forEach() { block ->
                val word =
                    (block[0].alphabetToByte() shl 18) or
                    (block[1].alphabetToByte() shl 12) or
                    (block[2].alphabetToByte() shl 6) or
                    (block[3].alphabetToByte())

                val b1 = (word ushr 16).toByte()
                val b2 = (word ushr 8).toByte()
                val b3 = word.toByte()

                result.add(b1)
                if (block[2] != padChar) result.add(b2)
                if (block[3] != padChar) result.add(b3)
            }

        return result.toByteArray()
    }

    fun ByteArray.encodeToBase64(): String = encode(this)
    fun String.decodeFromBase64(): ByteArray = decode(this)
}
