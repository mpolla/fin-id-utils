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
     * Test ids containing new separator characters introduced in 1.1.2023
     * @see <a href="https://vm.fi/en/project-on-redesigning-the-system-of-personal-identity-codes">Redesigning the personal identity code system</a>
     */
    @Test
    fun test2023redesign() {
        assertTrue(HetuUtil.isValid("010594Y9032"))
        assertTrue(HetuUtil.isValid("010594Y9021"))
        assertTrue(HetuUtil.isValid("020594X903P"))
        assertTrue(HetuUtil.isValid("020594X902N"))
        assertTrue(HetuUtil.isValid("030594W903B"))
        assertTrue(HetuUtil.isValid("030694W9024"))
        assertTrue(HetuUtil.isValid("040594V9030"))
        assertTrue(HetuUtil.isValid("040594V902Y"))
        assertTrue(HetuUtil.isValid("050594U903M"))
        assertTrue(HetuUtil.isValid("050594U902L"))
        assertTrue(HetuUtil.isValid("010516B903X"))
        assertTrue(HetuUtil.isValid("010516B902W"))
        assertTrue(HetuUtil.isValid("020516C903K"))
        assertTrue(HetuUtil.isValid("020516C902J"))
        assertTrue(HetuUtil.isValid("030516D9037"))
        assertTrue(HetuUtil.isValid("030516D9026"))
        assertTrue(HetuUtil.isValid("010501E9032"))
        assertTrue(HetuUtil.isValid("020502E902X"))
        assertTrue(HetuUtil.isValid("020503F9037"))
        assertTrue(HetuUtil.isValid("020504A902E"))
        assertTrue(HetuUtil.isValid("020504B904H"))
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
