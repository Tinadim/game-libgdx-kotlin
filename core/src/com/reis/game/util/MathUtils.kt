package com.reis.game.util

object MathUtils {
    @JvmStatic
    fun absMin(number1: Float, number2: Float): Float {
        return if (Math.abs(number1) < Math.abs(number2)) number1 else number2
    }

    @JvmStatic
    fun absMax(number1: Float, number2: Float): Float {
        return if (Math.abs(number1) > Math.abs(number2)) number1 else number2
    }
}