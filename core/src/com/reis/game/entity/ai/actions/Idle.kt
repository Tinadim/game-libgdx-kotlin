package com.reis.game.entity.ai.actions

import com.reis.game.contants.ActionConstants

/**
 * Created by bernardoreis on 12/31/17.
 */
class Idle: DurationAction {

    constructor() : super(ActionConstants.MIN_PRIORITY, -1f)

    constructor(duration: Float) : super(ActionConstants.MIN_PRIORITY, duration)
}