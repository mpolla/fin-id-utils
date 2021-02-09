package com.github.mpolla

import java.math.BigDecimal
import java.math.RoundingMode

public abstract class FinIdUtil {

    private val SYMBOLS = "0123456789ABCDEFHJKLMNPRSTUVWXY"
    private val MULTIPLIER = 31
    private val SCALE = 25


    public abstract fun isValid(id : String?) : Boolean

    public abstract fun generateRandom() : String

    /**
     * Control character computation based on birgth date and the individual number.
     *
     * @see <a href="https://vrk.fi/en/personal-identity-code1">Finnish Population Register Centre's documentation</a>
     */
    public fun computeControlChar(idNumber : Int) : Char {
        val decimalPart = BigDecimal(idNumber).divide(BigDecimal(MULTIPLIER), SCALE, RoundingMode.HALF_UP).remainder(BigDecimal.ONE)
        val decimalPartMultiplied = decimalPart.times(BigDecimal(MULTIPLIER))
        val ccIndex = decimalPartMultiplied.setScale(0, BigDecimal.ROUND_HALF_UP).toInt()
        return SYMBOLS[ccIndex]
    }

}
