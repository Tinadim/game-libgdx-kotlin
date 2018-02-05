package com.reis.game.entity.components

import com.badlogic.gdx.math.Vector2
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.KnockBack
import com.reis.game.mechanics.battle.DamageSource

/**
 * Created by bernardoreis on 1/28/18.
 */

class CombatComponent(entity: GameEntity): EntityComponent(entity) {

    private val BLINK_DURATION = 0.15f

    private var hp: Int = 1
    private var contactDamage: Int = 1
    private var invincibleDuration: Float = 1f // seconds
    private var invincibilityCooldown: Float = 0f // seconds
    private var isInCooldown: Boolean = false

    fun onHitTaken(source: DamageSource, force: Vector2?) {
        if (isInCooldown) {
            return
        }
        isInCooldown = true
        invincibilityCooldown = 0f

        this.applyHit(source)

        val spriteComponent = this.entity.getComponent<SpriteComponent>(SpriteComponent::class.java)
        spriteComponent?.blink(invincibleDuration, BLINK_DURATION)

        val action = KnockBack(force ?: Vector2.Zero,
                invincibleDuration * 0.3f, source.knockBackDistance)
        entity.getComponent<AiComponent>(AiComponent::class.java)?.addAction(action)
    }

    fun applyTouchDamageToEntity(target: GameEntity, force: Vector2? = null) {
        if (contactDamage > 0) {
            val source = buildContactDamageSource(force)
            source.applyHit(target)
        }
    }

    private fun buildContactDamageSource(force: Vector2?): DamageSource {
        return DamageSource
                .baseDamage(contactDamage)
                .force(force ?: Vector2.Zero)
                .build()
    }

    private fun applyHit(source: DamageSource) {
        //TODO apply hit effects
        this.hp -= source.baseDamage
    }

    override fun update(delta: Float) {
        invincibilityCooldown += delta
        if (invincibilityCooldown >= invincibleDuration) {
            isInCooldown = false
        }
    }
}