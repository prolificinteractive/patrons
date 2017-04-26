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
public class IntPreferenceTest {

  private static final int DEFAULT_VALUE = 0;

  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final int FORTY_TWO = 42;

  private SharedPreferences prefs;

  @Before public void setUp() throws Exception {
    prefs = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext());
  }

  @Test public void testGet() throws Exception {
    final IntPreference pref = new IntPreference(prefs, "int_test");

    pref.delete();
    assertThat(pref.get(), is(equalTo(DEFAULT_VALUE)));

    pref.set(ONE);
    pref.set(TWO);
    pref.set(FORTY_TWO);

    assertThat(pref.get(), is(equalTo(FORTY_TWO)));
    assertThat(pref.get(), is(not(equalTo(ONE))));
    assertThat(pref.get(), is(not(equalTo(TWO))));
  }
}
