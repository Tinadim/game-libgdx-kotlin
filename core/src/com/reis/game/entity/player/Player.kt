package com.reis.game.entity.player

import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.prototypes.AiProto.AiData
import com.reis.game.prototypes.AnimationProto.AnimationData
import com.reis.game.prototypes.AnimationProto.AnimationPrototype
import com.reis.game.prototypes.AnimationProto.AnimationFrame

/**
 * Created by bernardoreis on 12/20/17.
 */
object Player: GameEntity(GameConstants.PLAYER_ID) {
    @JvmStatic
    fun buildPlayerAiData(): AiData {
        return AiData
                .newBuilder()
                .setAiType("PlayerAi")
                .build()
    }

    @JvmStatic
    fun buildPlayerAnimationData(): AnimationData {
        val data = ArrayList<AnimationPrototype>()
        buildIdleAnimations(data)
        buildWalkingAnimations(data)
        return AnimationData
                .newBuilder()
                .addAllAnimationPrototype(data)
                .build()
    }

    private fun buildIdleAnimations(animationList: ArrayList<AnimationPrototype>) {
        val frame = AnimationFrame
                .newBuilder()
                .setIndex(0)
                .setFrameDuration(1.5f)
                .build()

        val ad0 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Idle")
                .setEntityOrientation(0)
                .setFrameDuration(0.05f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player idle 0")
                .addFrames(frame)
                .build()

        val ad1 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Idle")
                .setEntityOrientation(1)
                .setFrameDuration(0.05f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player idle 1")
                .addFrames(frame)
                .build()

        val ad2 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Idle")
                .setEntityOrientation(2)
                .setFrameDuration(0.05f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player idle 2")
                .addFrames(frame)
                .build()

        val ad3 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Idle")
                .setEntityOrientation(3)
                .setFrameDuration(0.05f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player idle 3")
                .addFrames(frame)
                .build()

        animationList.add(ad0)
        animationList.add(ad1)
        animationList.add(ad2)
        animationList.add(ad3)
    }

    private fun buildWalkingAnimations(animationList: ArrayList<AnimationPrototype>) {
        val ad0 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Movement")
                .setEntityOrientation(0)
                .setFrameDuration(0.07f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player walk 0")
                .build()

        val ad1 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Movement")
                .setEntityOrientation(1)
                .setFrameDuration(0.07f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player walk 1")
                .build()

        val ad2 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Movement")
                .setEntityOrientation(2)
                .setFrameDuration(0.07f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player walk 2")
                .build()

        val ad3 = AnimationPrototype
                .newBuilder()
                .setActionClassName("Movement")
                .setEntityOrientation(3)
                .setFrameDuration(0.07f)
                .setFrameOffsetX(8 + 32 * -0.5f)
                .setAtlasName("player/player.atlas")
                .setAnimationName("player walk 3")
                .build()

        animationList.add(ad0)
        animationList.add(ad1)
        animationList.add(ad2)
        animationList.add(ad3)
    }
}