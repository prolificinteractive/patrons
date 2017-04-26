package com.prolificinteractive.patron.conceal.suite;

import com.prolificinteractive.patron.conceal.ConcealBooleanPreferenceTest;
import com.prolificinteractive.patron.conceal.ConcealFloatPreferenceTest;
import com.prolificinteractive.patron.conceal.ConcealIntPreferenceTest;
import com.prolificinteractive.patron.conceal.ConcealLongPreferenceTest;
import com.prolificinteractive.patron.conceal.ConcealStringPreferenceTest;
import com.prolificinteractive.patron.conceal.ConcealStringSetPreferenceTest;
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
