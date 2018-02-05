package com.reis.game.entity.components

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.AI
import com.reis.game.entity.ai.actions.Action
import com.reis.game.entity.ai.actions.ActionQueue
import com.reis.game.entity.ai.actions.Idle
import com.reis.game.prototypes.AiData

/**
 * Created by bernardoreis on 12/25/17.
 */
class AiComponent constructor(entity: GameEntity, aiData: AiData? = null) : EntityComponent(entity) {

    private val actionProcessor: ActionQueue = ActionQueue()

    /**
     * Decoupling the StateMachineAI from the action logic. The state machine will be used
     * to feed the action processor, and the the action priority system will decide if the
     * current action should be replaced
     */

    private var ai: AI? = null
    private var currentAction: Action = Idle()

    init {
        currentAction.start(entity)
        if (aiData != null) {
            this.ai = AI.parse(aiData, entity)
        }
    }

    override fun update(delta: Float) {
        ai?.update(delta)
        val nextAction = actionProcessor.getNextAction()
        if (shouldReplaceCurrentAction(nextAction)) {
            replaceCurrentAction(nextAction)
        }
        currentAction.update(delta, entity)
    }

    private fun shouldReplaceCurrentAction(nextAction: Action): Boolean {
        if (currentAction == nextAction) {
            return false
        }

        val hasPriorityOverCurrentAction = nextAction.getPriority() > currentAction.getPriority()
        val isSelfReplaceable = currentAction::class == nextAction::class &&
                currentAction.isSelfReplaceable()

        return currentAction.isFinished() ||
                hasPriorityOverCurrentAction ||
                isSelfReplaceable
    }

    private fun replaceCurrentAction(nextAction: Action) {
        if (!currentAction.isFinished()) {
            currentAction.stop(entity)
        }
        currentAction = nextAction
        currentAction.start(entity)
    }

    fun addAction(action: Action) {
        actionProcessor.addAction(action)
    }

    fun getActionProcessor(): ActionQueue {
        return actionProcessor
    }
}