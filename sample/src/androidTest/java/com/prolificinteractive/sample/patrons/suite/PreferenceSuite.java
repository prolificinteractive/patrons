package com.prolificinteractive.sample.patrons.suite;

import com.prolificinteractive.sample.patrons.BooleanPreferenceTest;
import com.prolificinteractive.sample.patrons.FloatPreferenceTest;
import com.prolificinteractive.sample.patrons.IntPreferenceTest;
import com.prolificinteractive.sample.patrons.LongPreferenceTest;
import com.prolificinteractive.sample.patrons.StringPreferenceTest;
import com.prolificinteractive.sample.patrons.StringSetPreferenceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
        StringPreferenceTest.class,
        FloatPreferenceTest.class,
        LongPreferenceTest.class,
        IntPreferenceTest.class,
        BooleanPreferenceTest.class,
        StringSetPreferenceTest.class
    }
)
public class PreferenceSuite {
}
