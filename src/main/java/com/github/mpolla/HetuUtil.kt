package com.github.mpolla

import java.lang.RuntimeException
import java.time.DateTimeException
import java.time.LocalDate
import java.util.*
import kotlin.math.absoluteValue

/**
 * Utility functions for the Finnish Personal Identity code, motly known as
 * "henkilötunnus" or "hetu" in Finnish.
 *
 * @see <a href="https://vrk.fi/en/personal-identity-code1">Finnish Population Register Centre</a>
 */
object HetuUtil : FinIdUtil() {

    private const val DATEPART_START_INDEX = 0
    private const val DATEPART_END_INDEX = 6
    private const val INDIVIDUAL_START_INDEX = 7
    private const val INDIVIDUAL_END_INDEX = 10
    private const val CONTROL_CHAR_INDEX = 10
    private const val HETU_LENGTH = 11

    /**
     * Checks the validity of identity code.
     *
     * @param id Identity code to be validated.
     * @return Validity status of identity code.
     */
    override fun isValid(id: String?): Boolean {
        if (id?.length != HETU_LENGTH) {
            return false
        }
        if (!validateBirthDate(id)) {
            return false
        }
        return id[CONTROL_CHAR_INDEX] == computeControlCharacter(id)
    }

    private fun validateBirthDate(id: String): Boolean {
        // Trivial check: do we have enough characters?
        if (id?.length < 6) {
            return false
        }
        // Second phase: does the id contain numbers?
        val dd : Int
        val mm : Int
        val yy : Int
        try {
            dd = Integer.parseInt(id.substring(0, 2))
            mm = Integer.parseInt(id.substring(2, 4))
            yy = Integer.parseInt(id.substring(4, 6))
        } catch (nfe : NumberFormatException) {
            return false
        }
        val separator : Char = id[6]
        val yyyy = yy + when (separator) {
            '+' -> 1800
            '-' -> 1900
            'A' -> 2000
            else -> return false
        }
        // Third phase: does the day exist in the calendar?
        try {
            LocalDate.of(yyyy, mm, dd)
        } catch (dte : DateTimeException) {
            return false
        }
        return true
    }

    private fun getDatePart(hetu: String) : String {
        return hetu.substring(DATEPART_START_INDEX, DATEPART_END_INDEX)
    }

    private fun getIndividualPart(hetu: String) : String {
        return hetu.substring(INDIVIDUAL_START_INDEX, INDIVIDUAL_END_INDEX)
    }

    /**
     * Compute control character.
     *
     * @param hetu Personal id without the control character suffix.
     * @return Control character.
     */
    fun computeControlCharacter(hetu : String): Char {
        val dateIndividualInteger = Integer.parseInt(getDatePart(hetu) + getIndividualPart(hetu))
        return computeControlChar(dateIndividualInteger)
    }

    /**
     * Compute control character.
     *
     * @param birthDate Date of birth.
     * @param individualNumber Individual number (2..899).
     * @return Control character.
     */
    fun computeControlCharacter(birthDate : LocalDate, individualNumber : Int) : Char {
        val dateIndividualInteger = Integer.parseInt(getDatePart(birthDate) + String.format("%03d", individualNumber))
        return computeControlChar(dateIndividualInteger)
    }

    /**
     * Check male gender of given identity code.
     *
     * @param hetu Identity code.
     * @return True if male.
     */
    fun isMale(hetu: String): Boolean {
        return !isFemale(hetu)
    }

    /**
     * Check female gender of given identity code.
     *
     * @param hetu Identity code.
     * @return True if female.
     */
    fun isFemale(hetu: String): Boolean {
        val individual = Integer.parseInt(getIndividualPart(hetu))
        return (individual % 2) == 0
    }

    /**
     * Generate a random valid identity code for testing
     * purposes.
     *
     * @return Random person identity code.
     */
    override fun generateRandom() : String {
        val random = Random()
        val gender = random.nextBoolean()
        val minDay = LocalDate.of(1800, 1, 1).toEpochDay()
        val maxDay = LocalDate.of(2019, 10, 6).toEpochDay()
        val randomEpochDay = minDay + random.nextInt((maxDay - minDay).toInt())
        val randomBirthDate = LocalDate.ofEpochDay(randomEpochDay)
        val separator = getSeparatorChar(randomBirthDate)
        val individual = getRandomIndividualNumber(gender)
        val datePart = getDatePart(randomBirthDate)
        val idWoControl = String.format("%s%s%03d", datePart, separator, individual)
        return idWoControl + computeControlCharacter(randomBirthDate, individual)
    }

    /**
     * Format date part "ddmmyy" from a given birth date.
     *
     * @return Date part of personal id.
     */
    private fun getDatePart(birthDate: LocalDate): String {
        val day = birthDate.dayOfMonth.absoluteValue
        val month = birthDate.monthValue
        val yearSuffix = birthDate.year.absoluteValue % 100
        return String.format("%02d%02d%02d", day, month, yearSuffix)
    }

    /**
     * Get the separator character for a give birth date.
     *
     * @return Separator character: '+', '-' or 'A'.
     */
    private fun getSeparatorChar(birthDate: LocalDate) : Char {
        return when {
            birthDate.year in 1800..1899 -> '+'
            birthDate.year in 1900..1999 -> '-'
            birthDate.year >= 2000 -> 'A'
            else -> throw RuntimeException("Birth year out of supported range.")
        }
    }

    /**
     * Number of individual in a day: assigned from range 2..899 with even numbers for female
     * and odd numbers for males.
     *
     * @param isFemale Gender of person is female.
     * @return Random individual number (2..899).
     */
    private fun getRandomIndividualNumber(isFemale : Boolean) : Int {
        return when {
            !isFemale -> 2 + Random().nextInt(448) + 1
            else -> 2 + Random().nextInt(448)
        }
    }
}

