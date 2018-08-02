package com.reis.game.scene

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.OrthographicCamera

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.reis.game.entity.GameEntity
import com.reis.game.util.MapUtils

class Scene(val map: TiledMap): Disposable {
    var mapRenderer: TiledMapRenderer = OrthogonalTiledMapRenderer(map)
    val stage: Stage = Stage()
    val camera: OrthographicCamera = stage.camera as OrthographicCamera
    val cameraHandler: CameraHandler = CameraHandler(camera)

    private val backgroundLayersIndexes: IntArray = intArrayOf(0)
    private val foregroundLayersIndexes: IntArray = intArrayOf(1)

    init {
        cameraHandler.bounds = MapUtils.getMapBounds(map)
    }

    fun update(delta: Float) {
        stage.act(Gdx.graphics.deltaTime)
        cameraHandler.update(delta)
    }

    fun draw() {
        mapRenderer.setView(cameraHandler.camera)
        mapRenderer.render(backgroundLayersIndexes)
        stage.draw()
        mapRenderer.render(foregroundLayersIndexes)
    }

    fun getWidth(): Float {
        return stage.viewport.screenWidth.toFloat()
    }

    fun addEntity(entity: GameEntity) {
        // TODO verify if stage will need layers as well
        stage.addActor(entity)
        entity.onAddedToScene()
    }

    override fun dispose() {
        stage.dispose()
        map.dispose()
    }
}