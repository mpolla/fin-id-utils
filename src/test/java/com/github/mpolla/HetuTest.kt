package com.github.mpolla

import junit.framework.TestCase.*

import org.junit.Test
import java.time.LocalDate
import java.time.Month

import org.apache.logging.log4j.LogManager

class HetuTest {

    val log = LogManager.getLogger("HetuTest")

    @Test
    fun nullTest() {
        assertFalse(HetuUtil.isValid(null))
    }

    /**
     * Validate an id for a person born before 1900.
     */
    @Test
    fun testPre1900() {
        assertTrue(HetuUtil.isValid("131052+308T"))
    }

    /**
     * Validate an id for a person born in 1900-2000.
     */
    @Test
    fun test1900() {
        assertTrue(HetuUtil.isValid("131052-308T"))
    }

    /**
     * Validate an id for a person born after 2000.
     */
    @Test
    fun testPost2000() {
        assertTrue(HetuUtil.isValid("131052A308T"))
    }

    /**
     * Basic test case of validateing a personal id.
     */
    @Test
    fun validTest() {
        // Maija Mäkinotkelma from https://vrk.fi/henkilotunnus
        assertTrue(HetuUtil.isValid("131052-308T"))
    }

    /**
     * Test the validity of an id with a correct control character but a nonsensical birth date.
     */
    @Test
    fun testValidIdWithImpossibleDate() {
        assertFalse(HetuUtil.isValid("999999-002A"))
    }

    /**
     * Test the validity of a randomly generated set of ids.
     */
    @Test
    fun generateRandomTest() {
        for (i in 1..100) {
            val randomHetu = HetuUtil.generateRandom()
            assertTrue(HetuUtil.isValid(randomHetu))
        }
    }

    /**
     * Validate the gender of the person.
     */
    @Test
    fun testGender() {
        assertTrue(HetuUtil.isFemale("131052-308T"))
        assertFalse(HetuUtil.isMale("131052-308T"))
    }

    @Test
    fun testControlCharacterForString() {
        assertEquals('T', HetuUtil.computeControlCharacter("131052-308"))
    }

    @Test
    fun testControlCharacterForDate() {
        val testDate = LocalDate.of(1952, Month.OCTOBER, 13)
        val testIndividualNumber = 308
        assertEquals('T', HetuUtil.computeControlCharacter(testDate, testIndividualNumber))
    }
}
