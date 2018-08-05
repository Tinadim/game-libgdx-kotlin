package com.reis.game.graphics

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.utils.Array
import com.reis.game.Main
import com.reis.game.contants.ActionConstants
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.actions.EntityAction
import com.reis.game.prototypes.AnimationProto.AnimationData
import com.reis.game.prototypes.AnimationProto.AnimationPrototype
import kotlin.reflect.KClass

/**
 * Created by berna on 18-Mar-18.
 */
class AnimationCache {

    private val cache: HashMap<AnimationKey, Animation> = HashMap()

    fun loadAnimations(entity: GameEntity, animationData: AnimationData) {
        val animationPrototypes = animationData.animationPrototypeList
        animationPrototypes.forEach { loadAnimation(entity, it) }
    }

    private fun loadAnimation(entity: GameEntity, animationPrototype: AnimationPrototype) {
        // TODO change way of accessing texture manager
        val textureAtlas = Main.getInstance().textureManager.getAtlas(animationPrototype.atlasName)
        val regions = textureAtlas.findRegions(animationPrototype.animationName)
        val frames = createAnimationFrames(animationPrototype, regions)
        val animation = Animation(frames)

        cacheAnimation(animationPrototype, animation, entity)
    }

    private fun createAnimationFrames(animationPrototype: AnimationPrototype,
            regions: Array<AtlasRegion>): List<AnimationFrame> {
        val frameDuration = animationPrototype.frameDuration
        val offsetX = animationPrototype.frameOffsetX
        val offsetY = animationPrototype.frameOffsetY
        val orientation = animationPrototype.entityOrientation
        val frames = regions.map {
            if (orientation == GameConstants.WEST) {
                it.flip(true, false);
            }
            AnimationFrame(it, frameDuration, offsetX, offsetY)
        }

        applyFrameOverrides(animationPrototype, frames)
        return frames
    }

    private fun applyFrameOverrides(animationPrototype: AnimationPrototype,
            frames: List<AnimationFrame>) {
        val frameOverrides = animationPrototype.framesList
        frameOverrides.forEach {
            val index = it.index
            val frame = frames[index]
            if (it.frameDuration != 0f) frame.frameDuration = it.frameDuration
            if (it.frameOffsetX != 0f) frame.drawOffsetX = it.frameOffsetX
            if (it.frameOffsetY != 0f) frame.drawOffsetY = it.frameOffsetY
        }
    }

    private fun cacheAnimation(animationPrototype: AnimationPrototype,
            animation: Animation, entity: GameEntity) {
        val className = "${ActionConstants.ACTION_PACKAGE}.${animationPrototype.actionClassName}"
        try {
            val klass = Class.forName(className).kotlin as KClass<out EntityAction>
            val key = AnimationKey(entity::class, klass, animationPrototype.entityOrientation)
            cache[key] = animation
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
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