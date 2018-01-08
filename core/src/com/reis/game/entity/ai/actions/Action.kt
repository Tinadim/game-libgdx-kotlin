package com.reis.game.entity.ai.actions

import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 12/19/17.
 */
abstract class Action constructor(private val priority: Int) {

    private var finished: Boolean = false
    private var interrupted: Boolean = false
    protected var selfReplaceable: Boolean = true

    fun start(entity: GameEntity) {
        this.onStart(entity)
    }

    fun update(delta: Float, entity: GameEntity) {
        if (this.finished) {
            return
        }
        this.onUpdate(delta, entity)
        if (this.finished && !this.interrupted) {
            this.onComplete(entity)
        }
    }

    fun stop(entity: GameEntity) {
        this.interrupted = true
        this.finished = true
        this.onStop(entity)
    }

    open fun onStart(entity: GameEntity) {}

    open fun onUpdate(delta: Float, entity: GameEntity) {}

    open fun onStop(entity: GameEntity) {}

    open fun onComplete(entity: GameEntity) {}

    open fun isFinished(): Boolean {
        return finished
    }

    protected fun finish() {
        this.finished = true
    }

    fun isSelfReplaceable(): Boolean {
        return this.selfReplaceable
    }

    fun getPriority(): Int {
        return this.priority
    }
}