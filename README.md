# Patrons
[![Travis build status](https://img.shields.io/travis/prolificinteractive/patrons.svg?style=flat-square)](https://travis-ci.org/prolificinteractive/patrons)

_Let your patron do the work._

A Patron is a preference wrapper. Come also with an encryption system.

## Features

Use the adequate patron for the preference value type you would like to store.

Patrons handle all types handled by shared preferences:
- boolean
- float
- float
- int
- String
- StringSet

Patrons also provide an encrypted version of the shared preferences. Use `patrons-conceal` to encrypt your preference values.

## Installation

```gradle
compile 'com.prolificinteractive:patrons:0.1.0'

// If you want patrons with encryption of your shared preferences
compile 'com.prolificinteractive:patrons-conceal:0.1.0'
```

### Proguard

TODO

## Usage

A default patron exists for all shared preferences types.

```java
public class MyClass {
  private BooleanPreference showOnboardingPreference;
  private StringPreference usernamePreference;
  private IntPreference delayPreference;
  
  void initMyPreferences() {
    showOnboardingPreference = new BooleanPreference(prefs, "onboarding", false); 
    usernamePreference = new StringPreference(prefs, "username", "anonymous");
    catsPreference = new IntPreference(prefs, "cats", 0);
  }
  
  void updateMyPreferences() {
    showOnboardingPreference.set(true);
    usernamePreference.set("a pretty name");
    delayPreference.set(10);
  }
  
  String getUsername() {
    return usernamePreference.get();
  }
  
  int getCats() {
    return catsPreference.get();
  }
}
```

## Contributing to Patrons

To report a bug or enhancement request, feel free to file an issue under the respective heading.

If you wish to contribute to the project, fork this repo and submit a pull request. Code contributions should follow the standards specified in the [Prolific Android Style Guide](https://github.com/prolificinteractive/android-code-styles).

## License

![prolific](https://s3.amazonaws.com/prolificsitestaging/logos/Prolific_Logo_Full_Color.png)

Copyright (c) 2017 Prolific Interactive

Patrons is maintained and sponsored by Prolific Interactive. It may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: ./LICENSE