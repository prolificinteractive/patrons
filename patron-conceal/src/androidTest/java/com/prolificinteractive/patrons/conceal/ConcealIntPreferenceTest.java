package com.prolificinteractive.patrons.conceal;

import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import com.facebook.soloader.SoLoader;
import com.prolificinteractive.patrons.IntPreference;
import com.prolificinteractive.patrons.Preference;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ConcealIntPreferenceTest {

  private SharedPreferences prefs;

  @Before public void setUp() throws Exception {
    SoLoader.init(InstrumentationRegistry.getContext(), false);
    prefs = new ConcealSharedPreferences(InstrumentationRegistry.getContext());
  }

  @Test public void delete() throws Exception {
    final Preference<Integer> pref = new IntPreference(prefs, "conceal_int_test");
    pref.delete();
    assertThat(pref.isSet(), is(equalTo(false)));
    pref.set(1);
    assertThat(pref.isSet(), is(equalTo(true)));
    pref.delete();
    assertThat(pref.isSet(), is(equalTo(false)));
  }

  @Test
  public void get() throws Exception {
    final Preference<Integer> pref = new IntPreference(prefs, "conceal_int_test2");
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