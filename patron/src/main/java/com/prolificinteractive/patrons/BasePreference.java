package com.prolificinteractive.patrons;

import android.content.SharedPreferences;

/**
 * The type Base preference.
 *
 * @param <T> the type parameter hold by the shared preference.
 */
public abstract class BasePreference<T> implements Preference<T> {
  /**
   * The shared preferences.
   */
  protected final SharedPreferences preferences;

  /**
   * The shared preference Key.
   */
  protected final String key;

  /**
   * The Default value.
   */
  protected final T defaultValue;

  /**
   * Instantiates a new Base preference.
   *
   * @param preferences the shared preferences
   * @param key the key
   */
  public BasePreference(final SharedPreferences preferences, final String key) {
    this(preferences, key, null);
  }

  /**
   * Instantiates a new Base preference.
   *
   * @param preferences the shared preferences
   * @param key the key
   * @param defaultValue the default value
   */
  public BasePreference(
      final SharedPreferences preferences,
      final String key,
      final T defaultValue) {
    this.preferences = preferences;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  @Override public void delete() {
    preferences.edit().remove(key).apply();
  }

  @Override public boolean isSet() {
    return preferences.contains(key);
  }
}
