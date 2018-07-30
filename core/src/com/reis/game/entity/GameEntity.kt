package com.reis.game.entity

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Group
import com.reis.game.contants.GameConstants
import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.entity.components.BodyComponent
import com.reis.game.entity.components.ComponentsBag
import com.reis.game.entity.components.EntityComponent
import com.reis.game.entity.components.EntityControllerComponent
import com.reis.game.util.MapUtils

/**
 * Created by bernardoreis on 12/19/17.
 */
open class GameEntity(val id: Int) : Group() {

    private val components: ComponentsBag = ComponentsBag()

    var orientation: Int = 0

    private var row: Int = 0
    private var col: Int = 0
    private var tileHeight: Int = 0
    private var tileWidth: Int = 0

    init {
        this.name = id.toString()
        this.setCoordinates(0, 0)
        this.setSize(1, 1)
    }

    override fun act(delta: Float) {
        super.act(delta)
        components.update(this, delta)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        components.draw(this, batch, this.color.a)
    }

    override fun remove(): Boolean {
        this.getComponent<BodyComponent>(BodyComponent::class.java)?.unbindTiles()
        return super.remove()
    }

    fun onAddedToScene() {
        this.components.getComponents().forEach {
            it.onAddedToScene()
        }
    }

    fun addAction(action: EntityAction) {
        val component = this.getComponent<EntityControllerComponent>(EntityControllerComponent::class.java)
        component?.setAction(action) ?: action.start(this)
    }

    fun registerAction(action: Action) {
        super.addAction(action)
    }

    fun hasComponent(componentClass: Class<out Component>): Boolean {
        return this.components.hasComponent(componentClass)
    }

    fun getComponents(filter: ((EntityComponent) -> Boolean)? = null): List<EntityComponent> {
        return this.components.getComponents(filter)
    }

    fun <T : EntityComponent> getComponent(componentClass: Class<out Component>): T? {
        return this.components.getComponent(componentClass)
    }

    fun <T : EntityComponent> requireComponent(componentClass: Class<out Component>): T {
        val componentName = componentClass.simpleName
        return getComponent(componentClass) ?:
                throw IllegalStateException("Component $componentName doesn't exist for entity")
    }

    fun addComponent(component: EntityComponent): Boolean {
        return this.components.add(component)
    }

    fun removeComponent(componentClass: Class<out EntityComponent>): EntityComponent {
        return this.components.remove(componentClass)
    }

    fun getCenterX(): Float {
        return this.width * .5f + this.x
    }

    fun getCenterY(): Float {
        return this.height * .5f + this.y
    }

    fun setRow(row: Int) {
        this.setCoordinates(row, this.col)
    }

    fun getRow(): Int {
        return this.row
    }

    fun setCol(col: Int) {
        this.setCoordinates(this.row, col)
    }

    fun getCol(): Int {
        return this.col
    }

    fun setCoordinates(row: Int, col: Int): GameEntity {
        this.calcPosition(row, col)
        return this
    }

    private fun calcPosition(row: Int, col: Int) {
        this.setPosition((col * GameConstants.TILE_SIZE).toFloat(),
                (row * GameConstants.TILE_SIZE).toFloat())
    }

    fun setTileWidth(tileWidth: Int) {
        this.setSize(tileWidth, this.tileHeight)
    }

    fun getTileWidth(): Int {
        return this.tileWidth
    }

    fun setTileHeight(tileHeight: Int) {
        this.setSize(this.tileWidth, tileHeight)
    }

    fun getTileHeight(): Int {
        return this.tileHeight
    }

    fun setSize(widthInTiles: Int, heightInTiles: Int): GameEntity {
        this.tileWidth = widthInTiles
        this.tileHeight = heightInTiles
        this.calcSize(widthInTiles, heightInTiles)
        return this
    }

    private fun calcSize(tileWidth: Int, tileHeight: Int) {
        this.setSize((tileWidth * GameConstants.TILE_SIZE).toFloat(),
                (tileHeight * GameConstants.TILE_SIZE).toFloat())
    }

    override fun setSize(width: Float, height: Float) {
        getComponent<BodyComponent>(BodyComponent::class.java)?.unbindTiles()
        super.setSize(width, height)
    }

    override fun sizeChanged() {
        getComponent<BodyComponent>(BodyComponent::class.java)?.onSizeChanged()
    }

    override fun moveBy(x: Float, y: Float) {
        if (x != 0f && y != 0f) {
            getComponent<BodyComponent>(BodyComponent::class.java)?.unbindTiles()
        }
        super.moveBy(x, y)
    }

    override fun positionChanged() {
        this.row = MapUtils.toTileCoord(this.y)
        this.col = MapUtils.toTileCoord(this.x)
        getComponent<BodyComponent>(BodyComponent::class.java)?.onPositionChanged()
    }
}