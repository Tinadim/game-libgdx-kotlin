package com.reis.game.entity.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentType
import com.badlogic.ashley.utils.Bag
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.Array
import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 12/25/17.
 */
class ComponentsBag {

    private val components: Bag<EntityComponent> = Bag()
    private val componentsArray: Array<EntityComponent> = Array()
    private val immutableComponentsArray: ImmutableArray<EntityComponent> = ImmutableArray(componentsArray)

    fun update(entity: GameEntity, delta: Float) {
        immutableComponentsArray.forEach({ it.update(delta) })
    }

    fun draw(entity: GameEntity, batch: Batch, parentAlpha: Float) {
        immutableComponentsArray.forEach({ it.draw(batch, entity.color.a) })
    }

    fun hasComponent(componentClass: Class<out Component>): Boolean {
        return getComponent<EntityComponent>(componentClass) != null
    }

    fun <T : EntityComponent> getComponent(componentClass: Class<out Component>): T? {
        return getComponent(ComponentType.getFor(componentClass))
    }

    private fun <T : EntityComponent> getComponent(componentType: ComponentType): T? {
        val componentTypeIndex = componentType.index
        return if (componentTypeIndex < components.capacity) {
            components.get(componentType.index) as T?
        } else {
            null
        }
    }

    fun add(component: EntityComponent): Boolean {
        val componentClass = component.javaClass
        val oldComponent = getComponent<EntityComponent>(componentClass)

        if (component === oldComponent) {
            return false
        }

        if (oldComponent != null) {
            removeInternal(componentClass)
        }

        val componentTypeIndex = ComponentType.getIndexFor(componentClass)
        components.set(componentTypeIndex, component)
        componentsArray.add(component)

        return true
    }

    fun remove(componentClass: Class<out EntityComponent>): EntityComponent {
        val componentType = ComponentType.getFor(componentClass)
        val componentTypeIndex = componentType.index
        return components.get(componentTypeIndex)
    }

    private fun removeInternal(componentClass: Class<out EntityComponent>): Boolean {
        val componentType = ComponentType.getFor(componentClass)
        val componentTypeIndex = componentType.index
        val removeComponent = components.get(componentTypeIndex)

        if (removeComponent != null) {
            components.set(componentTypeIndex, null)
            componentsArray.removeValue(removeComponent, true)
            return true
        }

        return false
    }
}