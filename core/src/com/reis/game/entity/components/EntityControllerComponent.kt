package com.reis.game.entity.components

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.controllers.EntityController
import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.entity.ai.actions.ActionQueue
import com.reis.game.entity.ai.actions.Idle

/**
 * Created by bernardoreis on 12/25/17.
 */
class EntityControllerComponent constructor(entity: GameEntity,
    private val entityController: EntityController) : EntityComponent(entity) {

    private var currentAction: EntityAction? = null

    override fun onSceneStarted() {
        entityController.start()
    }

    override fun update(delta: Float) {
        entityController.update(delta)
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