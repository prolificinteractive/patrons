package com.prolificinteractive.patrons.factory;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;
import com.prolificinteractive.patrons.conceal.ConcealIntPreference;
import com.prolificinteractive.patrons.conceal.ConcealStringPreference;
import com.prolificinteractive.patrons.Preference;

public class ConcealPreferenceFactory {

  private final Crypto crypto;
  private final SharedPreferences sharedPreferences;

  public ConcealPreferenceFactory(
      final Crypto crypto,
      final SharedPreferences sharedPreferences) {
    this.crypto = crypto;
    this.sharedPreferences = sharedPreferences;
  }

  @SuppressWarnings("unchecked")
  public <T, P extends Preference<?>> P create(final String key, final Class<T> clazz) {
    if (clazz.equals(String.class)) {
      return (P) new ConcealStringPreference(crypto, sharedPreferences, key);
    } else if (clazz.equals(Integer.class)) {
      return (P) new ConcealIntPreference(crypto, sharedPreferences, key);
    }

    throw new IllegalStateException("");
  }
}
