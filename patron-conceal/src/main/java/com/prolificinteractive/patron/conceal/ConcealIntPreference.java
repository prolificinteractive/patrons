package com.prolificinteractive.patron.conceal;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;
import com.prolificinteractive.patrons.Preference;
import com.prolificinteractive.patrons.StringPreference;

public class ConcealIntPreference implements Preference<Integer> {
  public static final String TAG = ConcealStringPreference.class.getSimpleName();

  protected final int defaultValue;

  private final StringPreference stringPreference;

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
    this.defaultValue = defaultValue;
    this.stringPreference =
        new ConcealStringPreference(crypto, preferences, key, String.valueOf(defaultValue));
  }

  @Override public void delete() {
    stringPreference.delete();
  }

  @Override public Integer get() {
    final String result = stringPreference.get();
    if (result == null) {
      return 0;
    }
    return Integer.parseInt(result);
  }

  @Override public boolean isSet() {
    return stringPreference.isSet();
  }

  @Override public void set(final Integer value) {
    stringPreference.set(String.valueOf(value));
  }
}
