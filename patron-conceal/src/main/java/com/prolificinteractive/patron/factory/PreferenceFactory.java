package com.prolificinteractive.patron.factory;

import android.content.SharedPreferences;
import com.facebook.crypto.Crypto;
import com.prolificinteractive.patrons.Preference;

public interface PreferenceFactory<T> {
  Preference<T> create(Crypto crypto, SharedPreferences sharedPreferences, String key);

  Preference<T> create(Crypto crypto, SharedPreferences sharedPreferences, String key, T value);
}
