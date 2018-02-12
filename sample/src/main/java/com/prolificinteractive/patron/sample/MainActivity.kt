package com.prolificinteractive.patron.sample

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.facebook.android.crypto.keychain.AndroidConceal
import com.prolificinteractive.patrons.IntPreference
import com.prolificinteractive.patrons.Preference
import com.prolificinteractive.patrons.StringPreference
import com.prolificinteractive.patrons.conceal.ConcealEncryption
import com.prolificinteractive.patrons.conceal.ConcealSharedPreferences
import com.prolificinteractive.patrons.conceal.KeystoreBackedKeychain
import com.prolificinteractive.patrons.conceal.NoKeyEncryption
import java.util.Random

class MainActivity : AppCompatActivity() {

  private lateinit var randomPref: Preference<Int?>
  private lateinit var encryptedPref: Preference<String?>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val defaultPref = PreferenceManager.getDefaultSharedPreferences(this)
    //final ConcealSharedPreferences prefs = new ConcealSharedPreferences(this);

    // Use this block to use KeyStore backed shared conceal shared preferences.
    val prefs = getKeystoreBackedConcealSharedPreferences(defaultPref)

    encryptedPref = StringPreference(defaultPref, "random")
    randomPref = IntPreference(prefs, "random")

    val save = findViewById<View>(R.id.save)
    val extract = findViewById<View>(R.id.extract)
    val random = findViewById<TextView>(R.id.random)
    val encrypted = findViewById<TextView>(R.id.encrypted)

    save.setOnClickListener { randomPref.set(r.nextInt()) }

    extract.setOnClickListener {
      random.text = String.format("Value: %s", randomPref.get())
      encrypted.text = String.format("Encrypted: %s", encryptedPref.get())
    }
  }

  private fun getKeystoreBackedConcealSharedPreferences(
      defaultPref: SharedPreferences): ConcealSharedPreferences {
    return ConcealSharedPreferences(
        this,
        defaultPref,
        NoKeyEncryption(),
        ConcealEncryption(this, "conceal_encryption",
            AndroidConceal
                .get()
                .createDefaultCrypto(KeystoreBackedKeychain(this, defaultPref))
        )
    )
  }

  companion object {

    private val r = Random(50)
  }
}
