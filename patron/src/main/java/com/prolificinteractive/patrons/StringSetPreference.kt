package com.prolificinteractive.patrons

import android.annotation.TargetApi
import android.content.SharedPreferences
import android.os.Build

/**
 * Preference wrapper for [Set] of [String].
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class StringSetPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    defaultValue: Set<String>? = null
) : BasePreference<Set<String>?>(preferences, key, defaultValue) {

  override fun get(): Set<String>? = preferences.getStringSet(key, defaultValue)

  override fun set(value: Set<String>?) = when (value) {
    null -> delete()
    else -> preferences.edit().putStringSet(key, value).apply()
  }
}
