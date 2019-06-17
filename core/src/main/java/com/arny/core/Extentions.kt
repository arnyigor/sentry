package com.arny.core


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.*
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment?, @IdRes frameId: Int, addToback: Boolean = false, tag: String? = null, onLoadFunc: () -> Unit? = {}, @AnimatorRes @AnimRes enter: Int? = null, @AnimatorRes @AnimRes exit: Int? = null) {
    if (fragment == null) return
    supportFragmentManager.transact({
        replace(frameId, fragment)
        if (addToback) {
            addToBackStack(tag)
        }
        onLoadFunc()
    }, enter, exit)
}

@JvmOverloads
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment?, @IdRes frameId: Int, @AnimatorRes @AnimRes enter: Int? = null, @AnimatorRes @AnimRes exit: Int? = null) {
    if (fragment == null) return
    supportFragmentManager.transact({
        replace(frameId, fragment)
    }, enter, exit)
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit, @AnimatorRes @AnimRes enter: Int? = null, @AnimatorRes @AnimRes exit: Int? = null) {
    val transaction = beginTransaction()
    if (enter != null && exit != null) {
        transaction.setCustomAnimations(enter, exit)
    }
    transaction.apply {
        action()
    }.commit()
}

inline fun <reified T : Any> Activity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Fragment.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {
    val context = this.context
    if (context != null) {
        val intent = newIntent<T>(context)
        intent.init()
        startActivityForResult(intent, requestCode, options)
    }
}

inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

fun Fragment.putExtras(init: Bundle.() -> Unit = {}) {
    val args = Bundle()
    args.init()
    this.arguments = args
}

fun Fragment.putExtras(args: Bundle?) {
    this.arguments = args
}

fun Activity.putExtras(resultCode: Int? = null, init: Intent.() -> Unit = {}) {
    val i = this.intent ?: Intent()
    i.init()
    this.intent = i
    if (resultCode != null) {
        setResult(resultCode, this.intent)
    }
}

fun View?.setVisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}

fun <T> Intent?.getExtra(extraName: String): T? {
    return this?.extras?.get(extraName) as? T
}

fun <T> Activity?.getExtra(extraName: String): T? {
    return this?.intent?.extras?.get(extraName) as? T
}

fun Activity?.removeExtra(extraName: String) {
    this?.intent?.removeExtra(extraName)
}

fun <T> Fragment?.getExtra(extraName: String): T? {
    return this?.arguments?.get(extraName) as? T
}

fun <T> Bundle?.getExtra(extraName: String): T? {
    return this?.get(extraName) as? T
}

fun Context?.color(@ColorRes res: Int): Int? {
    return this?.let { ContextCompat.getColor(it, res) }
}

fun Context?.string(@StringRes res: Int): String? {
    return this?.resources?.getString(res)
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment?, tag: String, @AnimatorRes @AnimRes enter: Int? = null, @AnimatorRes @AnimRes exit: Int? = null) {
    if (fragment == null) return
    supportFragmentManager.transact({
        add(fragment, tag)
    }, enter, exit)
}


fun AppCompatActivity.replaceFragmentInActivity(
        fragment: Fragment, @IdRes frameId: Int,
        addToback: Boolean = false,
        tag: String? = null,
        onLoadFunc: () -> Unit? = {},
        animResourses: Pair<Int, Int>? = null
) {
    val tg = tag ?: fragment.javaClass.simpleName
    supportFragmentManager.transact {
        if (animResourses != null) {
            val slideIn = animResourses.first
            val slideOut = animResourses.second
            setCustomAnimations(slideIn, slideOut)
        }
        replace(frameId, fragment, tg)
        if (addToback) {
            addToBackStack(tag)
        }
    }
    onLoadFunc()
}


fun AppCompatActivity.popBackStack(immadiate: Boolean = true) {
    if (immadiate) {
        supportFragmentManager.popBackStackImmediate()
    } else {
        supportFragmentManager.popBackStack()
    }
}

fun AppCompatActivity.getFragment(position: Int): Fragment? {
    return supportFragmentManager.fragments.getOrNull(position)
}

fun AppCompatActivity.fragmentBackStackCnt(): Int {
    return supportFragmentManager.backStackEntryCount
}

fun AppCompatActivity.fragmentsBackStack(): Fragment {
    val fragments = supportFragmentManager.fragments
    val size = fragments.size
    val fragment = fragments.get(size - 1)
    return fragment
}

fun AppCompatActivity.fragmentBackStackClear() {
    return supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
}

fun AppCompatActivity.getFragmentInContainer(@IdRes containerId: Int): Fragment? {
    return supportFragmentManager.findFragmentById(containerId)
}

fun AppCompatActivity.getFragmentByTag(tag: String): Fragment? {
    return supportFragmentManager.findFragmentByTag(tag)
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: (ActionBar?.() -> Unit)? = null) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action?.let { it() }
    }
}

fun AppCompatActivity.setupActionBar(toolbar: Toolbar?, action: (ActionBar?.() -> Unit)? = null) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        action?.let { it() }
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}