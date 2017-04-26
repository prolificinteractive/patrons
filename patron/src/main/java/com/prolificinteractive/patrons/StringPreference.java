package com.prolificinteractive.patrons;

import android.content.SharedPreferences;

/**
 * Preference wrapper for {@link String}.
 */
public class StringPreference extends BasePreference<String> {
  public StringPreference(final SharedPreferences preferences, final String key) {
    this(preferences, key, null);
  }

  public StringPreference(
      final SharedPreferences preferences,
      final String key,
      final String defaultValue) {
    super(preferences, key, defaultValue);
  }

  @Override public String get() {
    return preferences.getString(key, defaultValue);
  }

  @Override public void set(final String value) {
    preferences.edit().putString(key, value).apply();
  }
}
