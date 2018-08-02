package com.reis.game.scene

import com.reis.game.Main
import com.reis.game.entity.EntityBuilder
import com.reis.game.prototypes.EntityTypeProto.EntityData

object SceneBuilder {
    @JvmStatic
    fun loadScene(name: String): Scene {
        // TODO change this way of accessing the resource manager
        val screenData = Main.getInstance().resourceManager.loadScreenData(name)
        val scene = createScene(screenData.tileMapName)
        loadEntities(scene, screenData.entityDataList)
        // TODO load background music
        return scene
    }

    private fun createScene(mapName: String): Scene {
        val map = Main.getInstance().resourceManager.loadMap(mapName)
        return Scene(map)
    }

    private fun loadEntities(scene: Scene, entityDataList: List<EntityData>) {
        val entityBuilder = EntityBuilder()
        entityDataList.forEach {
            val entity = entityBuilder.build(it)
            scene.addEntity(entity)
        }
    }
}