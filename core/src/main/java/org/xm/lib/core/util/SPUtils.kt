package org.xm.lib.core.util

import android.content.Context
import org.xm.lib.core.base.App

/**
 * SharedPreferences 工具类
 */
object SPUtils {

    const val SP_FILE_NAME = "sp"

    private val sp = App.context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String) = sp.getString(key, defValue)!!

    fun putBoolean(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean) = sp.getBoolean(key, defValue)

    fun putInt(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, value: Int) = sp.getInt(key, value)

    fun putLong(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, value: Long) = sp.getLong(key, value)

    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    /**
     * 获取 SharedPref 中所有数据集合
     *
     * @return 保存的所有数据集合
     */
    fun getAll(): Map<String, *> = sp.all
}