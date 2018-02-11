package com.reis.game.state.quests

import com.reis.game.state.events.Event
import com.reis.game.state.events.EventEmitter
import com.reis.game.state.events.EventType

/**
 * Created by bernardoreis on 2/10/18.
 */
class Quest(val id: Int, var currentStep: Int = 0): EventEmitter {

    private val steps: MutableList<QuestStep> = ArrayList()

    var isComplete: Boolean = false

    companion object {
        const val FINISHED: Int = -1
        const val NOT_STARTED: Int = 0
    }

    fun addStep(step: QuestStep) {
        this.steps.add(step)
    }

    fun updateCurrentStep(event: Event) {
        val step = steps[currentStep]
        step.updateRequirements(event)
        if (step.isComplete) {
            currentStep += 1
            if (currentStep == steps.size) {
                this.onComplete()
            }
        }
    }

    private fun onComplete() {
        println("Quest complete")
        this.isComplete = true
        this.emit(Event(EventType.QUEST_COMPLETE, this))
    }
}