package com.reis.game.state.quests

import com.reis.game.state.State
import com.reis.game.state.events.Event

/**
 * Created by bernardoreis on 2/10/18.
 */
class QuestManager {

    private val activeQuests: MutableCollection<Quest> = ArrayList()
    private var questLog: Map<Int, Quest>? = null

    fun loadQuests(state: State) {
        val questStates = state.questStates
        val questLog = QuestLoader().loadQuestLog()
        questLog.forEach {
            val questId = it.key
            val stateForQuest = questStates[questId] ?: Quest.NOT_STARTED
            it.value.currentStep = stateForQuest

            if (stateForQuest >= Quest.NOT_STARTED) {
                activeQuests.add(it.value)
            }
        }
        this.questLog = questLog
    }

    fun updateActiveQuests(event: Event) {
        val iterator = activeQuests.iterator()
        iterator.forEach {
            it.updateCurrentStep(event)
            if (it.isComplete) {
                iterator.remove()
            }
        }
    }
}