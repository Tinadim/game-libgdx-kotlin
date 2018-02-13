package com.reis.game.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.reis.game.entity.ai.controllers.ManualController

/**
 * Created by bernardoreis on 1/2/18.
 */
class InputHandler(private val entityController: ManualController): InputProcessor {

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
        } else if (keycode == Input.Keys.SPACE) {
            return executeAction()
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
        this.entityController.handleDirectionalInput(this.direction.cpy())
        return true
    }

    private fun executeAction(): Boolean {
        this.entityController.executePrimaryAction()
        return true
    }
}