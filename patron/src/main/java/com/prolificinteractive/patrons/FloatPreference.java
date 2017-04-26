package com.prolificinteractive.patrons;

import android.content.SharedPreferences;

/**
 * Preference wrapper for {@link Float}.
 */
public class FloatPreference extends BasePreference<Float> {
  public FloatPreference(final SharedPreferences preferences, final String key) {
    this(preferences, key, 0);
  }

  public FloatPreference(
      final SharedPreferences preferences,
      final String key,
      final float defaultValue) {
    super(preferences, key, defaultValue);
  }

  @Override public Float get() {
    return preferences.getFloat(key, defaultValue);
  }

  @Override public void set(final Float value) {
    preferences.edit().putFloat(key, value).apply();
  }
}
