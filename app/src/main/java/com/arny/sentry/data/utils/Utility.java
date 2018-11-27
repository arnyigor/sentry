package com.arny.sentry.data.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	public static boolean matcher(String regex, String string) {
		return Pattern.matches(regex, string);
	}

	public static String match(String where, String pattern, int groupnum) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(where);
		while (m.find()) {
			if (!m.group(groupnum).equals("")) {
				return m.group(groupnum);
			}
		}
		return null;
	}

	public static String readAssetFile(Context context, String folder, String fileName) {
		InputStream input;
		try {
			input = context.getAssets().open(folder + "/" + fileName);
			int size = input.available();
			byte[] buffer = new byte[size];
			input.read(buffer);
			input.close();
			return new String(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> boolean contains(ArrayList<T> array, T v) {
		for (T e : array) {
			if (v.equals(e)) {
				return true;
			}
		}
		return false;
	}

	public static void iterHashMap(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
		}
	}

	public static String stringContains(String where, String[] cases, String[] answers) {
		for (int i = 0; i < cases.length; i++) {
			if (where.contains(cases[i])) return answers[i];
		}
		return where;
	}

	public static <T> boolean contains(final T[] array, T v) {
		for (T e : array) {
			if (v.equals(e)) {
				return true;
			}
		}
		return false;
	}

	public static void setJsonParam(JSONObject params, String col, Object val) {
		try {
			params.put(col, val);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static <V, T> ArrayList<T> getValuesFromMap(HashMap<V, T> hashMap) {
		ArrayList<T> list = new ArrayList<>();
		for (Map.Entry<V, T> entry : hashMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	public static <V, T> ArrayList<V> getKeysFromMap(HashMap<V, T> map) {
		ArrayList<V> list = new ArrayList<>();
		for (Map.Entry<V, T> entry : map.entrySet()) {
			list.add(entry.getKey());
		}
		return list;
	}

	public static Boolean getFullEquals(Object parent, Object other) {
		if (other == null) return false;
		if (!other.getClass().getSimpleName().equals(parent.getClass().getSimpleName())) return false;
		Collection<Field> fieldsP = getFields(parent.getClass());
		if (fieldsP.size() != getFields(other.getClass()).size()) return false;
		for (Field fieldP : fieldsP) {
			fieldP.setAccessible(true);
			try {
				Object o = fieldP.get(parent);
				Object o2 = fieldP.get(other);
				boolean equals = o.equals(o2);
				if (!equals) return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			fieldP.setAccessible(false);
		}
		return true;
	}

	public static String getFields(Object o) {
		Collection<Field> fields = getFields(o.getClass());
		StringBuilder builder = new StringBuilder();
		builder.append(o.getClass().getSimpleName()).append("(");
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				String msg = field.getName() + ":'" + field.get(o) + "'(" + field.getType().getSimpleName() + ");";
				builder.append(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			field.setAccessible(false);
		}
		builder.append(") \n");
		return builder.toString();
	}

	/**
	 * Get all fields of a class.
	 *
	 * @param clazz The class.
	 * @return All fields of a class.
	 */
	public static Collection<Field> getFields(Class<?> clazz) {
		Map<String, Field> fields = new HashMap<>();
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (!field.getName().equalsIgnoreCase("shadow$_klass_") && !field.getName().equalsIgnoreCase("serialVersionUID") && !field.getName().equalsIgnoreCase("$change") && !field.getName().equalsIgnoreCase("shadow$_monitor_")) {
					if (!fields.containsKey(field.getName())) {
						fields.put(field.getName(), field);
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return fields.values();
	}

	public static String getFields(Object cls, String[] include) {
		Collection<Field> fields = getFields(cls.getClass());
		StringBuilder builder = new StringBuilder();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				String msg = "\n" + field.getName() + ":" + field.get(cls) + "; ";
				for (String s : include) {
					if (s.equalsIgnoreCase(field.getName())) {
						builder.append(msg);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			field.setAccessible(false);
		}
		return builder.toString();
	}

	public static String dumpCursor(Cursor cursor) {
		return DatabaseUtils.dumpCursorToString(cursor);
	}

	public static boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			return !(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable());
		}
		return false;
	}

	public static synchronized boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (manager != null) {
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
				String className = service.service.getClassName();
				if (serviceClass.getName().equals(className)) {
					return true;
				}
			}
		}
		return false;
	}

	@NonNull
	public static <T> ArrayList<T> getListCopy(List<T> list) {
		ArrayList<T> arrayList = new ArrayList<>();
		arrayList.addAll(list);
		ArrayList<T> listCopy = new ArrayList<>(arrayList.size());
		listCopy.addAll((ArrayList<T>) arrayList.clone());
		Collections.copy(listCopy, list);
		return listCopy;
	}

	public static String listToString(List<String> strings) {
		StringBuilder res = new StringBuilder();
		boolean first = true;
		for (String s : strings) {
			if (!first) {
				res.append(",");
				first = false;
			}
			res.append(s);
		}
		return res.toString();
	}

	public static <T> String objectsListToString(List<T> tList) {
		StringBuilder res = new StringBuilder();
		for (T s : tList) {
			res.append(s.toString());
            res.append(",");
        }
        res.delete(res.length() - 1, res.length());
        return res.toString();
	}

	@NonNull
	public static <T> ArrayList<T> getExcludeList(ArrayList<T> list, List<T> items, Comparator<T> comparator) {
		ArrayList<T> res = new ArrayList<>();
		for (T t : list) {
			int pos = Collections.binarySearch(items, t, comparator);
			if (pos < 0) {
				res.add(t);
			}
		}
		return res;
	}

	public static String getThread() {
		return Thread.currentThread().getName();
	}

	public static void hideSoftKeyboard(Context context) {
		InputMethodManager systemService = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (systemService != null) {
			systemService.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
		}
	}

	public static void showSoftKeyboard(Context context) {
		InputMethodManager systemService = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (systemService != null) {
			systemService.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
		}
	}

	private void hideSystemUI(Activity activity) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    // Shows the system bars by removing all the flags

	// except for the ones that make the content appear under the system bars.
    private void showSystemUI(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
