package com.reis.game.mechanics.battle.weapons

import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.MovementComponent
import com.reis.game.mechanics.battle.DamageSource
import com.reis.game.mechanics.collision.CollisionType

/**
 * Created by bernardoreis on 2/18/18.
 */
class TestWeapon: Weapon() {

    override fun buildDamageSource(attacker: GameEntity): DamageSource {
        // TODO for this weapon, the parent damage source still adds an unnecessary object to
        // the scene
        val parentDamageSource = DamageSource.Builder().build()
        val baseDamageSource = DamageSource
                .maxHits(1)
                .baseDamage(1)
                .actionDuration(1f)
                .collisionType(CollisionType.PASSIVE)
                .collisionListener(DamageSource.DamageSourceCollisionListener())
                .build()

        val eastDamageSource = baseDamageSource.clone()
        val southDamageSource = baseDamageSource.clone()
        val westDamageSource = baseDamageSource.clone()

        calcInitialPositionAndVelocity(attacker, GameConstants.NORTH, baseDamageSource)
        calcInitialPositionAndVelocity(attacker, GameConstants.EAST, eastDamageSource)
        calcInitialPositionAndVelocity(attacker, GameConstants.SOUTH, southDamageSource)
        calcInitialPositionAndVelocity(attacker, GameConstants.WEST, westDamageSource)

        parentDamageSource.addActor(baseDamageSource)
        parentDamageSource.addActor(eastDamageSource)
        parentDamageSource.addActor(southDamageSource)
        parentDamageSource.addActor(westDamageSource)

        return parentDamageSource
    }

    private fun calcInitialPositionAndVelocity(attacker: GameEntity, direction: Int,
            damageSource: DamageSource) {
        damageSource.addComponent(MovementComponent(damageSource))
        damageSource.setSize(1, 1)

        val baseY = attacker.y
        val baseX = attacker.x
        val velocity: Vector2

        when (direction) {
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
            else -> throw IllegalStateException("Invalid direction: " + direction)
        }

        damageSource.requireComponent<MovementComponent>(MovementComponent::class.java)
                .move(velocity)
    }
}