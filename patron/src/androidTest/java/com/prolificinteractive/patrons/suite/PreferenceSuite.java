package com.prolificinteractive.patrons.suite;

import com.prolificinteractive.patrons.BooleanPreferenceTest;
import com.prolificinteractive.patrons.FloatPreferenceTest;
import com.prolificinteractive.patrons.IntPreferenceTest;
import com.prolificinteractive.patrons.LongPreferenceTest;
import com.prolificinteractive.patrons.StringPreferenceTest;
import com.prolificinteractive.patrons.StringSetPreferenceTest;
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
