package jetbrains.datalore.vis.svgMapper.skia
import kotlin.test.Test
import kotlin.test.assertEquals

class Base64Test {

    @Test
    fun emptyBuffer() {
        val str = ""
        val data = "".toByteArray()
        assertEquals(str, Base64.encode(data))
        assertEquals(data, Base64.decode(str))
    }

    @Test
    fun f() {
        val str = "Zg=="
        val data = "f".toByteArray()
        assertEquals(str, Base64.encode(data))
        assertEquals(data, Base64.decode(str))
    }

    @Test
    fun fo() {
        val str = "Zm8="
        val data = "fo".toByteArray()
        assertEquals(str, Base64.encode(data))
        assertEquals(data, Base64.decode(str))
    }

    @Test
    fun foo() {
        val str = "Zm9v"
        val data = "foo".toByteArray()
        assertEquals(str, Base64.encode(data))
        assertEquals(data, Base64.decode(str))
    }

    @Test
    fun foob() {
        val str = "Zm9vYg=="
        val data = "foob".toByteArray()
        assertEquals(str, Base64.encode(data))
        assertEquals(data, Base64.decode(str))
    }

    @Test
    fun fooba() {
        val str = "Zm9vYmE="
        val data = "fooba".toByteArray()
        assertEquals(str, Base64.encode(data))
        assertEquals(data, Base64.decode(str))
    }

    @Test
    fun foobar() {
        val data = "foobar".toByteArray()
        val str = "Zm9vYmFy"
        assertEquals(str, Base64.encode(data))
        assertEquals(data, Base64.decode(str))
    }

    @Test
    fun bunchEncode() {
        assertEquals("AA==", Base64.encode(byteArray(0)))
        assertEquals("AAA=", Base64.encode(byteArray(0, 0)))
        assertEquals("AAAA", Base64.encode(byteArray(0, 0, 0)))
        assertEquals("/+8=", Base64.encode(byteArray(0xff, 0xef)))
    }

    @Test
    fun bunchDecode() {
        assertEquals(byteArray(0), Base64.decode("AA=="))
        assertEquals(byteArray(0, 0), Base64.decode("AAA="))
        assertEquals(byteArray(0, 0, 0), Base64.decode("AAAA"))
        assertEquals(byteArray(0xff, 0xef), Base64.decode("/+8="))
    }

    private fun byteArray(vararg a : Int): ByteArray = a.toList().map { it.toByte() }.toByteArray()
    private fun assertEquals(expected: ByteArray, actual: ByteArray) {
        assertEquals(expected.toList(), actual.toList())
    }
}