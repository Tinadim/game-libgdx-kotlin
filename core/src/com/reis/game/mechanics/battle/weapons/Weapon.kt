package com.reis.game.mechanics.battle.weapons

import com.reis.game.mechanics.battle.DamageSourceFactory

/**
 * Created by bernardoreis on 2/4/18.
 */
abstract class Weapon: DamageSourceFactory {

    var baseDamage: Int = 1
    var attackSpeed: Float = 2f
}