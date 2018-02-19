package com.reis.game.scene.dialog

import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 2/14/18.
 */
class DialogManager {
    private val currentDialogWindowsForEntity: MutableMap<GameEntity, DialogWindow> = HashMap()
    private val dialogsForEntity: MutableMap<Int, List<String>> = loadMockDialogs()
    private val listenersForEntity: MutableMap<GameEntity, HashSet<DialogListener>> = HashMap()

    fun loadDialogs() {
        loadMockDialogs()
    }

    fun showDialogForEntity(entity: GameEntity, interactingWith: GameEntity): DialogWindow? {
        val content = getDialogContentForEntity(interactingWith)
        content ?: return null
        return createDialog(entity, content)
    }

    private fun getDialogContentForEntity(interactingWith: GameEntity): String? {
        val dialogs = dialogsForEntity[interactingWith.id]
        return dialogs?.first()
    }

    private fun createDialog(entity: GameEntity, content: String): DialogWindow {
        val dialog = DialogWindow(content, DialogOptions(), entity)
        currentDialogWindowsForEntity[entity] = dialog
        listenersForEntity[entity]?.forEach { it.onDialogStarted(dialog) }
        dialog.show()
        return dialog
    }

    fun getDialogForEntity(entity: GameEntity): DialogWindow? {
        return currentDialogWindowsForEntity[entity]
    }

    fun hasDialogs(entity: GameEntity): Boolean {
        return currentDialogWindowsForEntity[entity] != null
    }

    fun onDialogEnded(dialog: DialogWindow) {
        currentDialogWindowsForEntity.remove(dialog.entity)
        listenersForEntity[dialog.entity]?.forEach { it.onDialogEnded(dialog) }
    }

    fun registerListener(entity: GameEntity, listener: DialogListener) {
        val listeners = getListenersForEntity(entity)
        listeners.add(listener)
        listenersForEntity[entity] = listeners
    }

    fun removeListener(entity: GameEntity, listener: DialogListener) {
        val listeners = getListenersForEntity(entity)
        listeners.remove(listener)
        listenersForEntity[entity] = listeners
    }

    private fun getListenersForEntity(entity: GameEntity): HashSet<DialogListener> {
        var listeners = listenersForEntity[entity]
        if (listeners == null) {
            listeners = HashSet()
        }
        return listeners
    }

    // TODO properly load dialogs
    private fun loadMockDialogs(): MutableMap<Int, List<String>> {
        val map = HashMap<Int, List<String>>()
        val dialogs = ArrayList<String>()
        dialogs.add("Hello world")
        map[4] = dialogs
        return map
    }
}