package com.rasalexman.flaircore.ext

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import com.rasalexman.flaircore.BuildConfig
import com.rasalexman.flaircore.interfaces.FacadeInitializer
import com.rasalexman.flaircore.interfaces.IFacade

/**
 * Remove child from parent if it has
 */
fun View.removeFromParent() {
    (this.parent as? ViewGroup)?.removeView(this)
}

/**
 * System function
 */
fun ImageView.clear() {
    this.setImageResource(0)
    this.setImageBitmap(null)
    this.setImageDrawable(null)
    this.setOnClickListener(null)
    this.background = null
}

/**
 * Clear button
 */
fun Button.clear() {
    this.background = null
    this.setOnClickListener(null)
    this.text = null
}

/**
 * Clear TextView
 */
fun TextView.clear() {
    this.text = null
    this.setOnClickListener(null)
    this.setCompoundDrawables(null, null, null, null)
    this.background = null
}

/**
 * System clear view function
 */
fun ViewGroup.clear() {
    var childView: View
    repeat(this.childCount) {
        childView = this.getChildAt(it)
        when (childView) {
            is ViewGroup -> (childView as ViewGroup).clear()
            is ImageView -> (childView as ImageView).clear()
            is Button -> (childView as Button).clear()
            is TextView -> (childView as TextView).clear()
            is CompoundButton -> {
                (childView as CompoundButton).text = null
                (childView as CompoundButton).setOnCheckedChangeListener(null)
            }
        }
    }
}

/**
 * Hide or show view by boolean flag
 */
var android.view.View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

/**
 * Hide view with optional param
 * @param gone
 * Does the view removed from parent (optional true)
 */
fun android.view.View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

/**
 * Show the view
 */
fun android.view.View.show() {
    visibility = View.VISIBLE
}

/**
 * Instantiate new Facade for given core name
 *
 * @param key
 * Core Name, used to get IFacade instance
 *
 * @param block
 * initialization function. This is a starting point to register Proxy/Mediators/Commands
 */
fun Context.flair(key: String = IFacade.DEFAULT_KEY, block: FacadeInitializer? = null): IFacade = IFacade.core(key, this, block)

/**
 * Log any messages with given lambda func
 */
inline fun log(lambda: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d("FLAIR_LOG ------>", lambda())
    }
}