package com.reis.game.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.reis.game.entity.ai.actions.Move
import com.reis.game.entity.player.ActionProcessor

/**
 * Created by bernardoreis on 1/2/18.
 */
object InputHandler: InputProcessor {

    private val direction: Vector2 = Vector2(0f, 0f)

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.RIGHT) {
            return updateDirection(1f, 0f)
        } else if (keycode == Input.Keys.LEFT) {
            return updateDirection(-1f, 0f)
        } else if (keycode == Input.Keys.UP) {
            return updateDirection(0f, 1f)
        } else if (keycode == Input.Keys.DOWN) {
            return updateDirection(0f, -1f)
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.RIGHT) {
            return updateDirection(-1f, 0f)
        } else if (keycode == Input.Keys.LEFT) {
            return updateDirection(1f, 0f)
        } else if (keycode == Input.Keys.UP) {
            return updateDirection(0f, -1f)
        } else if (keycode == Input.Keys.DOWN) {
            return updateDirection(0f, 1f)
        }
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    private fun updateDirection(x: Float, y: Float): Boolean {
        this.direction.add(x, y)
        ActionProcessor.addAction(Move(this.direction.cpy()))
        return true
    }
}