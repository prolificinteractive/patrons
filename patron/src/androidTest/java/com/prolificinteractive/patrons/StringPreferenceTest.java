package com.prolificinteractive.patrons;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class StringPreferenceTest {

  private static final String DEFAULT_VALUE = null;

  private static final String TEN = "ten";
  private static final String TWENTY = "20";
  private static final String FORTY_TWO = "forty_2";

  private SharedPreferences prefs;

  @Before public void setUp() throws Exception {
    prefs = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext());
  }

  @Test public void testGet() throws Exception {
    final StringPreference pref = new StringPreference(prefs, "string_test");

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
