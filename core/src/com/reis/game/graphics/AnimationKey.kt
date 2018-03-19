package com.reis.game.graphics

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.EntityAction
import kotlin.reflect.KClass

/**
 * Created by berna on 18-Mar-18.
 */
data class AnimationKey(val entityClass: KClass<out GameEntity>,
    val actionClass: KClass<out EntityAction>, val orientation: Int)