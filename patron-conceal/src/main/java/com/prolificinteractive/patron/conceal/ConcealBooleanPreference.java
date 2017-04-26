package com.prolificinteractive.patron.conceal;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;

public class ConcealBooleanPreference extends BaseConcealPreference<Boolean> {

  public ConcealBooleanPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key) {
    this(crypto, preferences, key, false);
  }

  public ConcealBooleanPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key,
      final boolean defaultValue) {
    super(crypto, preferences, key, defaultValue);
  }

  @Override public Boolean get() {
    final String result = stringPreference.get();
    if (result == null) {
      return defaultValue;
    }
    return Boolean.parseBoolean(result);
  }
}
