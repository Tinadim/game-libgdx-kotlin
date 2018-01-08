package com.reis.game.entity.ai.transitions

import com.reis.game.entity.ai.AI
import com.reis.game.entity.player.ActionProcessor

/**
 * Created by bernardoreis on 12/20/17.
 */
class PlayerActionCoordinator: TransitionCondition {

    override fun evaluate(ai: AI): Boolean {
        val currentAction = ai.getCurrentState().getAction()
        val nextAction = ActionProcessor.getNextAction()
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
}