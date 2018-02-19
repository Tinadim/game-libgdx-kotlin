package com.reis.game.scene.dialog

import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Imported and adapted from AndEngine.
 */

object FontUtils {

    private const val UNSPECIFIED = -1

    fun measureText(font: BitmapFont, text: CharSequence): Float {
        return FontUtils.measureText(font, text, null)
    }

    fun measureText(font: BitmapFont, text: CharSequence, start: Int, end: Int): Float {
        return FontUtils.measureText(font, text, start, end, null)
    }

    fun measureText(font: BitmapFont, text: CharSequence, widths: FloatArray?): Float {
        return FontUtils.measureText(font, text, 0, text.length, widths)
    }

    fun measureText(font: BitmapFont, text: CharSequence, start: Int, end: Int, widths: FloatArray?): Float {
        val textLength = end - start
        /* Early exits. */
        if (start == end) {
            return 0f
        } else if (textLength == 1) {
            return font.data.getGlyph(text[start]).width.toFloat()
        }

        var previousLetter: BitmapFont.Glyph? = null
        var width = 0f
        var pos = start
        var i = 0
        while (pos < end) {
            val letter = font.data.getGlyph(text[pos])
            if (previousLetter != null) {
                width += previousLetter.getKerning(letter.id.toChar()).toFloat()
            }
            previousLetter = letter

            /* Check if this is the last character. */
            width += if (pos == end - 1) (letter.xoffset + letter.width).toFloat() else letter.xadvance.toFloat()

            if (widths != null) {
                widths[i] = width
            }
            pos++
            i++
        }
        return width
    }

    fun <L : MutableList<CharSequence>> splitLines(font: BitmapFont, text: CharSequence, result: L,
            autoWrap: AutoWrap, autoWrapWidth: Float): L {
        /**
         * TODO In order to respect already existing linebreaks, [FontUtils.splitLines]
         * could be leveraged and than the following methods could be called for each line.
         */
        return when (autoWrap) {
            AutoWrap.LETTERS -> FontUtils.splitLinesByLetters(font, text, result, autoWrapWidth)
            AutoWrap.WORDS -> FontUtils.splitLinesByWords(font, text, result, autoWrapWidth)
            AutoWrap.CJK -> FontUtils.splitLinesByCJK(font, text, result, autoWrapWidth)
            AutoWrap.NONE -> throw IllegalArgumentException("Unexpected " +
                    AutoWrap::class.java.simpleName + ": '" + autoWrap + "'.")
        }
    }

    private fun <L : MutableList<CharSequence>> splitLinesByLetters(font: BitmapFont,
            text: CharSequence, result: L, autoWrapWidth: Float): L {
        val textLength = text.length

        var lineStart = 0
        var lineEnd = 0
        var lastNonWhitespace = 0
        var charsAvailable = false

        var i = 0
        while (i < textLength) {
            val character = text[i]
            if (character != ' ') {
                if (charsAvailable) {
                    lastNonWhitespace = i + 1
                } else {
                    charsAvailable = true
                    lineStart = i
                    lastNonWhitespace = lineStart + 1
                    lineEnd = lastNonWhitespace
                }
            }

            if (charsAvailable) {
                /* Just for debugging. */
                // final CharSequence line = pText.subSequence(lineStart, lineEnd);
                // final float lineWidth = FontUtils.measureText(pFont, pText, lineStart, lineEnd);
                // final CharSequence lookaheadLine = pText.subSequence(lineStart, lastNonWhitespace);
                val lookaheadLineWidth = FontUtils.measureText(font, text, lineStart, lastNonWhitespace)

                val isEndReached = i == textLength - 1
                if (isEndReached) {
                    /* When the end of the string is reached, add remainder to result. */
                    if (lookaheadLineWidth <= autoWrapWidth) {
                        result.add(text.subSequence(lineStart, lastNonWhitespace))
                    } else {
                        result.add(text.subSequence(lineStart, lineEnd))
                        /* Avoid special case where last line is added twice. */
                        if (lineStart != i) {
                            result.add(text.subSequence(i, lastNonWhitespace))
                        }
                    }
                } else {
                    if (lookaheadLineWidth <= autoWrapWidth) {
                        lineEnd = lastNonWhitespace
                    } else {
                        result.add(text.subSequence(lineStart, lineEnd))
                        i = lineEnd - 1
                        charsAvailable = false
                    }
                }
            }
            i++
        }

        return result
    }

