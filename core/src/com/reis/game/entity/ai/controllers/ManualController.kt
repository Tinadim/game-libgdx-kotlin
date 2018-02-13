package com.reis.game.entity.ai.controllers

import com.badlogic.gdx.math.Vector2
import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.handlers.ActionHandler
import com.reis.game.entity.ai.actions.Move
import com.reis.game.entity.ai.actions.handlers.AttackHandler
import com.reis.game.entity.ai.actions.handlers.InteractionHandler
import com.reis.game.entity.components.EntityControllerComponent
import com.reis.game.mechanics.collision.Collision
import com.reis.game.mechanics.collision.CollisionListener
import com.reis.game.mechanics.collision.filters.CollisionFilter
import com.reis.game.mechanics.collision.filters.FriendlyEntityCollision

/**
 * Created by bernardoreis on 2/11/18.
 */
class ManualController(private val entity: GameEntity): EntityController, CollisionListener {

    override val filter: CollisionFilter = FriendlyEntityCollision()
    private var actionHandler: ActionHandler = AttackHandler(entity)

    init {
        Main.getInstance().collisionManager.registerListener(entity, this)
    }

    fun handleDirectionalInput(direction: Vector2) {
        val action = Move(direction)
        entity.requireComponent<EntityControllerComponent>(EntityControllerComponent::class.java).addAction(action)
    }

    fun executePrimaryAction() {
        actionHandler.handle()
    }

    override fun onCollisionStarted(collision: Collision) {
        // TODO filter triggers
        actionHandler = InteractionHandler(entity, collision.collidedWith)
    }

    override fun onCollisionEnded(collision: Collision) {
        actionHandler = AttackHandler(entity)
    }

    override fun update(delta: Float) {}
}