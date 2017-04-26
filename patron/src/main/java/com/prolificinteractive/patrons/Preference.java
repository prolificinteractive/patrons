package com.prolificinteractive.patrons;

/**
 * The interface Preference.
 *
 * @param <T> the type parameter hold by the preference
 */
public interface Preference<T> {
  /**
   * Delete the current value from the shared preference.
   */
  void delete();

  /**
   * Return preference value.
   *
   * @return the value
   */
  T get();

  /**
   * Whether the value is set.
   *
   * @return is value set
   */
  boolean isSet();

  /**
   * Set value to shared preferences.
   *
   * @param value the value set
   */
  void set(T value);
}
