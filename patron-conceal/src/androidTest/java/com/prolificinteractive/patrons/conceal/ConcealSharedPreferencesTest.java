package com.prolificinteractive.patrons.conceal;

import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import com.facebook.soloader.SoLoader;
import com.prolificinteractive.patrons.BooleanPreference;
import com.prolificinteractive.patrons.FloatPreference;
import com.prolificinteractive.patrons.IntPreference;
import com.prolificinteractive.patrons.LongPreference;
import com.prolificinteractive.patrons.Preference;
import com.prolificinteractive.patrons.StringPreference;
import com.prolificinteractive.patrons.StringSetPreference;
import com.prolificinteractive.patrons.conceal.ConcealSharedPreferences;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ConcealSharedPreferencesTest {

  private SharedPreferences prefs;

  @Before public void setUp() throws Exception {
    SoLoader.init(InstrumentationRegistry.getContext(), false);
    prefs = new ConcealSharedPreferences(InstrumentationRegistry.getContext());
  }

  @Test public void getString() throws Exception {
    final Preference<String> pref = new StringPreference(prefs, "string_test");

    pref.delete();
    assertThat(pref.get(), is(equalTo(null)));

    pref.set("ten");
    pref.set("20");
    pref.set("42");

    assertThat(pref.get(), is(not(equalTo("ten"))));
    assertThat(pref.get(), is(not(equalTo("20"))));
    assertThat(pref.get(), is(equalTo("42")));
  }

  @Test public void getStringSet() throws Exception {
    final StringSetPreference pref = new StringSetPreference(prefs, "string_set_test");

    final Set<String> ten = new HashSet<>(Collections.singletonList("10"));
    final Set<String> twenty = new HashSet<>(Arrays.asList("twenty", "20"));
    final Set<String> fortyTwo = new HashSet<>(Arrays.asList("forty", "two", "2"));

    pref.delete();
    assertThat(pref.get(), is(equalTo(null)));

    pref.set(ten);
    pref.set(twenty);
    pref.set(fortyTwo);

    assertThat(pref.get(), is(equalTo(fortyTwo)));
    assertThat(pref.get(), is(not(equalTo(ten))));
    assertThat(pref.get(), is(not(equalTo(twenty))));
  }

  @Test public void getInt() throws Exception {
    final Preference<Integer> pref = new IntPreference(prefs, "int_test");

    pref.delete();
    assertThat(pref.get(), is(equalTo(0)));

    pref.set(10);
    pref.set(20);
    pref.set(42);

    assertThat(pref.get(), is(equalTo(42)));
    assertThat(pref.get(), is(not(equalTo(10))));
    assertThat(pref.get(), is(not(equalTo(20))));
  }

  @Test public void getLong() throws Exception {
    final Preference<Long> pref = new LongPreference(prefs, "long_test");

    pref.delete();
    assertThat(pref.get(), is(equalTo(0L)));

    pref.set(10L);
    pref.set(20L);
    pref.set(42L);

    assertThat(pref.get(), is(equalTo(42L)));
    assertThat(pref.get(), is(not(equalTo(10L))));
    assertThat(pref.get(), is(not(equalTo(20L))));
  }

  @Test public void getFloat() throws Exception {
    final Preference<Float> pref = new FloatPreference(prefs, "float_test");

    pref.delete();
    assertThat(pref.get(), is(equalTo(0f)));

    pref.set(10.0f);
    pref.set(20.0f);
    pref.set(42.0f);

    assertThat(pref.get(), is(equalTo(42.0f)));
    assertThat(pref.get(), is(not(equalTo(10.0f))));
    assertThat(pref.get(), is(not(equalTo(20.0f))));
  }

  @Test public void getBoolean() throws Exception {
    final Preference<Boolean> pref = new BooleanPreference(prefs, "boolean_test");

    pref.delete();
    assertThat(pref.get(), is(equalTo(false)));

    pref.set(true);
    pref.set(false);
    pref.set(true);

    assertThat(pref.get(), is(equalTo(true)));
    assertThat(pref.get(), is(not(equalTo(false))));
  }
}