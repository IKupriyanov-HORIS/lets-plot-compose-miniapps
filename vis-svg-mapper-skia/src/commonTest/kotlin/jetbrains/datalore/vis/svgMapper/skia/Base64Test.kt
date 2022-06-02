package jetbrains.datalore.vis.svgMapper.skia
import kotlin.test.Test
import kotlin.test.assertEquals

class Base64Test {

    @Test
    fun emptyBuffer() {
        assertEquals("", Base64.encode("".toByteArray()))
    }

    @Test
    fun f() {
        assertEquals("Zg==", Base64.encode("f".toByteArray()))
    }

    @Test
    fun fo() {
        assertEquals("Zm8=", Base64.encode("fo".toByteArray()))
    }

    @Test
    fun foo() {
        assertEquals("Zm9v", Base64.encode("foo".toByteArray()))
    }

    @Test
    fun foob() {
        assertEquals("Zm9vYg==", Base64.encode("foob".toByteArray()))
    }

    @Test
    fun fooba() {
        assertEquals("Zm9vYmE=", Base64.encode("fooba".toByteArray()))
    }

    @Test
    fun foobar() {
        assertEquals("Zm9vYmFy", Base64.encode("foobar".toByteArray()))
    }

    @Test
    fun bunch() {
        assertEquals("AA==", Base64.encode(byteArray(0)))
        assertEquals("AAA=", Base64.encode(byteArray(0, 0)))
        assertEquals("AAAA", Base64.encode(byteArray(0, 0, 0)))
        assertEquals("/+8=", Base64.encode(byteArray(0xff, 0xef)))
    }

    private fun byteArray(vararg a : Int): ByteArray = a.toList().map { it.toByte() }.toByteArray()
}