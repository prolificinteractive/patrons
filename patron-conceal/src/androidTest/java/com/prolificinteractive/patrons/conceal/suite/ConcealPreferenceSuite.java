package com.prolificinteractive.patrons.conceal.suite;

import com.prolificinteractive.patrons.conceal.ConcealBooleanPreferenceTest;
import com.prolificinteractive.patrons.conceal.ConcealFloatPreferenceTest;
import com.prolificinteractive.patrons.conceal.ConcealIntPreferenceTest;
import com.prolificinteractive.patrons.conceal.ConcealLongPreferenceTest;
import com.prolificinteractive.patrons.conceal.ConcealStringPreferenceTest;
import com.prolificinteractive.patrons.conceal.ConcealStringSetPreferenceTest;
import com.prolificinteractive.patrons.conceal.ConcealSharedPreferencesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
        ConcealStringPreferenceTest.class,
        ConcealFloatPreferenceTest.class,
        ConcealLongPreferenceTest.class,
        ConcealIntPreferenceTest.class,
        ConcealBooleanPreferenceTest.class,
        ConcealStringSetPreferenceTest.class,
        ConcealSharedPreferencesTest.class
    }
)
public class ConcealPreferenceSuite {
}
