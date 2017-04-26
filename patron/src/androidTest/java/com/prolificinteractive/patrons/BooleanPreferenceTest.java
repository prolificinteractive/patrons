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
public class BooleanPreferenceTest {

  private SharedPreferences prefs;

  @Before public void setUp() throws Exception {
    prefs = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext());
  }

  @Test public void testGet() throws Exception {
    final BooleanPreference pref = new BooleanPreference(prefs, "boolean_test");
    pref.delete();
    assertThat(pref.get(), is(equalTo(false)));
    pref.set(true);
    pref.set(false);
    pref.set(true);
    assertThat(pref.get(), is(equalTo(true)));
    assertThat(pref.get(), is(not(equalTo(false))));
  }
}
