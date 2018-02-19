package com.reis.game.entity.ai.actions

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 12/19/17.
 */
open class EntityAction constructor(private val priority: Int): Action() {

    private var finished: Boolean = false
    private var interrupted: Boolean = false
    protected var selfReplaceable: Boolean = true

    var hasStarted = false

    override fun setActor(actor: Actor?) {
        if (actor != null && actor !is GameEntity) {
            throw IllegalArgumentException("Actor must be a GameEntity instance")
        }
        super.setActor(actor)
    }

    private fun getEntity(): GameEntity {
        return actor as GameEntity
    }

    fun start(entity: GameEntity) {
        if (!hasStarted) {
            hasStarted = true
            // TODO this feels hacky
            entity.registerAction(this)
            onStart(entity)
        }
    }

    override fun act(delta: Float): Boolean {
        if (actor != null) {
            update(delta, getEntity())
        }
        return finished
    }

    private fun update(delta: Float, entity: GameEntity) {
        if (finished) {
            return
        }
        onUpdate(delta, entity)
        if (finished && !interrupted) {
            this.onComplete(entity)
        }
    }

    fun stop(entity: GameEntity) {
        interrupted = true
        finish()
        onStop(entity)
    }

    open fun onStart(entity: GameEntity) {}

    open fun onUpdate(delta: Float, entity: GameEntity) {}

    open fun onStop(entity: GameEntity) {}

    open fun onComplete(entity: GameEntity) {}

    open fun isFinished(): Boolean {
        return finished
    }

    protected fun finish() {
        finished = true
    }

    fun isSelfReplaceable(): Boolean {
        return selfReplaceable
    }

    fun getPriority(): Int {
        return priority
    }
}