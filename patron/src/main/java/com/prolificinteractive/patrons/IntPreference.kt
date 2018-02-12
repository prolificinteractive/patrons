package com.prolificinteractive.patrons

import android.content.SharedPreferences

/**
 * Preference wrapper for [Integer].
 */
class IntPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Int? = null
) : BasePreference<Int?>(preferences, key, defaultValue) {

  override fun get() = preferences.getInt(key, defaultValue ?: 0)

  override fun set(value: Int?) = when (value) {
    null -> delete()
    else -> preferences.edit().putInt(key, value).apply()
  }
}
