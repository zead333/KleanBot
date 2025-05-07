package dev.pegasus.kleanbot.utilities.extensions

import androidx.core.widget.NestedScrollView

/**
 * Created by: Sohaib Ahmed
 * Date: 5/7/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

fun NestedScrollView.scrollToEnd(animated: Boolean = true) {
    val target = getChildAt(childCount - 1)?.bottom ?: return
    if (animated) {
        smoothScrollTo(0, target)
    } else {
        scrollTo(0, target)
    }
}