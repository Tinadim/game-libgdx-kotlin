package com.reis.game.mechanics.battle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent
import com.reis.game.entity.components.CombatComponent
import com.reis.game.entity.components.EntityComponent
import com.reis.game.entity.components.SpriteComponent
import com.reis.game.mechanics.collision.Collision
import com.reis.game.mechanics.collision.CollisionListener
import com.reis.game.mechanics.collision.CollisionType
import com.reis.game.mechanics.collision.filters.TriggerFilter
import com.reis.game.util.Filter

/**
 * Created by bernardoreis on 1/28/18.
 */
class DamageSource private constructor(): GameEntity(-1) {

    var baseDamage: Int = 0
    var hitCount: Int = 0
    var maxHits: Int = 0
    var knockBackDistance: Int = 0
    var duration: Float = -1f
    var actionDuration: Float = 0f
    var force: Vector2? = null
    var collisionType: CollisionType = CollisionType.ACTIVE
    var collisionListener: CollisionListener? = null

    private var elapsedTime: Float = 0f

    init {
        this.addComponent(SpriteComponent(this, Color.YELLOW))
    }

    companion object {
        fun baseDamage(baseDamage: Int): Builder {
            return Builder().baseDamage(baseDamage)
        }

        fun maxHits(maxHits: Int): Builder {
            return Builder().maxHits(maxHits)
        }

        fun knockBackDistance(knockBackDistance: Int): Builder {
            return Builder().knockBackDistance(knockBackDistance)
        }

        fun duration(duration: Float): Builder {
            return Builder().duration(duration)
        }

        fun actionDuration(duration: Float): Builder {
            return Builder().actionDuration(duration)
        }

        fun force(force: Vector2): Builder {
            return Builder().force(force)
        }

        fun collisionType(collisionType: CollisionType): Builder {
            return Builder().collisionType(collisionType)
        }

        fun collisionListener(collisionListener: CollisionListener): Builder {
            return Builder().collisionListener(collisionListener)
        }

        fun addComponent(component: EntityComponent): Builder {
            return Builder().addComponent(component)
        }
    }

    fun applyHit(target: GameEntity) {
        val combatComponent = target.getComponent<CombatComponent>(CombatComponent::class.java)
        combatComponent?.onHitTaken(this, this.force)
    }

    fun clone(): DamageSource {
        val source = DamageSource
                .baseDamage(baseDamage)
                .maxHits(maxHits)
                .knockBackDistance(knockBackDistance)
                .duration(duration)
                .actionDuration(actionDuration)
                .collisionType(collisionType)
        if (force != null) {
            source.force(force!!)
        }
        if (collisionListener != null) {
            source.collisionListener(collisionListener!!)
        }
        return source.build()
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (this.duration > 0) {
            this.elapsedTime += delta
            if (elapsedTime >= duration) {
                this.remove()
            }
        }
    }

    class DamageSourceCollisionListener: CollisionListener {
        // TODO filter friendly entities
        override val filter: Filter<Collision> = TriggerFilter()

        override fun onCollisionStarted(collision: Collision) {
            val damageSource = collision.entity as DamageSource
            damageSource.applyHit(collision.collidedWith)

            if (damageSource.maxHits > 0) {
                damageSource.hitCount += 1
                if (damageSource.hitCount >= damageSource.maxHits) {
                    damageSource.remove()
                }
            }
        }

        override fun onCollisionEnded(collision: Collision) {}
    }

    class Builder {
        private val source = DamageSource()
        private var collisionListener: CollisionListener? = null

        fun build(): DamageSource {
            val collisionListener = source.collisionListener
            if (collisionListener != null) {
                if (!source.hasComponent(BodyComponent::class.java)) {
                    val component = BodyComponent(source, source.collisionType)
                    source.addComponent(component)
                }
                Main.getInstance().collisionManager.registerListener(source, collisionListener)
            }
            return source
        }

        fun baseDamage(baseDamage: Int): Builder {
            source.baseDamage = baseDamage
            return this
        }

        fun maxHits(maxHits: Int): Builder {
            source.maxHits = maxHits
            return this
        }

        fun knockBackDistance(knockBackDistance: Int): Builder {
            source.knockBackDistance = knockBackDistance
            return this
        }

        fun duration(duration: Float): Builder {
            source.duration = duration
            return this
        }

        fun actionDuration(duration: Float): Builder {
            source.actionDuration = duration
            return this
        }

        fun force(force: Vector2): Builder {
            source.force = force
            return this
        }

        fun collisionType(collisionType: CollisionType): Builder {
            source.collisionType = collisionType
            return this
        }

        fun collisionListener(collisionListener: CollisionListener): Builder {
            source.collisionListener = collisionListener
            return this
        }

        fun addComponent(component: EntityComponent): Builder {
            source.addComponent(component)
            return this
        }
    }
}