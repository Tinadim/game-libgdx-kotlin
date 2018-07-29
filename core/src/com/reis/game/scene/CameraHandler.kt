package com.reis.game.scene

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.reis.game.entity.GameEntity

class CameraHandler(val camera: OrthographicCamera) {
    companion object {
        const val DEFAULT_LERP = 10f
    }

    var entityToFollow: GameEntity? = null
    var bounds: Rectangle? = null
    var lerp: Float = DEFAULT_LERP

    fun update(delta: Float) {
        updateCameraPosition(delta)
        checkCameraBounds()
        camera.update()
    }

    private fun updateCameraPosition(delta: Float) {
        val entityToFollow = this.entityToFollow
        if (entityToFollow != null) {
            val position = camera.position
            position.x += (entityToFollow.x - position.x) * lerp * delta
            position.y += (entityToFollow.y - position.y) * lerp * delta
        }
    }

    private fun checkCameraBounds() {
        val bounds = this.bounds
        if (bounds != null) {
            val cameraHalfWidth = camera.viewportWidth * .5f
            val cameraHalfHeight = camera.viewportHeight * .5f
            camera.position.x = MathUtils.clamp(
                    camera.position.x,
                    cameraHalfWidth,
                    bounds.width - cameraHalfWidth
            )
            camera.position.y = MathUtils.clamp(
                    camera.position.y,
                    cameraHalfHeight,
                    bounds.height - cameraHalfHeight
            )
        }
    }
}