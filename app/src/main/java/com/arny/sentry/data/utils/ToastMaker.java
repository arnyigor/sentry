package com.arny.sentry.data.utils;

import android.content.Context;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class ToastMaker {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toastError(final Context context, final String message) {
        Toasty.error(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toastInfo(Context context, String message) {
        Toasty.info(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toastSuccess(final Context context, final String message) {
        Toasty.success(context, message, Toast.LENGTH_LONG).show();
    }

}