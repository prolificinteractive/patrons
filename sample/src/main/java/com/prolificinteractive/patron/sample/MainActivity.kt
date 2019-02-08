package com.prolificinteractive.patron.sample

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.facebook.android.crypto.keychain.AndroidConceal
import com.prolificinteractive.patrons.IntPreference
import com.prolificinteractive.patrons.Preference
import com.prolificinteractive.patrons.StringPreference
import com.prolificinteractive.patrons.conceal.ConcealEncryption
import com.prolificinteractive.patrons.conceal.ConcealSharedPreferences
import com.prolificinteractive.patrons.conceal.KeystoreBackedKeychain
import com.prolificinteractive.patrons.conceal.NoKeyEncryption
import kotlinx.android.synthetic.main.activity_main.encrypted
import kotlinx.android.synthetic.main.activity_main.extract
import kotlinx.android.synthetic.main.activity_main.random
import kotlinx.android.synthetic.main.activity_main.save
import java.util.Random

class MainActivity : AppCompatActivity() {
  companion object {
    private val r = Random(50)
  }

  private lateinit var randomPref: Preference<Int?>
  private lateinit var encryptedPref: Preference<String?>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val defaultPref = PreferenceManager.getDefaultSharedPreferences(this)

    // Use this block to use KeyStore backed shared conceal shared preferences.
    val prefs = getKeystoreBackedConcealSharedPreferences(defaultPref)
    // val prefs = getDefaultConcealSharedPreferences()

    encryptedPref = StringPreference(defaultPref, "random")
    randomPref = IntPreference(prefs, "random")

    save.setOnClickListener { randomPref.set(r.nextInt()) }

    extract.setOnClickListener {
      random.text = String.format("Value: %s", randomPref.get())
      encrypted.text = String.format("Encrypted: %s", encryptedPref.get())
    }
  }

  private fun getKeystoreBackedConcealSharedPreferences(
    defaultPref: SharedPreferences
  ): ConcealSharedPreferences {
    val crypto = AndroidConceal.get().createDefaultCrypto(KeystoreBackedKeychain(this, defaultPref))
    val valueEncryption = ConcealEncryption(this, "conceal_encryption", crypto)
    return ConcealSharedPreferences(this, defaultPref, NoKeyEncryption(), valueEncryption)
  }

  private fun getDefaultConcealSharedPreferences() = ConcealSharedPreferences(this)
}
