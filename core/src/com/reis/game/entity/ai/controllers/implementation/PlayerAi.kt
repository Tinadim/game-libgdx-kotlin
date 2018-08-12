package com.reis.game.entity.ai.controllers.implementation

import com.badlogic.gdx.math.Vector2
import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.states.State
import com.reis.game.entity.ai.states.player.BasePlayerState
import com.reis.game.entity.ai.states.player.CollisionState
import com.reis.game.entity.ai.states.player.DialogState
import com.reis.game.entity.ai.states.player.IdleState
import com.reis.game.entity.components.MovementComponent
import com.reis.game.input.InputDispatcher
import com.reis.game.mechanics.collision.Collision
import com.reis.game.mechanics.collision.CollisionListener
import com.reis.game.mechanics.collision.filters.CollisionFilter
import com.reis.game.mechanics.collision.filters.InteractionCollisionFilter
import com.reis.game.prototypes.AiProto.AiData
import com.reis.game.scene.dialog.DialogListener
import com.reis.game.scene.dialog.DialogWindow

class PlayerAi(entity: GameEntity): AI(entity),
        InputDispatcher, DialogListener, CollisionListener {

    override val collisionFilter: CollisionFilter = InteractionCollisionFilter()

    private var previousState: State? = null

    init {
        // TODO change this way of registering listeners.
        // Perhaps envity should extend event emitter, and a way to use would be
        // entity.on(EVENT_TYPE.COLLISION, this)
        // entity.on(EVENT_TYPE.DIALOG, this)
        Main.getInstance().collisionManager.registerListener(entity, this)
        Main.getInstance().dialogManager.registerListener(entity, this)
    }

    override fun setCurrentState(state: State) {
        previousState = getCurrentState()
        super.setCurrentState(state)
    }

    override fun buildStateMachine(data: AiData): State {
        return IdleState(this)
    }

    override fun executePrimaryAction() {
        val currentState = getCurrentState() as BasePlayerState
        currentState.executePrimaryAction()
    }

    override fun handleDirectionalInput(direction: Vector2) {
        entity.requireComponent<MovementComponent>(MovementComponent::class.java).move(direction)
    }

    override fun onDialogEnded(dialog: DialogWindow) {
        // TODO should these be transitions?
        val previousState = this.previousState
        if (!Main.getInstance().dialogManager.hasDialogs(entity) && previousState != null) {
            setCurrentState(previousState)
        }
    }

    override fun onDialogStarted(dialog: DialogWindow) {
        setCurrentState(DialogState(this))
    }

    override fun onCollisionStarted(collision: Collision) {
        setCurrentState(CollisionState(this, collision.collidedWith))
    }

    override fun onCollisionEnded(collision: Collision) {
        setCurrentState(IdleState(this))
    }
}