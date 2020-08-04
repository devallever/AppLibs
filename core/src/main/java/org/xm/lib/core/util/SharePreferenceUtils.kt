package org.xm.lib.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.xm.lib.core.base.App

@Deprecated("")
@SuppressLint("ApplySharedPref")
object SharePreferenceUtils {

    private val sp = App.context.getSharedPreferences(SPUtils.SP_FILE_NAME, Context.MODE_PRIVATE)

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
sp     * @param value The value of sp.
     */

    fun put(key: String, value: String?, isCommit: Boolean = false) {
        sp.edit(isCommit) {
            putString(key, value)
        }
    }

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or `""` otherwise
     */
    fun getString(key: String): String? {
        return getString(key, "")
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or `defaultValue` otherwise
     */
    fun getString(key: String, defaultValue: String?): String? {
        return sp.getString(key, defaultValue)
    }

    fun put(key: String, value: Int, isCommit: Boolean = false) {
        sp.edit(isCommit) {
            putInt(key, value)
        }
    }

    fun getInt(key: String): Int {
        return getInt(key, -1)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sp.getInt(key, defaultValue)
    }

    fun put(key: String, value: Long, isCommit: Boolean = false) {
        sp.edit(isCommit) {
            putLong(key, value)
        }
    }

    fun getLong(key: String): Long {
        return getLong(key, -1L)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sp.getLong(key, defaultValue)
    }

    fun put(key: String, value: Float, isCommit: Boolean = false) {
        sp.edit(isCommit) {
            putFloat(key, value)
        }
    }

    fun getFloat(key: String): Float {
        return getFloat(key, -1f)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sp.getFloat(key, defaultValue)
    }

    fun put(key: String, value: Boolean, isCommit: Boolean = false) {
        sp.edit(isCommit) {
            putBoolean(key, value)
        }
    }

    fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    fun put(key: String, value: Set<String?>?, isCommit: Boolean = false) {
        sp.edit(isCommit) {
            putStringSet(key, value)
        }
    }

    fun getStringSet(key: String): Set<String>? {
        return getStringSet(key, emptySet<String>())
    }

    fun getStringSet(key: String, defaultValue: Set<String?>?): Set<String>? {
        return sp.getStringSet(key, defaultValue)
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    val all: Map<String, *>
        get() = sp.all

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return `true`: yes<br></br>`false`: no
     */
    operator fun contains(key: String): Boolean {
        return sp.contains(key)
    }
    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    fun remove(key: String, isCommit: Boolean = false) {
        sp.edit(isCommit) {
            remove(key)
        }
    }
    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use [SharedPreferences.Editor.commit],
     * false to use [SharedPreferences.Editor.apply]
     */
    /**
     * Remove all preferences in sp.
     */
    fun clear(isCommit: Boolean = false) {
        sp.edit(isCommit) {
            clear()
        }
    }

    fun isSpace(s: String?): Boolean {
        if (s == null) {
            return true
        }
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}