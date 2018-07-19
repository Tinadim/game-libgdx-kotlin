package com.reis.game.entity.ai.actions

import com.badlogic.gdx.utils.Timer
import com.reis.game.Main
import com.reis.game.contants.ActionConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.EntityControllerComponent
import com.reis.game.scene.dialog.DialogListener
import com.reis.game.scene.dialog.DialogWindow
import com.reis.game.state.events.Event
import com.reis.game.state.events.EventEmitter
import com.reis.game.state.events.EventType

/**
 * Created by bernardoreis on 2/11/18.
 */
class Interaction(private val entity: GameEntity, private val interactingWith: GameEntity):
        EntityAction(ActionConstants.INTERACTION_PRIORITY), EventEmitter, DialogListener {

    private var interaction: InteractWith = InteractWith(this)

    override fun onStart(entity: GameEntity) {
        super.onStart(entity)
        interaction.startInteraction(interactingWith)
    }

    override fun onComplete(entity: GameEntity) {
        super.onComplete(entity)
        interaction.endInteraction()
    }

    override fun onStop(entity: GameEntity) {
        super.onStop(entity)
        interaction.endInteraction()
    }

    override fun onDialogStarted(dialog: DialogWindow) {}

    override fun onDialogEnded(dialog: DialogWindow) {
        this.complete(entity)
    }

    private fun startInteraction() {
        emit(Event(EventType.ENTITY_INTERACTION, interactingWith))
        adjustTargetOrientation(entity, interactingWith)
        // TODO change the way to obtain the dialog manager instance
        val dialogManager = Main.getInstance().dialogManager
        dialogManager.registerListener(entity, this)
        dialogManager.showDialogForEntity(entity, interactingWith)
    }

    private fun endInteraction() {
        finish()
    }

    private fun adjustTargetOrientation(entity: GameEntity, interactingWith: GameEntity) {
        val orientation = (entity.orientation + 2) % 4
        interactingWith.orientation = orientation
    }

    private class InteractWith(private val baseInteraction: Interaction):
            EntityAction(ActionConstants.INTERACTION_PRIORITY) {
        companion object {
            const val MAX_WAIT: Float = 0.1f
        }

        private var timedOut = false
        private var task: Timer.Task? = null

        fun startInteraction(entity: GameEntity) {
            entity.addAction(this)
            if (entity.hasComponent(EntityControllerComponent::class.java)) {
                startTimer()
            }
        }

        fun endInteraction() {
            finish()
        }

        private fun startTimer() {
            // TODO this feels a bit overkill
            task = Timer.schedule(object: Timer.Task() {
                override fun run() {
                    this@InteractWith.timedOut = true
                    if (!this@InteractWith.hasStarted) {
                        this@InteractWith.finish()
                        this@InteractWith.baseInteraction.endInteraction()
                    }
                }
            }, MAX_WAIT)
        }

        override fun onStart(entity: GameEntity) {
            if (!timedOut) {
                task?.cancel()
                super.onStart(entity)
                baseInteraction.startInteraction()
            }
        }
    }
}