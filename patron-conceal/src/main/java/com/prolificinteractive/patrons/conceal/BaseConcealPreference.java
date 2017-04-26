package com.prolificinteractive.patrons.conceal;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;
import com.prolificinteractive.patrons.Preference;
import com.prolificinteractive.patrons.StringPreference;

public abstract class BaseConcealPreference<T> implements Preference<T> {

  protected final T defaultValue;
  protected final StringPreference stringPreference;

  public BaseConcealPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key,
      final T defaultValue) {
    this.defaultValue = defaultValue;
    this.stringPreference =
        new ConcealStringPreference(crypto, preferences, key, String.valueOf(defaultValue));
  }

  @Override public void delete() {
    stringPreference.delete();
  }

  @Override public boolean isSet() {
    return stringPreference.isSet();
  }

  @Override public void set(final T value) {
    stringPreference.set(String.valueOf(value));
  }
}
