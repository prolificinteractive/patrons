package com.prolificinteractive.patrons.conceal;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;

public class ConcealIntPreference extends BaseConcealPreference<Integer> {

  public ConcealIntPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key) {
    this(crypto, preferences, key, 0);
  }

  public ConcealIntPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key,
      final int defaultValue) {
    super(crypto, preferences, key, defaultValue);
  }

  @Override public Integer get() {
    final String result = stringPreference.get();
    if (result == null) {
      return defaultValue;
    }
    return Integer.parseInt(result);
  }
}
