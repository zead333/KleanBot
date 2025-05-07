package dev.pegasus.kleanbot.utilities

import android.os.Handler
import android.os.Looper
import android.widget.TextView

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

class TypeWriter(private val textView: TextView) {

    private var textToDisplay: CharSequence = ""
    private var index = 0
    private var delay: Long = 100 // milliseconds (speed per character)
    private val handler = Handler(Looper.getMainLooper())

    private val characterAdder = object : Runnable {
        override fun run() {
            if (index <= textToDisplay.length) {
                textView.text = textToDisplay.subSequence(0, index)
                index++
                handler.postDelayed(this, delay)
            }
        }
    }

    fun animateText(text: CharSequence, characterDelay: Long = 100) {
        textToDisplay = text
        index = 0
        delay = characterDelay
        textView.text = ""
        handler.removeCallbacks(characterAdder)
        handler.postDelayed(characterAdder, delay)
    }

    fun stop() {
        handler.removeCallbacks(characterAdder)
    }
}