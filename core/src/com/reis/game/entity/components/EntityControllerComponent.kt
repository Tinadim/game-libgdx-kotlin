package com.reis.game.entity.components

import com.reis.game.entity.GameEntity
import com.reis.game.entity.actions.EntityAction
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.controllers.AiFactory
import com.reis.game.prototypes.AiProto.AiData

/**
 * Created by bernardoreis on 12/25/17.
 */
class EntityControllerComponent constructor(entity: GameEntity,
        private val aiData: AiData) : EntityComponent(entity) {

    private var currentAction: EntityAction? = null

    val ai: AI = AiFactory.invoke(entity, aiData)

    override fun onAddedToScene() {
        // TODO this shouldn't be stored inside the controller component
        val state = ai.buildStateMachine(aiData)
        ai.start(state)
    }

    override fun update(delta: Float) {
        ai.update(delta)
        // TODO check impact of this logic on the state machine
        // if (currentAction?.isFinished()) {
        // currentAction = Idle()
        // }
        // Action update already happens as part of the action update
        // currentAction.update(delta, entity)
    }

    private fun shouldReplaceCurrentAction(nextAction: EntityAction): Boolean {
        val currentAction = this.currentAction ?: return true
        if (currentAction === nextAction) {
            return false
        }

        val hasPriorityOverCurrentAction = nextAction.getPriority() > currentAction.getPriority()
        val isSelfReplaceable = currentAction::class == nextAction::class &&
                currentAction.isSelfReplaceable()

        return currentAction.isFinished() ||
                hasPriorityOverCurrentAction ||
                isSelfReplaceable
    }

    private fun replaceCurrentAction(nextAction: EntityAction) {
        val currentAction = this.currentAction
        if (currentAction != null && !currentAction.isFinished()) {
            currentAction.stop(entity)
        }
        this.currentAction = nextAction
        nextAction.start(entity)
    }

    fun setAction(action: EntityAction) {
        if (shouldReplaceCurrentAction(action)) {
            replaceCurrentAction(action)
        }
    }

    fun getCurrentAction(): EntityAction? {
        return currentAction
    }
}