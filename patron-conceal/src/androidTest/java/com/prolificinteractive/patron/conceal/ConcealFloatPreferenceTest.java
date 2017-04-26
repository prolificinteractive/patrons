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
import com.prolificinteractive.patrons.conceal.ConcealFloatPreference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ConcealFloatPreferenceTest {

  private static final float DEFAULT_VALUE = 0;

  private static final float TEN = 10.0f;
  private static final float TWENTY = 20.0f;
  private static final float FORTY_TWO = 42.0f;

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

  @Test public void testGet() throws Exception {
    final ConcealFloatPreference pref = new ConcealFloatPreference(crypto, prefs, "float_test");
    pref.delete();
    assertThat(pref.get(), is(equalTo(DEFAULT_VALUE)));
    pref.set(TEN);
    pref.set(TWENTY);
    pref.set(FORTY_TWO);
    assertThat(pref.get(), is(equalTo(FORTY_TWO)));
    assertThat(pref.get(), is(not(equalTo(TEN))));
    assertThat(pref.get(), is(not(equalTo(TWENTY))));
  }
}
