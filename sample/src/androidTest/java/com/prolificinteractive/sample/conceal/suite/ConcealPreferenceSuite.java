package com.prolificinteractive.sample.conceal.suite;

import com.prolificinteractive.sample.conceal.ConcealBooleanPreferenceTest;
import com.prolificinteractive.sample.conceal.ConcealFloatPreferenceTest;
import com.prolificinteractive.sample.conceal.ConcealIntPreferenceTest;
import com.prolificinteractive.sample.conceal.ConcealLongPreferenceTest;
import com.prolificinteractive.sample.conceal.ConcealStringPreferenceTest;
import com.prolificinteractive.sample.conceal.ConcealStringSetPreferenceTest;
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
        ConcealStringSetPreferenceTest.class
    }
)
public class ConcealPreferenceSuite {
}
