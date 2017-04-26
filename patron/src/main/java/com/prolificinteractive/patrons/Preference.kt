package com.prolificinteractive.patrons

/**
 * The interface Preference.
 *
 * @param <T> the type parameter hold by the preference
</T> */
interface Preference<T> {

  /**
   * Whether the value is set.
   *
   * @return is value set
   */
  fun isSet(): Boolean

  /**
   * Delete the current value from the shared preference.
   */
  fun delete()

  /**
   * Return preference value.
   *
   * @return the value
   */
  fun get(): T?

  /**
   * Set value to shared preferences.
   *
   * @param value the value set
   */
  fun set(value: T?)
}
