package com.prolificinteractive.patrons.conceal

import android.content.Context
import android.content.SharedPreferences
import com.facebook.android.crypto.keychain.SecureRandomFix
import com.facebook.crypto.exception.KeyChainException
import com.facebook.crypto.keychain.KeyChain
import java.security.SecureRandom

class KeystoreBackedKeychain(
    context: Context,
    private val mSharedPreferences: SharedPreferences) : KeyChain {

  private val mSecureRandom: SecureRandom = SecureRandomFix.createLocalSecureRandom()

  private var mCipherKey: ByteArray? = null
  private var mMacKey: ByteArray? = null
  private var mSetCipherKey: Boolean = false
  private var mSetMacKey: Boolean = false

  init {
    KeyStoreHelper.createKeys(context, ALIAS)
  }

  @Synchronized override fun destroyKeys() {
    KeyStoreHelper.deleteKeys(ALIAS)
  }

  @Synchronized
  @Throws(KeyChainException::class)
  override fun getCipherKey(): ByteArray? {
    if (!this.mSetCipherKey) {
      this.mCipherKey = this.maybeGenerateKey(CIPHER_KEY_PREF, CIPHER_LENGTH)
    }

    this.mSetCipherKey = true
    return this.mCipherKey
  }

  @Throws(KeyChainException::class)
  override fun getMacKey(): ByteArray? {
    if (!this.mSetMacKey) {
      this.mMacKey = this.maybeGenerateKey(MAC_KEY_PREF, MAC_LENGTH)
    }

    this.mSetMacKey = true
    return this.mMacKey
  }

  @Throws(KeyChainException::class)
  override fun getNewIV(): ByteArray {
    val iv = ByteArray(IV_LENGTH)
    this.mSecureRandom.nextBytes(iv)
    return iv
  }

  private fun generateAndSaveKey(pref: String, length: Int): ByteArray {
    val key = ByteArray(length)
    this.mSecureRandom.nextBytes(key)
    val editor = this.mSharedPreferences.edit()
    val encrypted = KeyStoreHelper.encrypt(ALIAS, key)
    editor.putString(pref, encrypted)
    editor.apply()
    return KeyStoreHelper.decrypt(ALIAS, encrypted)
  }

  @Throws(KeyChainException::class)
  private fun maybeGenerateKey(pref: String, length: Int): ByteArray {
    val encrypted = this.mSharedPreferences.getString(pref, null)
    return when (encrypted) {
      null -> this.generateAndSaveKey(pref, length)
      else -> KeyStoreHelper.decrypt(ALIAS, encrypted)
    }
  }

  companion object {
    private const val CIPHER_KEY_PREF = "encrypted_cipher_key"
    private const val MAC_KEY_PREF = "encrypted_mac_key"
    private const val ALIAS = "keystore_backed_keychain"

    private const val CIPHER_LENGTH = 32
    private const val MAC_LENGTH = 64
    private const val IV_LENGTH = 12
  }
}