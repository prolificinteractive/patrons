package com.prolificinteractive.patrons.conceal

import android.content.Context
import android.util.Base64
import android.util.Log
import com.facebook.android.crypto.keychain.AndroidConceal
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain
import com.facebook.crypto.Crypto
import com.facebook.crypto.CryptoConfig
import com.facebook.crypto.Entity
import com.facebook.crypto.exception.CryptoInitializationException
import com.facebook.crypto.exception.KeyChainException
import java.io.IOException
import java.nio.charset.Charset
import java.util.HashSet

class ConcealEncryption @JvmOverloads constructor(
    context: Context,
    name: String = "conceal_encryption",
    private val crypto: Crypto = AndroidConceal.get()
        .createDefaultCrypto(SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256)),
    private val entity: Entity = Entity.create(name)
) : KeyEncryption, ValueEncryption {

  override fun decrypt(value: String): String {
    try {
      return String(
          crypto.decrypt(Base64.decode(value, Base64.NO_WRAP), entity),
          Charset.defaultCharset()
      )
    } catch (e: KeyChainException) {
      Log.e(TAG, e.message)
    } catch (e: CryptoInitializationException) {
      Log.e(TAG, e.message)
    } catch (e: IOException) {
      Log.e(TAG, e.message)
    }

    return value
  }

  override fun decryptSet(values: Set<String?>): Set<String?> {
    val decryptedSet = HashSet<String?>(values.size)

    values.forEach {
      try {
        decryptedSet.add(String(
            crypto.decrypt(Base64.decode(it, Base64.NO_WRAP), entity),
            Charset.defaultCharset()
        ))
      } catch (e: Exception) {
        Log.e(TAG, e.message)
        decryptedSet.add(null)
      }
    }
    return decryptedSet
  }

  override fun encrypt(value: String): String {
    try {
      return Base64.encodeToString(
          crypto.encrypt(value.toByteArray(Charset.defaultCharset()), entity),
          Base64.NO_WRAP
      )
    } catch (e: KeyChainException) {
      Log.e(TAG, e.message)
    } catch (e: CryptoInitializationException) {
      Log.e(TAG, e.message)
    } catch (e: IOException) {
      Log.e(TAG, e.message)
    }

    return value
  }

  override fun encryptSet(values: Set<String?>): Set<String?> {
    val encryptedSet = HashSet<String?>(values.size)
    values.forEach {
      try {
        encryptedSet.add(Base64.encodeToString(
            crypto.encrypt(
                it?.toByteArray(Charset.defaultCharset()),
                entity
            ),
            Base64.NO_WRAP
        ))
      } catch (e: KeyChainException) {
        Log.e(TAG, e.message)
        encryptedSet.add(null)
      } catch (e: CryptoInitializationException) {
        Log.e(TAG, e.message)
        encryptedSet.add(null)
      } catch (e: IOException) {
        Log.e(TAG, e.message)
        encryptedSet.add(null)
      }
    }
    return encryptedSet
  }

  companion object {
    const val TAG = "ConcealEncryption"
  }
}
