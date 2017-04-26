package com.prolificinteractive.patron.conceal;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.prolificinteractive.patron.factory.ConcealPreferenceFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ConcealIntPreferenceTest {

  private ConcealPreferenceFactory factory;

  @Before public void setUp() throws Exception {
    final SharedPreferences prefs =
        PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext());
    final Crypto crypto = AndroidConceal
        .get()
        .createDefaultCrypto(new SharedPrefsBackedKeyChain(
            InstrumentationRegistry.getContext(),
            CryptoConfig.KEY_256
        ));

    factory = new ConcealPreferenceFactory(crypto, prefs);
  }

  @Test public void delete() throws Exception {
    final ConcealIntPreference pref = factory.create("conceal_int_test", Integer.class);
    pref.delete();
    assertThat(pref.isSet(), is(equalTo(false)));
    pref.set(1);
    assertThat(pref.isSet(), is(equalTo(true)));
    pref.delete();
    assertThat(pref.isSet(), is(equalTo(false)));
  }

  @Test
  public void get() throws Exception {
    final ConcealIntPreference pref = factory.create("conceal_int_test2", Integer.class);
    pref.delete();
    assertThat(pref.get(), is(equalTo(0)));
    pref.set(1);
    assertThat(pref.get(), is(equalTo(1)));
    assertThat(pref.get(), is(not(equalTo(666))));
    pref.set(42);
    assertThat(pref.get(), is(equalTo(42)));
    assertThat(pref.get(), is(not(equalTo(1))));
  }
}