package com.prolificinteractive.patrons.conceal;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.prolificinteractive.patrons.BasePreference;
import com.prolificinteractive.patrons.StringPreference;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * Preference wrapper using {@link StringPreference} with encryption on the {@link String}.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ConcealStringSetPreference extends BasePreference<Set<String>> {
  public static final String TAG = ConcealStringSetPreference.class.getSimpleName();
  private final Crypto crypto;
  private final Entity entity;

  public ConcealStringSetPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key) {
    this(crypto, preferences, key, null);
  }

  public ConcealStringSetPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key,
      final Set<String> defaultValue) {
    super(preferences, key, defaultValue);
    this.crypto = crypto;
    this.entity = Entity.create(key);
  }

  @Override public Set<String> get() {
    final Set<String> encryptedSet = preferences.getStringSet(key, defaultValue);

    if (encryptedSet == null) {
      return defaultValue;
    }

    final Set<String> decryptedSet = new HashSet<>(encryptedSet.size());

    for (final String s : encryptedSet) {
      try {
        decryptedSet.add(new String(
            crypto.decrypt(Base64.decode(s, Base64.NO_WRAP), entity),
            Charset.defaultCharset()
        ));
      } catch (final Exception e) {
        Log.e(TAG, e.getMessage());
        decryptedSet.add(null);
      }
    }
    return decryptedSet;
  }

  @Override public void set(final Set<String> value) {
    final Set<String> encryptedSet = new HashSet<>(value.size());
    for (final String s : value) {
      try {
        encryptedSet.add(Base64.encodeToString(
            crypto.encrypt(
                s.getBytes(Charset.defaultCharset()),
                entity
            ),
            Base64.NO_WRAP
        ));
      } catch (KeyChainException | CryptoInitializationException | IOException e) {
        Log.e(TAG, e.getMessage());
        encryptedSet.add(null);
      }
    }
    preferences.edit().putStringSet(key, encryptedSet).apply();
  }
}
