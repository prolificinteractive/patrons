package com.prolificinteractive.patron.conceal;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.soloader.SoLoader;
import com.prolificinteractive.patrons.conceal.ConcealBooleanPreference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ConcealBooleanPreferenceTest {

  private SharedPreferences prefs;
  private Crypto crypto;

  @Before public void setUp() throws Exception {
    SoLoader.init(InstrumentationRegistry.getContext(), false);

    prefs = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext());
    crypto = AndroidConceal
        .get()
        .createDefaultCrypto(new SharedPrefsBackedKeyChain(
            InstrumentationRegistry.getContext(),
            CryptoConfig.KEY_256
        ));
  }

  @Test public void get() throws Exception {
    final ConcealBooleanPreference pref =
        new ConcealBooleanPreference(crypto, prefs, "boolean_test");
    pref.delete();
    assertThat(pref.get(), is(equalTo(false)));
    pref.set(true);
    pref.set(false);
    pref.set(true);
    assertThat(pref.get(), is(equalTo(true)));
    assertThat(pref.get(), is(not(equalTo(false))));
  }
}
