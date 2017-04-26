package com.prolificinteractive.patrons;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class StringSetPreferenceTest {

  private static final Set<String> DEFAULT_VALUE = null;

  private static final Set<String> TEN = new HashSet<>(Collections.singletonList("10"));
  private static final Set<String> TWENTY = new HashSet<>(Arrays.asList("twenty", "20"));
  private static final Set<String> FORTY_TWO = new HashSet<>(Arrays.asList("forty", "two", "2"));

  private SharedPreferences prefs;

  @Before public void setUp() throws Exception {
    prefs = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext());
  }

  @Test public void get() throws Exception {
    final StringSetPreference pref = new StringSetPreference(prefs, "string_set_test");

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
