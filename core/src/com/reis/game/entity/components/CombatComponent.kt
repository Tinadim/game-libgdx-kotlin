package com.reis.game.entity.components

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.Attack
import com.reis.game.entity.ai.actions.KnockBack
import com.reis.game.entity.player.Player
import com.reis.game.mechanics.battle.DamageSource
import com.reis.game.mechanics.battle.DamageSourceFactory
import com.reis.game.mechanics.collision.Collision
import com.reis.game.mechanics.collision.CollisionListener
import com.reis.game.mechanics.collision.filters.EnemyEntityCollision
import com.reis.game.util.Filter

/**
 * Created by bernardoreis on 1/28/18.
 */

class CombatComponent(entity: GameEntity, private val team: Int): EntityComponent(entity), CollisionListener {
    var hp: Int = 1
    var contactDamage: Int = 1
    var invincibleDuration: Float = 1f // seconds
    var invincibilityCooldown: Float = 0f // seconds
    var isInCooldown: Boolean = false

    // TODO turn this into a list
    private var primaryDamageSourceFactory: DamageSourceFactory? = null

    override val filter: Filter<Collision> = EnemyEntityCollision()

    companion object {
        const val BLINK_DURATION = 0.15f
    }

    fun isEnemyOf(entity: GameEntity): Boolean {
        val component = entity.getComponent<CombatComponent>(CombatComponent::class.java)
        return component !== null && component.team != this.team
    }

    fun attack(factory: DamageSourceFactory) {
        val action = Attack(factory)
        entity.addAction(action)
    }

    fun getPrimaryDamageSource(): DamageSourceFactory {
        return primaryDamageSourceFactory!!
    }

    fun setPrimaryDamageSource(damageSourceFactory: DamageSourceFactory) {
        primaryDamageSourceFactory = damageSourceFactory
    }

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
        entity.addAction(action)
    }

    private fun applyTouchDamageToEntity(target: GameEntity, force: Vector2? = null) {
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

    override fun onCollisionStarted(collision: Collision) {
        // TODO figure out a way to get movement direction here
        applyTouchDamageToEntity(collision.collidedWith)
    }

    override fun onCollisionEnded(collision: Collision) {}
}