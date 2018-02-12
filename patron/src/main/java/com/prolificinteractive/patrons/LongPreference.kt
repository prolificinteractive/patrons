package com.prolificinteractive.patrons

import android.content.SharedPreferences

/**
 * Preference wrapper for [Long].
 */
class LongPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Long = 0L
) : BasePreference<Long>(preferences, key, defaultValue) {

  override fun get() = preferences.getLong(key, defaultValue)

  override fun set(value: Long?) = when (value) {
    null -> delete()
    else -> preferences.edit().putLong(key, value).apply()
  }
}
