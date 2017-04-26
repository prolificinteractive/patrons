package com.prolificinteractive.patrons;

import android.content.SharedPreferences;

/**
 * Preference wrapper for {@link Boolean}.
 */
public class BooleanPreference extends BasePreference<Boolean> {
  public BooleanPreference(final SharedPreferences preferences, final String key) {
    this(preferences, key, false);
  }

  public BooleanPreference(
      final SharedPreferences preferences,
      final String key,
      final Boolean defaultValue) {
    super(preferences, key, defaultValue);
  }

  @Override public Boolean get() {
    return preferences.getBoolean(key, defaultValue);
  }

  @Override public void set(final Boolean value) {
    preferences.edit().putBoolean(key, value).apply();
  }
}
