# Patrons
[![Travis branch](https://img.shields.io/travis/prolificinteractive/patrons/master.svg)](https://travis-ci.org/prolificinteractive/patrons) [![](https://jitpack.io/v/prolificinteractive/patrons.svg)](https://jitpack.io/#prolificinteractive/patrons)



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

Step 1. Add the JitPack repository to your build file

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency

```groovy
dependencies {
  implementation 'com.github.prolificinteractive:patrons:${patronsVersion}'

  // If you want patrons with encryption of your shared preferences
  implementation 'com.github.prolificinteractive:patrons-conceal:${patronsVersion}'
}
```

## Usage

A default patron exists for all shared preferences types.

#### Boolean
```kotlin
val boolPref: Preference<Boolean> = BooleanPreference(prefs, "key")
```

#### Float
```kotlin
val floatPref: Preference<Float> = FloatPreference(prefs, "key")
```

#### Integer
```kotlin
val intPref: Preference<Integer> = IntPreference(prefs, "key")
```

#### Long
```kotlin
val longPref: Preference<Long> = LongPreference(prefs, "key")
```

#### String
```kotlin
val stringPref: Preference<String> = StringPreference(prefs, "key")
```

#### String Set
```kotlin
val stringSetPref: Preference<Set<String>> = StringSetPreference(prefs, "key")
```

### Conceal

Init Conceal in your application class:

```java
import com.facebook.soloader.SoLoader;

public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, false);
  }
}
```

## Encryption

### SharedPreferences Backed Keychain

```kotlin
val prefs = ConcealSharedPreferences(context)
```

### Keystore Backed Keychain

By default, preference's key is __NOT__ encrypted but the behaviour can be customized by passing your own custom [KeyEncryption](patron-conceal/src/main/java/com/prolificinteractive/patrons/conceal/KeyEncryption.kt)

```kotlin
val defaultPref = PreferenceManager.getDefaultSharedPreferences(context)
val concealedPrefs = ConcealSharedPreferences(
  this,
  defaultPref,
  NoKeyEncryption(),
  ConcealEncryption(
    context,
    "conceal_encryption",
    AndroidConceal
      .get()
      .createDefaultCrypto(KeystoreBackedKeychain(context, defaultPref))
    )
)
```

### Proguard

TODO

## Contributing to Patrons

To report a bug or enhancement request, feel free to file an issue under the respective heading.

If you wish to contribute to the project, fork this repo and submit a pull request. Code contributions should follow the standards specified in the [Prolific Android Style Guide](https://github.com/prolificinteractive/android-code-styles).

## License

![prolific](https://s3.amazonaws.com/prolificsitestaging/logos/Prolific_Logo_Full_Color.png)

Copyright (c) 2018 Prolific Interactive

Patrons is maintained and sponsored by Prolific Interactive. It may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: ./LICENSE
