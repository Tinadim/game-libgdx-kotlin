package com.reis.game.state.quests

import com.reis.game.state.requirements.TriggerFired

/**
 * Created by bernardoreis on 2/10/18.
 */
class QuestLoader {

    fun loadQuestLog(): Map<Int, Quest> {
        return loadMockQuestLog()
    }

    private fun loadMockQuestLog(): Map<Int, Quest> {
        // TODO remove this and proper implement quest log loading
        val questLog = HashMap<Int, Quest>()
        val quest = Quest(1)

        val step1 = QuestStep()
        val requirement1 = TriggerFired(3)
        step1.addRequirement(requirement1)

        quest.addStep(step1)

        questLog[quest.id] = quest
        return questLog
    }
}