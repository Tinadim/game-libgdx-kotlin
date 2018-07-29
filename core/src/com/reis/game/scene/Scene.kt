package com.reis.game.scene

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.OrthographicCamera

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapRenderer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.reis.game.Main
import com.reis.game.util.MapUtils

class Scene: Disposable {
    var backgroundMusic: Music? = null
    var map: TiledMap? = null
    var mapRenderer: TiledMapRenderer? = null
    val stage: Stage = Stage()
    val camera: OrthographicCamera = stage.camera as OrthographicCamera
    val cameraHandler: CameraHandler = CameraHandler(camera)

    private val backgroundLayers: IntArray = intArrayOf(0)
    private val foregroundLayers: IntArray = intArrayOf(1)

    fun load(mapName: String) {
        // TODO change this way of accessing resource manager
        this.map = Main.getInstance().resourceManager.loadMap(mapName)
        // TODO check scale and turn it into a constant
        mapRenderer = OrthogonalTiledMapRenderer(this.map)
        // mapRenderer = OrthogonalTiledMapRenderer(this.map, GameConstants.SCALE)
        // TODO isolate cameraHandler creation, set width and height as constants
        // cameraHandler.setToOrtho(false, 30f, 20f)
        cameraHandler.bounds = MapUtils.getMapBounds(map!!)
    }

    fun update(delta: Float) {
        stage.act(Gdx.graphics.deltaTime)
        cameraHandler.update(delta)
    }

    fun draw() {
        mapRenderer?.setView(cameraHandler.camera)
        // TODO render layers separately
        mapRenderer?.render(backgroundLayers)
        stage.draw()
        mapRenderer?.render(foregroundLayers)
    }

    fun getWidth(): Float {
        return stage.viewport.screenWidth.toFloat()
    }

    override fun dispose() {
        stage.dispose()
        map?.dispose()
    }
}