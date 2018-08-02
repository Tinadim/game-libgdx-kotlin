package com.reis.game.mechanics.battle.weapons

import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.MovementComponent
import com.reis.game.mechanics.battle.DamageSource
import com.reis.game.mechanics.collision.CollisionType

/**
 * Created by bernardoreis on 2/4/18.
 */
class Bow: Weapon() {

    init {
        this.attackSpeed = 1f
    }

    override fun buildDamageSource(attacker: GameEntity): DamageSource {
        val damageSource = DamageSource
                .baseDamage(this.baseDamage)
                .maxHits(1)
                .actionDuration(this.attackSpeed)
                .collisionType(CollisionType.PASSIVE)
                .collisionListener(DamageSource.DamageSourceCollisionListener())
                .knockBackDistance(1)
                .build()
        damageSource.addComponent(MovementComponent(damageSource))
        damageSource.setTileSize(1, 1)
        calcInitialPositionAndVelocity(attacker, damageSource)
        return damageSource
    }

    private fun calcInitialPositionAndVelocity(attacker: GameEntity, damageSource: DamageSource) {
        val orientation = attacker.orientation
        val baseY = attacker.y
        val baseX = attacker.x
        val velocity: Vector2

        when (orientation) {
            GameConstants.NORTH -> {
                damageSource.setPosition(baseX, baseY + GameConstants.TILE_SIZE)
                velocity = Vector2(0f, 2f)
            }
            GameConstants.EAST -> {
                damageSource.setPosition(baseX + GameConstants.TILE_SIZE, baseY)
                velocity = Vector2(2f, 0f)
            }
            GameConstants.SOUTH -> {
                damageSource.setPosition(baseX, baseY - GameConstants.TILE_SIZE)
                velocity = Vector2(0f, -2f)
            }
            GameConstants.WEST -> {
                damageSource.setPosition(baseX - GameConstants.TILE_SIZE, baseY)
                velocity = Vector2(-2f, 0f)
            }
            else -> throw IllegalStateException("Invalid orientation: " + orientation)
        }

        damageSource.requireComponent<MovementComponent>(MovementComponent::class.java)
                .move(velocity)
    }
}