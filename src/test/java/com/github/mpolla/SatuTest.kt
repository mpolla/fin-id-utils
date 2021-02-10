package com.github.mpolla

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class SatuTest {

    @Test
    fun testNull() {
        assertFalse(SatuUtil.isValid(null))
    }

    @Test
    fun testValidSatu() {
        assertTrue(SatuUtil.isValid("10011187H"))
    }

    @Test
    fun testControlCharCase() {
        assertFalse(SatuUtil.isValid("10011187h"))
    }

    @Test
    fun testInvalidSatu() {
        assertFalse(SatuUtil.isValid("10011187A"))
    }

    @Test
    fun testTooShort() {
        assertFalse(SatuUtil.isValid("10011H"))
    }

    @Test
    fun testEmpty() {
        assertFalse(SatuUtil.isValid(""))
    }

    @Test
    fun generateRandomTest() {
        for (i in 1..100) {
            val randomSatu = SatuUtil.generateRandom()
            assertTrue(SatuUtil.isValid(randomSatu))
        }
    }
}