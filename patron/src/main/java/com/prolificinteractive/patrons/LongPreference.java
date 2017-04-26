package com.prolificinteractive.patrons;

import android.content.SharedPreferences;

/**
 * Preference wrapper for {@link Long}.
 */
public class LongPreference extends BasePreference<Long> {
  public LongPreference(final SharedPreferences preferences, final String key) {
    this(preferences, key, 0L);
  }

  public LongPreference(
      final SharedPreferences preferences,
      final String key,
      final long defaultValue) {
    super(preferences, key, defaultValue);
  }

  @Override public Long get() {
    return preferences.getLong(key, defaultValue);
  }

  @Override public void set(final Long value) {
    preferences.edit().putLong(key, value).apply();
  }
}
