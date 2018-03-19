package com.reis.game.event

/**
 * Created by berna on 18-Mar-18.
 */
data class Event(val type: EventType, val trigger: Any, val data: Any? = null)