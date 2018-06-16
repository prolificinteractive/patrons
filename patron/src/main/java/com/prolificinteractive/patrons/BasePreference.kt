package com.prolificinteractive.patrons

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener

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

  private val listeners: MutableMap<OnPreferenceChangeListener<T?>, OnSharedPreferenceChangeListener> = mutableMapOf()

  override fun delete() = preferences.edit().remove(key).apply()

  override fun isSet() = preferences.contains(key)

  override fun registerChangeListener(listener: OnPreferenceChangeListener<T?>) {
    val l = OnSharedPreferenceChangeListener { _, _ -> listener(get()) }
    preferences.registerOnSharedPreferenceChangeListener(l)
    listeners[listener] = l
  }

  override fun unregisterChangeListener(listener: OnPreferenceChangeListener<T?>) {
    val l = listeners[listener]
    preferences.unregisterOnSharedPreferenceChangeListener(l)
    listeners.remove(listener)
  }

  override fun unregisterAllChangeListener() {
    listeners.forEach { preferences.unregisterOnSharedPreferenceChangeListener(it.value) }
    listeners.clear()
  }
}