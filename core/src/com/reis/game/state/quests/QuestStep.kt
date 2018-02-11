package com.reis.game.state.quests

import com.reis.game.state.events.Event
import com.reis.game.state.events.EventEmitter
import com.reis.game.state.events.EventType
import com.reis.game.state.reactions.Reaction
import com.reis.game.state.requirements.Requirement
import java.util.ArrayList

/**
 * Created by bernardoreis on 2/10/18.
 */
class QuestStep: EventEmitter {

    private var requirements: MutableList<Requirement> = ArrayList()
    private var reactions: MutableList<Reaction>? = null
    var isComplete: Boolean = false

    fun addRequirement(requirement: Requirement) {
        requirements.add(requirement)
    }

    fun addReaction(reaction: Reaction) {
        if (this.reactions == null) {
            this.reactions = ArrayList()
        }
        this.reactions?.add(reaction)
    }

    fun updateRequirements(event: Event) {
        var complete = true
        requirements.forEach{
            if (!it.checkEventType(event)) {
                complete = false
                return
            }
            it.update(event)
            complete = complete && it.isFulfilled
        }

        if (complete) {
            this.onComplete()
        }
    }

    private fun onComplete() {
        println("Step complete")
        this.isComplete = true
        this.reactions?.forEach{ it.fire() }
        this.emit(Event(EventType.QUEST_STEP_COMPLETE, this))
    }
}