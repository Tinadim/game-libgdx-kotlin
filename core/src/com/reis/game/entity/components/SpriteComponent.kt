package com.reis.game.entity.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
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
        shapeRenderer.setProjectionMatrix(batch.projectionMatrix)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(color.r, color.g, color.b, parentAlpha)
        shapeRenderer.rect(entity.x, entity.y, entity.width, entity.height)
        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)
        batch.begin()
    }
}