package com.reis.game.graphics

/**
 * Created by berna on 18-Mar-18.
 */
class Animation constructor(private val keyFrames: List<out AnimationFrame>) {

    private var currentIndex: Int = 0
    private var elapsedTime: Float = 0f

    fun update(deltaTime: Float) {
        elapsedTime += deltaTime
        val currentFrame = keyFrames[currentIndex % keyFrames.size]
        if (elapsedTime > currentFrame.frameDuration) {
            elapsedTime = 0f
            currentIndex++
        }
        if (currentIndex >= keyFrames.size) {
            currentIndex = 0
        }
    }

    fun getKeyFrame(): AnimationFrame {
        return keyFrames[currentIndex]
    }
}