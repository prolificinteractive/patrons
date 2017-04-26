package com.prolificinteractive.patrons

import android.content.SharedPreferences

/**
 * The type Base preference.
 *
 * @param <T> the type parameter hold by the shared preference.
 */
/**
 * Instantiates a new Base preference.
 *
 * @param preferences the shared preferences
 * @param key the key
 * @param defaultValue the default value
 */
abstract class BasePreference<T>(
    /**
     * The shared preferences.
     */
    protected val preferences: SharedPreferences,
    /**
     * The shared preference Key.
     */
    protected val key: String,
    /**
     * The Default value.
     */
    protected val defaultValue: T
) : Preference<T> {

  override fun delete() = preferences.edit().remove(key).apply()
  override fun isSet() = preferences.contains(key)
}