package com.reis.game.mechanics.battle

import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 2/17/18.
 */
interface DamageSourceFactory {

    fun buildDamageSource(attacker: GameEntity): DamageSource
}