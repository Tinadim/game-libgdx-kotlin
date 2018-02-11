package com.reis.game.state.events

import com.reis.game.Main
import java.util.LinkedList

/**
 * Created by bernardoreis on 2/10/18.
 */
class EventProcessor {

    private val eventQueue: LinkedList<Event> = LinkedList()

    fun addEvent(event: Event) {
        eventQueue.push(event)
    }

    fun update() {
        // TODO change this "main" access
        val questManager = Main.getInstance().questManager
        if (eventQueue.isNotEmpty()) {
            val copy = LinkedList(eventQueue)
            // TODO: not sure if this nested loop is the most efficient way of updating quests
            copy.forEach { questManager.updateActiveQuests(it) }
            // new events might be added to the queue while updating active quests
            eventQueue.removeAll(copy)
        }
    }
}