package com.reis.game.scene.dialog

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.StringBuilder
import com.reis.game.Main
import com.reis.game.contants.DialogConstants.DIALOG_EXTERNAL_LEFT_PADDING
import com.reis.game.contants.DialogConstants.DIALOG_EXTERNAL_RIGHT_PADDING
import com.reis.game.contants.DialogConstants.DIALOG_EXTERNAL_TOP_PADDING
import com.reis.game.contants.DialogConstants.DIALOG_INTERNAL_PADDING
import com.reis.game.entity.GameEntity
import java.util.ArrayList

/**
 * Created by bernardoreis on 2/14/18.
 */
class DialogWindow(text: String, options: DialogOptions = DialogOptions(),
        val entity: GameEntity): Table() {

    private val content: Label
    private var lines: MutableList<CharSequence> = ArrayList()
    private var lastLineIndex: Int = 0
    private var expectedWidth: Int = options.width
    private var expectedHeight: Int = options.height

    init {
        skin = options.skin
        content = Label(text, skin)
        createDialog(text, options)
    }

    private fun createDialog(text: String, options: DialogOptions) {
        content.setWrap(true)
        content.setAlignment(options.verticalAlignment or options.horizontalAlignment)

        val availableWidth = calcAvailableWidth()
        val availableHeight = calcAvailableHeight()
        val font = content.style.font
        lines = FontUtils.splitLines(font, text, lines, AutoWrap.WORDS, availableWidth)

        calcDialogPosition()
        background = skin.getDrawable("background")
        defaults().pad(
                DIALOG_EXTERNAL_TOP_PADDING.toFloat(),
                DIALOG_EXTERNAL_LEFT_PADDING.toFloat(),
                DIALOG_EXTERNAL_TOP_PADDING.toFloat(),
                DIALOG_EXTERNAL_RIGHT_PADDING.toFloat()
        )

        val contentTable = Table(skin)
        contentTable.defaults().pad(10f).space(0f)
        add<Table>(contentTable).expand().fill()
        contentTable.add(content)
                .width(availableWidth)
                .height(availableHeight)
    }

    fun show() {
        showNextScreen()
        // TODO change this out of main
        Main.getInstance().addDialog(this)
    }

    fun update(): Boolean {
        val hasMoreScreens = hasMoreScreens()
        if (hasMoreScreens) {
            showNextScreen()
        } else {
            onDialogEnded()
        }
        return !hasMoreScreens
    }

    private fun onDialogEnded() {
        // TODO: change way of obtaining dialog manager instance
        Main.getInstance().dialogManager.onDialogEnded(this)
        remove()
    }

    private fun hasMoreScreens(): Boolean {
        return this.lastLineIndex < this.lines.size - 1
    }

    private fun showNextScreen() {
        val font = content.style.font
        val contentToDisplay = getNextLines(font, 0f)
        content.setText(contentToDisplay)

        this.invalidateHierarchy()
        this.invalidate()
        this.layout()
        this.pack()
    }

    private fun getNextLines(font: BitmapFont, leading: Float): String {
        val lineHeight = font.lineHeight
        var totalHeight = 0f
        var counter = lastLineIndex
        for (i in lastLineIndex until this.lines.size) {
            if (!availableSpaceForNextLine(font, leading, totalHeight)) {
                break
            }
            totalHeight += if (i == 0) lineHeight else lineHeight + leading
            counter++
        }
        val linesToShow = ArrayList(this.lines.subList(lastLineIndex, counter))
        val contentToShow = StringBuilder()
        for (line in linesToShow) {
            contentToShow.append(line)
        }
        this.lastLineIndex += linesToShow.size
        return contentToShow.toString()
    }

    private fun availableSpaceForNextLine(font: BitmapFont, leading: Float, totalHeight: Float): Boolean {
        return (totalHeight + font.lineHeight
                + leading) <= calcAvailableHeight()
    }

    private fun calcAvailableWidth(): Float {
        return (expectedWidth - 2 * DIALOG_INTERNAL_PADDING).toFloat()
    }

    private fun calcAvailableHeight(): Float {
        return (expectedHeight - 2 * DIALOG_INTERNAL_PADDING).toFloat()
    }

    private fun calcDialogPosition() {
        val width = Main.getInstance().scene.getWidth()
        val x = (width - expectedWidth) * 0.5f
        this.setPosition(x, 0f)
    }
}