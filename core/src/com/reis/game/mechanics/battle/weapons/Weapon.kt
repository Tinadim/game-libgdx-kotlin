package com.reis.game.mechanics.battle.weapons

import com.reis.game.entity.GameEntity
import com.reis.game.mechanics.battle.DamageSource

/**
 * Created by bernardoreis on 2/4/18.
 */
abstract class Weapon {

    var baseDamage: Int = 1
    var attackSpeed: Float = 0.5f

    abstract fun buildDamageSource(attacker: GameEntity): DamageSource
}