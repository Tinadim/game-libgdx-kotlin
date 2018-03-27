package com.reis.game.util

import com.reis.game.Main

class Utils {

    companion object {
        @JvmStatic
        fun randomInt(min: Int, max: Int): Int {
            // TODO change access to resource manager
            return Main.getInstance().resourceManager.random.nextInt(max - min + 1) + min
        }
    }
}