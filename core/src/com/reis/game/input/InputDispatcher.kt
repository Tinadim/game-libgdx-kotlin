package com.reis.game.input

import com.badlogic.gdx.math.Vector2

interface InputDispatcher {
    fun executePrimaryAction()

    fun handleDirectionalInput(direction: Vector2)
}