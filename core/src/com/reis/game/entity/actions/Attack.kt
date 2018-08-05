package com.reis.game.entity.actions

import com.reis.game.Main
import com.reis.game.contants.ActionConstants
import com.reis.game.entity.GameEntity
import com.reis.game.mechanics.battle.DamageSourceFactory

/**
 * Created by bernardoreis on 2/4/18.
 */
class Attack(private val damageSourceFactory: DamageSourceFactory):
        DurationAction(ActionConstants.ATTACK_PRIORITY, -1f) {

    init {
        this.selfReplaceable = false
    }

    override fun onStart(entity: GameEntity) {
        super.onStart(entity)
        val damageSource = damageSourceFactory.buildDamageSource(entity)
        // TODO this feels hacky
        this.duration = damageSource.actionDuration
        // TODO change this
        val stage = Main.getInstance().scene.stage
        stage.addActor(damageSource)
    }
}