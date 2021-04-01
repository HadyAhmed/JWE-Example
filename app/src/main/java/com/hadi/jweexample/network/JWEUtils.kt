package com.hadi.jweexample.network

import android.net.ParseException
import com.hadi.jweexample.Constants
import com.nimbusds.jose.*
import com.nimbusds.jose.crypto.DirectDecrypter
import com.nimbusds.jose.crypto.DirectEncrypter
import org.json.JSONObject
import java.util.*

object JWEUtils {
    private fun hexStr2Bytes(hexStr: String): ByteArray {
        var str = hexStr
        str = str.toLowerCase(Locale.ROOT)
        val length = str.length
        val bytes = ByteArray(length shr 1)
        var index = 0
        for (i in 0 until length) {
            if (index > str.length - 1) return bytes
            val highDit = (Character.digit(str[index], 16) and 0xFF).toByte()
            val lowDit = (Character.digit(str[index + 1], 16) and 0xFF).toByte()
            bytes[i] = (highDit shl 4 or lowDit).toByte()
            index += 2
        }
        return bytes
    }

    fun makeEncryptionOfJson(jsonObject: JSONObject): String? {
        val originalKey = Constants.SECRET_KEY!!
        val header = JWEHeader(
            JWEAlgorithm.DIR,
            EncryptionMethod.A128CBC_HS256,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            0,
            null,
            null,
            null,
            null
        )
        val payload =
            Payload(jsonObject.toString())
        val jweObject = JWEObject(header, payload)
        try {
            jweObject.encrypt(DirectEncrypter(hexStr2Bytes(originalKey)))
        } catch (e: JOSEException) {
            e.printStackTrace()
        }
        return jweObject.serialize()
    }

    fun makeDecryptionOfJson(str: String?): String {
        val originalKey = Constants.SECRET_KEY!!
        var jweObject2: JWEObject? = null
        try {
            jweObject2 = JWEObject.parse(str)
            jweObject2.decrypt(DirectDecrypter(hexStr2Bytes(originalKey)))
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: JOSEException) {
            e.printStackTrace()
        }
        return jweObject2!!.payload.toString()
    }
}

private infix fun Byte.shl(i: Int): Int = this.toInt().shl(i)
infix fun Int.or(that: Byte): Int = this.or(that.toInt())
