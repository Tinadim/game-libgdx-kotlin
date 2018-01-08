package com.reis.game.entity.ai.actions

import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.MovementComponent

/**
 * Created by bernardoreis on 1/1/18.
 */
class Move(private val velocity: Vector2): Action(1) {

    //Local cache of movement component for the entity
    private var component: MovementComponent? = null

    init {
        this.selfReplaceable = true
    }

    override fun onStart(entity: GameEntity) {
        this.component = entity.requireComponent(MovementComponent::class.java)
        component?.move(velocity)
    }

    override fun onUpdate(delta: Float, entity: GameEntity) {
        val component = this.component
        if (component != null && component.isStopped()) {
            this.finish()
        }
    }

    override fun onStop(entity: GameEntity) {
        component?.stop()
    }
}