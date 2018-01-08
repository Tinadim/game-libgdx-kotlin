package com.reis.game.util

/**
 * Created by bernardoreis on 1/7/18.
 */
interface Filter <T> {

    fun test(t: T): Boolean
}