package com.prolificinteractive.patron.conceal;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;

public class ConcealLongPreference extends BaseConcealPreference<Long> {

  public ConcealLongPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key) {
    this(crypto, preferences, key, 0L);
  }

  public ConcealLongPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key,
      final long defaultValue) {
    super(crypto, preferences, key, defaultValue);
  }

  @Override public Long get() {
    final String result = stringPreference.get();
    if (result == null) {
      return defaultValue;
    }
    return Long.parseLong(result);
  }
}
