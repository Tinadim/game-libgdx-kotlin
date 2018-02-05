package com.reis.game.entity.ai.actions

import com.reis.game.Main
import com.reis.game.contants.ActionConstants
import com.reis.game.entity.GameEntity
import com.reis.game.mechanics.battle.DamageSource

/**
 * Created by bernardoreis on 2/4/18.
 */
class Attack(private val damageSource: DamageSource):
        DurationAction(ActionConstants.ATTACK_PRIORITY, damageSource.actionDuration) {

    init {
        this.selfReplaceable = false
    }

    override fun onStart(entity: GameEntity) {
        super.onStart(entity)
        Main.getInstance().stage.addActor(this.damageSource)
    }
}