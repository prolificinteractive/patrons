package com.prolificinteractive.patrons

import android.content.SharedPreferences

/**
 * Preference wrapper for [Boolean].
 */
class BooleanPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Boolean = false
) : BasePreference<Boolean>(preferences, key, defaultValue) {

  override fun get() = preferences.getBoolean(key, defaultValue)

  override fun set(value: Boolean?) = when (value) {
    null -> delete()
    else -> preferences.edit().putBoolean(key, value).apply()
  }
}
