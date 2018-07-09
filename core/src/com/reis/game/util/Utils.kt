package com.reis.game.util

import com.reis.game.Main

object Utils {

    @JvmStatic
    fun randomInt(min: Int, max: Int): Int {
        // TODO change access to resource manager
        return Main.getInstance().resourceManager.random.nextInt(max - min + 1) + min
    }

    @JvmStatic
    fun randomBoolean(): Boolean {
        // TODO change access to resource manager
        return Main.getInstance().resourceManager.random.nextBoolean()
    }
}