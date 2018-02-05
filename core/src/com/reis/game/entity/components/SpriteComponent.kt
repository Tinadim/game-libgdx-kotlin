package com.reis.game.entity.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 1/2/18.
 */
class SpriteComponent(entity: GameEntity, private val color: Color): EntityComponent(entity) {

    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    override fun draw(batch: Batch, parentAlpha: Float) {
        drawShape(batch, parentAlpha)
    }

    private fun drawShape(batch: Batch, parentAlpha: Float) {
        batch.end()
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(batch.projectionMatrix)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(color.r, color.g, color.b, parentAlpha)
        shapeRenderer.rect(entity.x, entity.y, entity.width, entity.height)
        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)
        batch.begin()
    }

    fun blink(totalDuration: Float, blinkDuration: Float) {
        val repeat = Math.floor(totalDuration.toDouble() / blinkDuration).toInt()
        val blink = Actions.repeat(repeat,
                Actions.sequence(
                        Actions.fadeOut(blinkDuration * 0.5f),
                        Actions.fadeIn(blinkDuration * 0.5f)
                )
        )
        this.entity.addAction(blink)
    }
}