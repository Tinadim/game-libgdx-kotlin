package com.reis.game.mechanics.collision

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
    private val hitbox: Hitbox = body.hitbox
    private val hotspotsX = hitbox.getHotspotsToTest(Axis.X, direction.x)
    private val hotspotsY = hitbox.getHotspotsToTest(Axis.Y, direction.y)

    companion object {
        fun checkCollision(entity: GameEntity): CollisionResults {
            val detector = CollisionDetector(entity, Vector2.Zero)
            val hotspots = detector.hitbox.getHotspots()
            val entities = detector.getEntitiesToTest(hotspots, Vector2.Zero)
            entities.forEach {
                detector.testCollisionWithEntity(it, Vector2.Zero)
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
        if (!body.isCollidable) {
            results.distanceWalked = movement
        }
        move()
        return results
    }

    private fun move() {
        while (distanceWalked.x < distanceToWalk.x || distanceWalked.y < distanceToWalk.y) {
            if (distanceWalked.x < distanceToWalk.x) {
                val inc = 1f
                val collided = testCollisionForAxis(Axis.X, direction.x, distanceWalked.x, inc)
                if (collided) {
                    distanceToWalk.x = distanceWalked.x
                } else {
                    distanceWalked.x += inc
                }
            }
            if (distanceWalked.y < distanceToWalk.y) {
                val inc = 1f
                val collided = testCollisionForAxis(Axis.Y, direction.y, distanceWalked.y, inc)
                if (collided) {
                    distanceToWalk.y = distanceWalked.y
                } else {
                    distanceWalked.y += inc
                }
            }
        }

        results.distanceWalked = distanceWalked.scl(direction)
    }

    private fun testCollisionForAxis(axis: Axis, direction: Float,
            distanceWalked: Float, inc: Float): Boolean {
        val hotspots = if (axis == Axis.X) hotspotsX else hotspotsY
        val offset = getOffset(axis, direction, distanceWalked, inc)
        val entitiesToTest = getEntitiesToTest(hotspots, offset)
        return testCollisionWithEntities(entitiesToTest, offset, axis)
    }

    private fun getOffset(axis: Axis, direction: Float, distanceWalked: Float,
            inc: Float): Vector2 {
        val offset = Vector2()
        if (axis == Axis.X) {
            offset.x += direction * (distanceWalked + inc)
        } else {
            offset.y += direction * (distanceWalked + inc)
        }
        return offset
    }

    private fun getEntitiesToTest(hotspots: List<Vector2>, offset: Vector2): Collection<GameEntity> {
        val entitiesToTest = HashSet<GameEntity>()
        return hotspots.fold(entitiesToTest) {
            entities, hotspot ->
            val futurePosition = hotspot.cpy().add(offset)
            val tile = MapUtils.getTileForPosition(futurePosition)
            val entitiesInTile = TileEntityMapper.getCurrentEntitiesInTile(tile.x.toInt(), tile.y.toInt())
            if (entitiesInTile != null) {
                entities.addAll(entitiesInTile)
            }
            entities
        }
    }

    private fun testCollisionWithEntities(entities: Collection<GameEntity>, offset: Vector2,
            axis: Axis): Boolean {
        return entities.fold(false, {
            collided, entity ->
            collided || testCollisionWithEntity(entity, offset)
        })
    }

    private fun testCollisionWithEntity(entityToTest: GameEntity, offset: Vector2): Boolean {
        if (entityToTest == this.entity || body.shouldIgnore(entityToTest)) {
            return false
        }
        val body: BodyComponent? = entityToTest.getComponent(BodyComponent::class.java)
        if (body == null || !body.isCollidable) {
            return false
        }

        val intersection = checkIntersectionBetweenEntities(entityToTest, offset)
        if (!intersection.isEmpty) {
            this.results.addCollision(Collision(entity, entityToTest, intersection))
            return !body.isTrigger
        }

        return false
    }

    private fun checkIntersectionBetweenEntities(entityToTest: GameEntity, offset: Vector2): Rectangle2D {
        val r1 = Rectangle2D.Float(entity.x + offset.x, entity.y + offset.y, entity.width, entity.height)
        val hitbox = Rectangle2D.Float(entityToTest.x, entityToTest.y, entityToTest.width, entityToTest.height)
        return r1.createIntersection(hitbox)
    }
}