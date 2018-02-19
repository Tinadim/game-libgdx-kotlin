package com.reis.game.entity.ai.controllers

import com.badlogic.gdx.math.Vector2
import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.handlers.ActionHandler
import com.reis.game.entity.ai.actions.handlers.AttackHandler
import com.reis.game.entity.ai.actions.handlers.DialogHandler
import com.reis.game.entity.ai.actions.handlers.InteractionHandler
import com.reis.game.entity.components.MovementComponent
import com.reis.game.mechanics.collision.Collision
import com.reis.game.mechanics.collision.CollisionListener
import com.reis.game.mechanics.collision.filters.CollisionFilter
import com.reis.game.mechanics.collision.filters.FriendlyEntityCollision
import com.reis.game.scene.dialog.DialogListener
import com.reis.game.scene.dialog.DialogWindow

/**
 * Created by bernardoreis on 2/11/18.
 */
class ManualController(private val entity: GameEntity): EntityController,
        CollisionListener, DialogListener {

    override val filter: CollisionFilter = FriendlyEntityCollision()
    private var actionHandler: ActionHandler = AttackHandler(entity)
        set(value) {
            previousHandler = actionHandler
            field = value
        }
    private var previousHandler: ActionHandler = actionHandler

    init {
        Main.getInstance().collisionManager.registerListener(entity, this)
        Main.getInstance().dialogManager.registerListener(entity, this)
    }

    fun handleDirectionalInput(direction: Vector2) {
        entity.requireComponent<MovementComponent>(MovementComponent::class.java).move(direction)
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

    override fun onDialogStarted(dialog: DialogWindow) {
        actionHandler = DialogHandler(entity)
    }

    override fun onDialogEnded(dialog: DialogWindow) {
        if (!Main.getInstance().dialogManager.hasDialogs(entity)) {
            actionHandler = previousHandler
        }
    }

    override fun update(delta: Float) {}
}