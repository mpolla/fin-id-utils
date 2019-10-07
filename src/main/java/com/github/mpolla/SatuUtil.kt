package com.github.mpolla

import java.lang.NumberFormatException
import java.time.LocalDate

import java.util.Random


/**
 * Utility functions for a FINUID (Finnish Unique Identification Number).
 *
 * SATU (Sähköinen AsisointiTUnnus in Finnish.
 */
object SatuUtil : FinIdUtil() {

    private const val NUMBERPART_START_INDEX = 0
    private const val NUMBERPART_END_INDEX = 8
    private const val CONTROL_CHARACTER_INDEX = 8
    private const val SATU_LENGHT = 9

    /**
     * Check the validity of a FINUID (Finnish Unique Identification Number).
     *
     * @param id Identity number to be validated.
     * @return True if valid.
     */
    override fun isValid(id: String?): Boolean {
        if (id == null || id.length != SATU_LENGHT) {
            return false
        }
        val numberPart: Int
        try {
            numberPart = Integer.parseInt(id.substring(NUMBERPART_START_INDEX, NUMBERPART_END_INDEX))
        } catch (nfe: NumberFormatException) {
            return false
        }
        return id[CONTROL_CHARACTER_INDEX] == computeControlChar(numberPart)

    }

    /**
     * Generate a random valid FINUID for testing
     * purposes.
     *
     * @return Random FINUID.
     */
    override fun generateRandom(): String {
        val randomIdNumber: Int = Random().nextInt(100000000) // 0..99999999
        val idWoControl = String.format("%08d", randomIdNumber)
        return idWoControl.plus(computeControlChar(randomIdNumber))
    }
}