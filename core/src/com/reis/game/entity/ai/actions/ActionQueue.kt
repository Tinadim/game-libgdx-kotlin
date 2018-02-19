package com.reis.game.entity.ai.actions

import com.google.common.collect.MinMaxPriorityQueue

/**
 * Created by bernardoreis on 1/6/18.
 */
class ActionQueue(private val capacity: Int = 10) {

    private val comparator: ActionComparator = ActionComparator()

    val idle = Idle()

    // Using capacity + 1 to prevent list from being resized when the last element is added
    private var queue: MinMaxPriorityQueue<EntityAction> = MinMaxPriorityQueue
        .orderedBy(comparator)
        .maximumSize(capacity + 1)
        .create()

    // TODO implement pooling for actions

    fun addAction(action: EntityAction) {
        // Let it add to the queue to be sorted according to the action priority
        queue.add(action)
        if (queue.size >= this.capacity) {
            // If size reached the maximum, remove the action with the least priority
            queue.removeLast()
        }
    }

    fun checkNextAction(): EntityAction {
        return queue.peek() ?: idle
    }

    fun getNextAction(): EntityAction {
        val action = queue.poll()
        return action ?: idle
    }

    class ActionComparator: Comparator<EntityAction> {
        override fun compare(a1: EntityAction, a2: EntityAction): Int {
            return a2.getPriority() - a1.getPriority()
        }
    }
}