package com.arny.core;

import android.content.Context
import android.graphics.Color
import android.support.annotation.ArrayRes
import android.support.annotation.LayoutRes
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog

fun Context.createCustomLayoutDialog(@LayoutRes layout: Int, initView: View.() -> Unit, cancelable: Boolean = true): AlertDialog? {
    if (!checkContextTheme(this)) return null
    val builder = AlertDialog.Builder(this)
    builder.setView(LayoutInflater.from(this).inflate(layout, null, false).apply(initView))
    if (!cancelable) {
        builder.setCancelable(false)
    }
    val dialog = builder.create()
    dialog.show()
    return dialog
}

@JvmOverloads
fun alertDialog(context: Context, title: String, content: String? = null, btnOkText: String? = "OK", btnCancelText: String? = null, cancelable: Boolean = false, onConfirm: () -> Unit?, onCancel: () -> Unit?, alert: Boolean = true, autoDissmiss: Boolean = true, btnNeutralText: String? = null, onNeutral: () -> Unit? = {}): MaterialDialog? {
    if (!checkContextTheme(context)) return null
    val builder = MaterialDialog.Builder(context)
    builder.title(title)
    builder.titleColor(if (alert) Color.RED else Color.BLACK)
    builder.cancelable(cancelable)
    builder.contentColor(Color.BLACK)
    if (btnOkText != null) {
        builder.positiveText(btnOkText)
        builder.onPositive { dialog, _ ->
            if (autoDissmiss) {
                dialog.dismiss()
            }
            onConfirm.invoke()
        }
    }
    if (btnCancelText != null) {
        builder.negativeText(btnCancelText)
        builder.onNegative { dialog, _ ->
            if (autoDissmiss) {
                dialog.dismiss()
            }
            onCancel.invoke()
        }
    }
    if (btnNeutralText != null) {
        builder.neutralText(btnNeutralText)
        builder.onNeutral { dialog, _ ->
            if (autoDissmiss) {
                dialog.dismiss()
            }
            onNeutral.invoke()
        }
    }
    if (!content.isNullOrBlank()) {
        builder.content(fromHtml(content))
    }
    val dlg = builder.build()
    dlg.show()
    return dlg

}

@JvmOverloads
fun listDialog(context: Context, title: String? = null, content: String? = null, items: Array<CharSequence>, cancelable: Boolean = false, onSelect: (position: Int) -> Unit?): MaterialDialog? {
    if (!checkContextTheme(context)) return null
    val builder = MaterialDialog.Builder(context)
    if (!title.isNullOrBlank()) {
        builder.title(title)
    }
    if (!content.isNullOrBlank()) {
        builder.content(content)
    }
    builder.cancelable(cancelable)
            .items(items.toString())
            .itemsCallback { _, _, which, _ -> onSelect.invoke(which) }
            .build()
    val dlg = builder.build()
    dlg.show()
    return dlg
}

@JvmOverloads
fun listDialog(context: Context, title: String? = null, content: String? = null, @ArrayRes items: Int, cancelable: Boolean = false, onSelect: (position: Int) -> Unit?): MaterialDialog? {
    return listDialog(context, title, content, context.resources.getTextArray(items), cancelable, onSelect)
}

@JvmOverloads
fun checkDialog(context: Context, title: String? = null, content: String? = null, items: Array<String>, itemsSelected: Array<Int>? = null, cancelable: Boolean = false, onInput: (selected: Array<Int>) -> Unit?): MaterialDialog? {
    if (!checkContextTheme(context)) return null
    val builder = MaterialDialog.Builder(context)
    if (!title.isNullOrBlank()) {
        builder.title(title)
    }
    if (!content.isNullOrBlank()) {
        builder.content(content)
    }
    builder.title(title.toString())
            .cancelable(cancelable)
            .items(items.asList())
            .itemsCallbackMultiChoice(itemsSelected) { _, which, _ ->
                onInput.invoke(which)
                true
            }
            .positiveText(android.R.string.ok)
    val dlg = builder.build()
    dlg.show()
    return dlg
}

@JvmOverloads
fun inputDialog(context: Context, title: String? = null, hint: String? = null, content: String? = null, prefill: String? = null, btnOkText: String? = null, btnCancelText: String? = null, cancelable: Boolean? = null, inputType: Int? = null, onCancel: () -> Unit?, onInput: (content: CharSequence?) -> Unit?): MaterialDialog? {
    if (!checkContextTheme(context)) return null
    val builder = MaterialDialog.Builder(context)
    if (!title.isNullOrBlank()) {
        builder.title(title)
    }
    if (!content.isNullOrBlank()) {
        builder.content(content)
    }
    builder.cancelable(cancelable ?: false)
            .positiveText(btnOkText ?: "OK")
            .negativeText(btnCancelText ?: "Отмена")
            .inputType(inputType ?: InputType.TYPE_CLASS_TEXT)
            .onNegative { dialog, _ ->
                dialog.dismiss()
                onCancel.invoke()
            }
            .input(hint ?: "", prefill ?: "") { _, input ->
                onInput.invoke(input)
            }
    val dlg = builder.build()
    dlg.show()
    return dlg
}

fun checkContextTheme(context: Context) = context is ContextThemeWrapper