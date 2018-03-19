package com.reis.game.event

/**
 * Created by berna on 18-Mar-18.
 */
abstract class EventFilter {

    abstract fun test(event: Event): Boolean
}