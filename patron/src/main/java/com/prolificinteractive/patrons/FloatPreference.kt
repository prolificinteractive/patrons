package com.prolificinteractive.patrons

import android.content.SharedPreferences

/**
 * Preference wrapper for [Float].
 */
class FloatPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Float = 0f
) : BasePreference<Float>(preferences, key, defaultValue) {

  override fun get() = preferences.getFloat(key, defaultValue)

  override fun set(value: Float?) = when (value) {
    null -> delete()
    else -> preferences.edit().putFloat(key, value).apply()
  }
}
