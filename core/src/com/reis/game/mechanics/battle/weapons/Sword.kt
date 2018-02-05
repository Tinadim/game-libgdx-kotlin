package com.reis.game.mechanics.battle.weapons

import com.badlogic.gdx.Game
import com.reis.game.contants.GameConstants
import com.reis.game.contants.GameConstants.TILE_SIZE
import com.reis.game.entity.GameEntity
import com.reis.game.mechanics.battle.DamageSource
import com.reis.game.mechanics.collision.CollisionType

/**
 * Created by bernardoreis on 2/4/18.
 */
class Sword: Weapon() {

    override fun buildDamageSource(attacker: GameEntity): DamageSource {
        val damageSource = DamageSource
                .baseDamage(this.baseDamage)
                .collisionType(CollisionType.PASSIVE)
                .collisionListener(DamageSource.DamageSourceCollisionListener())
                .duration(this.attackSpeed)
                .actionDuration(this.attackSpeed)
                .knockBackDistance(1)
                .build()

        damageSource.setSize(1, 1)
        calcAttackRange(attacker, damageSource)
        return damageSource
    }

    private fun calcAttackRange(attacker: GameEntity, damageSource: DamageSource) {
        val orientation = attacker.orientation
        val baseY = attacker.y
        val baseX = attacker.x
        when (orientation) {
            GameConstants.NORTH -> damageSource.setPosition(baseX, baseY + TILE_SIZE)
            GameConstants.EAST -> damageSource.setPosition(baseX + TILE_SIZE, baseY)
            GameConstants.SOUTH -> damageSource.setPosition(baseX, baseY - TILE_SIZE)
            GameConstants.WEST -> damageSource.setPosition(baseX - TILE_SIZE, baseY)
            else -> throw IllegalStateException("Invalid orientation: " + orientation)
        }
    }
}