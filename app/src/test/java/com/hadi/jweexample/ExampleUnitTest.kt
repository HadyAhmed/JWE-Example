package com.hadi.jweexample

import com.hadi.jweexample.network.JWEUtils
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun decryption_test() {
        val test =
            JWEUtils.makeDecryptionOfJson("eyJhbGciOiJkaXIiLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0..quf6nw8Zs19McW45QHReKA.v5nCYxLkeKaUdCaqX7i9zw.jETKfyNMnMt0L_aDoPWwxA")
        assertEquals("Test", test)
    }
}