package com.reis.game.mechanics.collision

import com.badlogic.gdx.Game
import com.badlogic.gdx.math.Vector2
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent
import com.reis.game.util.Axis
import com.reis.game.util.MapUtils
import java.awt.geom.Rectangle2D

/**
 * Created by bernardoreis on 1/6/18.
 */
class CollisionDetector constructor(private val entity: GameEntity, private val movement: Vector2) {

    private val distanceWalked: Vector2 = Vector2()
    private val distanceToWalk: Vector2 = Vector2(Math.abs(movement.x), Math.abs(movement.y))
    private val direction: Vector2 = Vector2(Math.signum(movement.x), Math.signum(movement.y))

    private val results: CollisionResults = CollisionResults()
    private val body: BodyComponent = entity.requireComponent(BodyComponent::class.java)
    private val hitbox: Hitbox = body.getHitbox()
    private val hotspotsX = hitbox.getHotspotsToTest(Axis.X, direction.x)
    private val hotspotsY = hitbox.getHotspotsToTest(Axis.Y, direction.y)

    companion object {
        fun checkCollision(entity: GameEntity): CollisionResults {
            val detector = CollisionDetector(entity, Vector2.Zero)
            val hotspots = detector.hitbox.getHotspots()
            for (hotspot in hotspots) {
                detector.testCollisionForHotspot(hotspot, Vector2.Zero)
            }
            return detector.results
        }

        fun isTouching(entity1: GameEntity, entity2: GameEntity): Boolean {
            val distanceX = Math.abs(entity2.getCenterX() - entity1.getCenterX())
            val distanceY = Math.abs(entity2.getCenterY() - entity1.getCenterY())
            val isTouchingX = distanceX <= entity1.width * 0.5 + entity2.width * 0.5
            val isTouchingY = distanceY <= entity1.height * 0.5 + entity2.height * 0.5
            return isTouchingX && isTouchingY
        }
    }

    fun test(): CollisionResults {
        if (body == null || !body.isCollidable()) {
            results.distanceWalked = movement
        }
        move()
        return results
    }

    private fun move() {
        while (distanceWalked.x < distanceToWalk.x || distanceWalked.y < distanceToWalk.y) {
            if (distanceWalked.x < distanceToWalk.x) {
                val collided = testCollisionForAxis(Axis.X, direction.x, distanceWalked.x)
                if (collided) {
                    distanceToWalk.x = distanceWalked.x
                } else {
                    distanceWalked.x++
                }
            }
            if (distanceWalked.y < distanceToWalk.y) {
                val collided = testCollisionForAxis(Axis.Y, direction.y, distanceWalked.y)
                if (collided) {
                    distanceToWalk.y = distanceWalked.y
                } else {
                    distanceWalked.y++
                }
            }
        }

        results.distanceWalked = distanceWalked.scl(direction)
    }

    private fun testCollisionForAxis(axis: Axis, direction: Float, distanceWalked: Float): Boolean {
        val hotspots = if (axis == Axis.X) hotspotsX else hotspotsY
        val offset = getOffset(axis, direction, distanceWalked)
        return hotspots!!.fold(false) {
            collided, hotspot -> collided || testCollisionForHotspot(hotspot, offset)
        }
    }

    private fun getOffset(axis: Axis, direction: Float, distanceWalked: Float): Vector2 {
        val offset = Vector2()
        if (axis == Axis.X) {
            offset.x += direction * (distanceWalked + 1)
        } else {
            offset.y += direction * (distanceWalked + 1)
        }
        return offset
    }

    private fun testCollisionForHotspot(hotspot: Vector2, offset: Vector2): Boolean {
        val futurePosition = hotspot.cpy().add(offset)
        val tile = MapUtils.getTileForPosition(futurePosition)
        val entitiesInTile = TileEntityMapper.getCurrentEntitiesInTile(tile.x.toInt(), tile.y.toInt())
        return entitiesInTile?.fold(false) {
            collided, entity -> collided || testCollisionWithEntity(entity, futurePosition)
        } ?: false
    }

    private fun testCollisionWithEntity(entityToTest: GameEntity, hotspot: Vector2): Boolean {
        if (entityToTest == this.entity || body!!.shouldIgnore(entityToTest)) {
            return false
        }
        val body: BodyComponent? = entityToTest.getComponent(BodyComponent::class.java)
        if (body == null || !body.isCollidable()) {
            return false
        }

        val collided = checkIntersectionBetweenEntities(entityToTest, hotspot)
        if (collided) {
            this.results.addCollision(Collision(entity, entityToTest))
        }

        // TODO return entityToTest.isTrigger() ?
        return collided
    }

    private fun checkIntersectionBetweenEntities(entityToTest: GameEntity, hotspot: Vector2): Boolean {
        //val r1 = Rectangle2D.Float(entity.x + offset.x, entity.y + offset.y, entity.width, entity.height)
        val hitbox = Rectangle2D.Float(entityToTest.x, entityToTest.y, entityToTest.width, entityToTest.height)
        return hitbox.contains(hotspot.x.toDouble(), hotspot.y.toDouble())
    }
}