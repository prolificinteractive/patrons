package com.prolificinteractive.patron.conceal;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.prolificinteractive.patrons.StringPreference;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Preference wrapper using {@link StringPreference} with encryption on the {@link String}.
 */
public class ConcealStringPreference extends StringPreference {
  public static final String TAG = ConcealStringPreference.class.getSimpleName();
  private final Crypto crypto;
  private final Entity entity;

  public ConcealStringPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key) {
    this(crypto, preferences, key, null);
  }

  public ConcealStringPreference(
      final Crypto crypto,
      final SharedPreferences preferences,
      final String key,
      final String defaultValue) {
    super(preferences, key, defaultValue);
    this.crypto = crypto;
    this.entity = Entity.create(key);
  }

  @Override public String get() {
    try {
      return new String(
          crypto.decrypt(Base64.decode(super.get(), Base64.NO_WRAP), entity),
          Charset.defaultCharset()
      );
    } catch (final Exception e) {
      Log.e(TAG, e.getMessage());
    }
    return defaultValue;
  }

  @Override public void set(final String value) {
    try {
      if (value != null) {
        super.set(Base64.encodeToString(
            crypto.encrypt(
                value.getBytes(Charset.defaultCharset()),
                entity
            ),
            Base64.NO_WRAP
        ));
      } else {
        delete();
      }
    } catch (KeyChainException | CryptoInitializationException | IOException e) {
      Log.e(TAG, e.getMessage());
    }
  }
}