    private fun <L : MutableList<CharSequence>> splitLinesByWords(font: BitmapFont,
            text: CharSequence, result: L, autoWrapWidth: Float): L {
        val textLength = text.length
        if (textLength == 0) {
            return result
        }

        val spaceWidth = font.data.getGlyph(' ').xadvance.toFloat()

        var lastWordEnd = FontUtils.UNSPECIFIED
        var lineStart = FontUtils.UNSPECIFIED
        var lineEnd = FontUtils.UNSPECIFIED

        var lineWidthRemaining = autoWrapWidth
        var firstWordInLine = true
        var i = 0
        while (i < textLength) {
            var spacesSkipped = 0
            /* Find next word. */
            run {
                /* Skip whitespaces. */ while (i < textLength && text[i] == ' ') {
                i++
                spacesSkipped++
            }
            }
            val wordStart = i

            /* Mark beginning of a new line. */
            if (lineStart == FontUtils.UNSPECIFIED) {
                lineStart = wordStart
            }

            run {
                /* Skip non-whitespaces. */ while (i < textLength && text[i] != ' ') {
                i++
            }
            }
            val wordEnd = i

            /* Nothing more could be read. */
            if (wordStart == wordEnd) {
                if (!firstWordInLine) {
                    result.add(text.subSequence(lineStart, lineEnd))
                }
                break
            }

            /* Just for debugging. */
            // final CharSequence word = pText.subSequence(wordStart, wordEnd);

            val wordWidth = FontUtils.measureText(font, text, wordStart, wordEnd)

            /* Determine the width actually needed for the current word. */
            val widthNeeded = if (firstWordInLine) wordWidth
                else spacesSkipped * spaceWidth + wordWidth

            /* Check if the word fits into the rest of the line. */
            if (widthNeeded <= lineWidthRemaining) {
                if (firstWordInLine) {
                    firstWordInLine = false
                } else {
                    lineWidthRemaining -= FontUtils.getAdvanceCorrection(font, text, lastWordEnd - 1)
                }
                lineWidthRemaining -= widthNeeded
                lastWordEnd = wordEnd
                lineEnd = wordEnd

                /* Check if the end was reached. */
                if (wordEnd == textLength) {
                    result.add(text.subSequence(lineStart, lineEnd))
                    /* Added the last line. */
                    break
                }
            } else {
                /* Special case for lines with only one word. */
                if (firstWordInLine) {
                    /* Check for lines that are just too big. */
                    if (wordWidth >= autoWrapWidth) {
                        result.add(text.subSequence(wordStart, wordEnd))
                        lineWidthRemaining = autoWrapWidth
                    } else {
                        lineWidthRemaining = autoWrapWidth - wordWidth

                        /* Check if the end was reached. */
                        if (wordEnd == textLength) {
                            result.add(text.subSequence(wordStart, wordEnd))
                            /* Added the last line. */
                            break
                        }
                    }

                    /* Start a completely new line. */
                    firstWordInLine = true
                    lastWordEnd = FontUtils.UNSPECIFIED
                    lineStart = FontUtils.UNSPECIFIED
                    lineEnd = FontUtils.UNSPECIFIED
                } else {
                    /* Finish the current line. */
                    result.add(text.subSequence(lineStart, lineEnd))

                    /* Check if the end was reached. */
                    if (wordEnd == textLength) {
                        /* Add the last word. */
                        result.add(text.subSequence(wordStart, wordEnd)) // TODO Does this cover all cases?
                        break
                    } else {
                        /* Start a new line, carrying over the current word. */
                        lineWidthRemaining = autoWrapWidth - wordWidth
                        firstWordInLine = false
                        lastWordEnd = wordEnd
                        lineStart = wordStart
                        lineEnd = wordEnd
                    }
                }
            }
        }
        return result
    }

    private fun <L : MutableList<CharSequence>> splitLinesByCJK(font: BitmapFont,
        text: CharSequence, result: L, autoWrapWidth: Float): L {
        val textLength = text.length
        var lineStart = 0
        var lineEnd = 0

        /* Skip whitespaces at the beginning of the string. */
        while (lineStart < textLength && text[lineStart] == ' ') {
            lineStart++
            lineEnd++
        }

        var i = lineEnd
        while (i < textLength) {
            lineStart = lineEnd

            run {
                /* Look for a sub string */
                var charsAvailable = true
                while (i < textLength) {

                    run label@ {
                        /* Skip whitespaces at the end of the string */
                        var j = lineEnd
                        while (j < textLength) {
                            if (text[j] == ' ') {
                                j++
                            } else {
                                break
                            }
                        }
                        if (j == textLength) {
                            if (lineStart == lineEnd) {
                                charsAvailable = false
                            }
                            i = textLength
                            return@label
                        }
                    }

                    lineEnd++

                    val lineWidth = FontUtils.measureText(font, text, lineStart, lineEnd)

                    if (lineWidth > autoWrapWidth) {
                        if (lineStart < lineEnd - 1) {
                            lineEnd--
                        }

                        result.add(text.subSequence(lineStart, lineEnd))
                        charsAvailable = false
                        i = lineEnd
                        break
                    }
                    i = lineEnd
                }

                if (charsAvailable) {
                    result.add(text.subSequence(lineStart, lineEnd))
                }
            }
        }

        return result
    }

    private fun getAdvanceCorrection(pFont: BitmapFont, pText: CharSequence, pIndex: Int): Float {
        val lastWordLastLetter = pFont.data.getGlyph(pText[pIndex])
        return (-(lastWordLastLetter.xoffset + lastWordLastLetter.width) + lastWordLastLetter.xadvance).toFloat()
    }
}
