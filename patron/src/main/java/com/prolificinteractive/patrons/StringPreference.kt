package com.prolificinteractive.patrons

import android.content.SharedPreferences

/**
 * Preference wrapper for [String].
 */
open class StringPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    defaultValue: String? = null
) : BasePreference<String?>(preferences, key, defaultValue) {

  override fun get(): String? = preferences.getString(key, defaultValue)

  override fun set(value: String?) = when (value) {
    null -> delete()
    else -> preferences.edit().putString(key, value).apply()
  }
}
