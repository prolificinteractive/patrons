package com.prolificinteractive.patrons;

import android.content.SharedPreferences;

/**
 * Preference wrapper for {@link Integer}.
 */
public class IntPreference extends BasePreference<Integer> {
  public IntPreference(final SharedPreferences preferences, final String key) {
    this(preferences, key, 0);
  }

  public IntPreference(
      final SharedPreferences preferences,
      final String key,
      final int defaultValue) {
    super(preferences, key, defaultValue);
  }

  @Override public Integer get() {
    return preferences.getInt(key, defaultValue);
  }

  @Override public void set(final Integer value) {
    preferences.edit().putInt(key, value).apply();
  }
}
