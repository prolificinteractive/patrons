package com.prolificinteractive.patrons;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import java.util.Set;

/**
 * Preference wrapper for {@link Set<String>}.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StringSetPreference extends BasePreference<Set<String>> {
  public StringSetPreference(final SharedPreferences preferences, final String key) {
    this(preferences, key, null);
  }

  public StringSetPreference(
      final SharedPreferences preferences,
      final String key,
      final Set<String> defaultValue) {
    super(preferences, key, defaultValue);
  }

  @Override public Set<String> get() {
    return preferences.getStringSet(key, defaultValue);
  }

  @Override public void set(final Set<String> value) {
    preferences.edit().putStringSet(key, value).apply();
  }
}
