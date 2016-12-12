package scott.com.workhard.utils.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import scott.com.workhard.app.App;

/**
 * Created by Julian Cardona on 5/10/16.
 */
public class PreferenceUtils {


    protected static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(App.getGlobalContext());
    }

    protected static SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    private static int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    private static void putInt(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

    private static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    private static void putString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    private static boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    private static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public static void clearPreferences() {

    }

}
