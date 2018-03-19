package com.reis.game.graphics

import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.prototypes.AnimationProto.AnimationData
import com.reis.game.prototypes.AnimationProto.AnimationPrototype
import kotlin.reflect.KClass

/**
 * Created by berna on 18-Mar-18.
 */
class AnimationManager {

    private val cache: HashMap<AnimationKey, Animation> = HashMap()

    fun loadAnimations(entity: GameEntity, animationData: AnimationData) {
        val animationPrototypes = animationData.animationPrototypeList
        animationPrototypes.forEach { loadAnimation(entity, it) }
    }

    private fun loadAnimation(entity: GameEntity, animationPrototype: AnimationPrototype) {
        // TODO change way of accessing texture manager
        val textureAtlas = Main.getInstance().textureManager.getAtlas(animationPrototype.atlasName)
        val frames = textureAtlas.findRegions(animationPrototype.animationName)
    }

    fun getAnimationForAction(entity: GameEntity, action: EntityAction): Animation? {
        val key = getAnimationKey(entity, action)
        return cache[key]
    }

    fun getAnimationForActionClass(entity: GameEntity, actionClass: KClass<out EntityAction>): Animation? {
        val key = AnimationKey(entity::class, actionClass, entity.orientation)
        return cache[key]
    }

    private fun getAnimationKey(entity: GameEntity, action: EntityAction): AnimationKey {
        return AnimationKey(entity::class, action::class, entity.orientation)
    }
}