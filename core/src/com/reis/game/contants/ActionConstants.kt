package com.reis.game.contants

object ActionConstants {
    const val ACTION_PACKAGE = "com.reis.game.entity.ai.actions"
    const val MIN_PRIORITY = 0
    const val MOVE_PRIORITY = 1
    const val ATTACK_PRIORITY = 4
    const val KNOCKBACK_PRIORITY = 5
    const val INTERACTION_PRIORITY = 6
    const val IDLE_PRIORITY = MIN_PRIORITY

    const val BASE_TURN_DURATION = 1f
    const val DEFAULT_MIN_IDLE_TURNS = 1
    const val DEFAULT_MAX_IDLE_TURNS = 4
    const val DEFAULT_RANGE_OF_SIGHT = 5
}