package com.reis.game.entity.components

import com.badlogic.gdx.graphics.g2d.Batch
import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.entity.ai.actions.Idle
import com.reis.game.event.Event
import com.reis.game.event.EventFilter
import com.reis.game.event.EventListener
import com.reis.game.event.EventType
import com.reis.game.graphics.Animation
import com.reis.game.prototypes.AnimationProto.AnimationData

/**
 * Created by berna on 18-Mar-18.
 */
class AnimationComponent(entity: GameEntity, animationData: AnimationData): EntityComponent(entity),
        EventListener {

    private var currentAnimation: Animation? = null

    init {
        Main.getInstance().newEventProcessor.on(EventType.ACTION_STARTED, this)
        Main.getInstance().animationManager.loadAnimations(entity, animationData)
    }

    override fun update(delta: Float) {
        super.update(delta)
        currentAnimation?.update(delta)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        updateAnimation(batch)
    }

    private fun updateAnimation(batch: Batch) {
        val currentAnimation = this.currentAnimation
        if (currentAnimation != null) {
            val texture = currentAnimation.getKeyFrame()
            batch.draw(texture, entity.x + texture.drawOffset.x,
                entity.y + texture.drawOffset.y)
        }
    }

    override val filter: EventFilter = object : EventFilter() {
        override fun test(event: Event): Boolean {
            val originator = event.data as GameEntity
            return originator === entity
        }
    }

    override fun onEvent(event: Event) {
        // TODO change this access to animation manager
        val animationManager = Main.getInstance().animationManager
        var animation = animationManager.getAnimationForAction(entity, event.trigger as EntityAction)
        if (animation == null) {
            animation = animationManager.getAnimationForActionClass(entity, Idle::class)
        }
        currentAnimation = animation
    }
}