package com.prolificinteractive.patrons.conceal;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;

public class ConcealFloatPreference extends BaseConcealPreference<Float> {

  public ConcealFloatPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key) {
    this(crypto, preferences, key, 0f);
  }

  public ConcealFloatPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key,
      final float defaultValue) {
    super(crypto, preferences, key, defaultValue);
  }

  @Override public Float get() {
    final String result = stringPreference.get();
    if (result == null) {
      return defaultValue;
    }
    return Float.parseFloat(result);
  }
}
