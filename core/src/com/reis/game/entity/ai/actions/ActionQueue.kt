package com.reis.game.entity.ai.actions

import com.google.common.collect.MinMaxPriorityQueue

/**
 * Created by bernardoreis on 1/6/18.
 */
class ActionQueue(private val capacity: Int = 10) {

    private val idle = Idle()
    private val comparator: ActionComparator = ActionComparator()

    // Using capacity + 1 to prevent list from being resized when the last element is added
    private var queue: MinMaxPriorityQueue<Action> = MinMaxPriorityQueue
        .orderedBy(comparator)
        .maximumSize(capacity + 1)
        .create()

    // TODO implement pooling for actions

    fun addAction(action: Action) {
        // Let it add to the queue to be sorted according to the action priority
        queue.add(action)
        if (queue.size >= this.capacity) {
            // If size reached the maximum, remove the action with the least priority
            queue.removeLast()
        }
    }

    fun checkNextAction(): Action {
        return queue.peek() ?: idle
    }

    fun getNextAction(): Action {
        val action = queue.poll()
        return action ?: idle.reset()
    }

    class ActionComparator: Comparator<Action> {
        override fun compare(a1: Action, a2: Action): Int {
            return a2.getPriority() - a1.getPriority()
        }
    }
}